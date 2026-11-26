import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;

public class SupplierOrderQuerier extends OrderQuerier {

    public static SupplierOrder MakeSupplierOrder(SupplierOrder order) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "INSERT INTO SupplierOrder (date, finished) VALUES (?, ?)";

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql, true)) {

            stmt.setString(1, order.GetDate().toString());
            stmt.setBoolean(2, false);

            querier.Execute(stmt);

            try (ResultSet keys = stmt.getGeneratedKeys()) {

                if (!keys.next()) {
                    throw new SQLException("Could not get supplier order ID");
                }

                int generatedId = keys.getInt(1);

                InsertTransactions(
                    querier,
                    generatedId,
                    order,
                    "SupplierTransaction",
                    "supplier_order_id"
                );

                order.SetId(generatedId);
                order.SetStatus(OrderStatus.ONGOING);
            }
        }      

        return order;
    }

    public static ArrayList<Transaction> GetSupplierTransactions(int orderId) throws SQLException {

        Querier querier = Querier.GetInstance();

        return GetTransactions(
            querier,
            orderId,
            "SupplierTransaction",
            "supplier_order_id"
        );
    }

    public static SupplierOrder GetSupplierOrder(int orderId) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "SELECT date, finished FROM SupplierOrder WHERE supplier_order_id = ?";

        SupplierOrder order;

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet res = querier.ExecuteQuery(stmt)) {

                if (!res.next()) {
                    return null;
                }

                String dateValue = res.getString("date");
                LocalDate date = dateValue == null ? null : LocalDate.parse(dateValue);

                boolean finished = res.getBoolean("finished");

                order = new SupplierOrder(orderId, date, finished);
            }
        }

        ArrayList<Transaction> transactions = GetSupplierTransactions(orderId);

        for (Transaction transaction : transactions) {
            order.AddTransaction(transaction);
        }

        return order;
    }
}
