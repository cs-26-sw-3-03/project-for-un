import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public abstract class OrderQuerier {
    
    protected static class TransactionKey {
        private final int productId;
        private final BigDecimal price;

        public TransactionKey(int productId, BigDecimal price) {
            this.productId = productId;
            this.price = price;
        }

        @Override 
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof TransactionKey)) return false;

            TransactionKey other = (TransactionKey) obj;

            return productId == other.productId && price.compareTo(other.price) == 0;
        }

        @Override 
        public int hashCode() {
            return 31 * productId + price.stripTrailingZeros().hashCode();
        }
    }

    protected static void InsertTransactions(
        Querier querier,
        int orderId,
        Order order,
        String tableName,
        String orderNameId
    ) throws SQLException {

        HashMap<TransactionKey, Integer> groupedTransactions = new HashMap<>();

        for (Transaction transaction : order.GetTransactions()) {

            TransactionKey key = new TransactionKey(transaction.GetIntId(), transaction.GetPrice());

            groupedTransactions.merge(key, transaction.GetCount(), Integer::sum);
        }

        int transactionCount = groupedTransactions.size();

        if (transactionCount == 0) {
            return;
        }

        StringBuilder sql = new StringBuilder(
            "INSERT INTO " + tableName + " (" + orderNameId + ", product_id, count, price) VALUES "
        );

        for (int i = 0; i < transactionCount; i++) {
            if (i > 0) {
                sql.append(", ");
            }

            sql.append("(?, ?, ?, ?)");
        }

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql.toString())) {

            int parameterIndex = 1;

            for (Map.Entry<TransactionKey, Integer> entry : groupedTransactions.entrySet()) {

                TransactionKey key = entry.getKey();
                int totalCount = entry.getValue();

                stmt.setInt(parameterIndex++, orderId);
                stmt.setInt(parameterIndex++, key.productId);
                stmt.setInt(parameterIndex++, totalCount);
                stmt.setBigDecimal(parameterIndex++, key.price);
            }

            querier.Execute(stmt);
         }
    }

    protected static ArrayList<Transaction> GetTransactions(
        Querier querier,
        int orderId,
        String tableName,
        String orderNameId
    ) throws SQLException {

        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT t.product_id, t.count, t.price, p.string_id, p.name " + "FROM " + tableName + " t " + "JOIN Product p ON t.product_id = p.int_id " + "WHERE t." + orderNameId + " = ?";

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet res = querier.ExecuteQuery(stmt)) {

                while (res.next()) {
                    
                    Transaction transaction = new Transaction(
                        res.getInt("product_id"),
                        res.getString("string_id"),
                        res.getString("name"),
                        res.getInt("count"),
                        res.getBigDecimal("price")
                    );

                    transactions.add(transaction);
                }
            }
        }

        return transactions;
    }
}