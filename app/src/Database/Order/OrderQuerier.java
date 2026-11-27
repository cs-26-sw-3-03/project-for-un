import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Provides database operations for customer and supplier orders
public abstract class OrderQuerier {
    
    // This clsas groups transactions with the same product ID and price
    protected static class TransactionKey {
        private final int productId;
        private final BigDecimal price;

        public TransactionKey(int productId, BigDecimal price) {
            this.productId = productId;
            this.price = price;
        }

        // this metheod equals() compares the product Id and price,
        // using compareTo() so that fx the price 50.0 and 50.00 are considered equal 
        public boolean equals(Object obj) { 
            if (this == obj) return true;
            if (!(obj instanceof TransactionKey)) return false;

            TransactionKey other = (TransactionKey) obj;

            return productId == other.productId && price.compareTo(other.price) == 0;
        }

        // hashcode() uses stripTrailingZeros() to confirm that equal prices have the same hash value, 
        // then this allows HashMap to group them corretly
        public int hashCode() {
            return 31 * productId + price.stripTrailingZeros().hashCode();
        }
    }

    //Inserts an order's transcation into the database, transactions with the same product id and price are grouped together
    // and their quantities are added to a single database entry
    protected static void InsertTransactions(int orderId, Order order, String tableName, String orderNameId) throws SQLException {

        Querier querier = Querier.GetInstance();

        // Here maps each unique product and price combination to its totaly quantity
        HashMap<TransactionKey, Integer> groupedTransactions = new HashMap<>();

        for (Transaction transaction : order.GetTransactions()) {

            TransactionKey key = new TransactionKey(transaction.GetIntId(), transaction.GetPrice());

            // Adds the transaction quantity to an existing key or creates a new entry
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

    protected static ArrayList<Transaction> GetTransactions(int orderId, String tableName, String orderNameId) throws SQLException {
        
        Querier querier = Querier.GetInstance();

        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT t.product_id, t.count, t.price, p.string_id, p.name FROM " + tableName + " t JOIN Product p ON t.product_id = p.int_id WHERE t." + orderNameId + " = ?";

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