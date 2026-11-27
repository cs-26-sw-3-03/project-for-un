import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SupplierOrderQuerier extends OrderQuerier {

    // Creates a supplier order in the database, gets the generated order ID and inserts the order's transactions
    public static SupplierOrder MakeSupplierOrder(SupplierOrder order) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "INSERT INTO SupplierOrder (date, finished) VALUES (?, ?)";

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql, true)) {

            stmt.setString(1, order.GetDate().toString());
            stmt.setBoolean(2, false);

            querier.Execute(stmt);

            // Here gets the auto generated supplier order ID
            try (ResultSet keys = stmt.getGeneratedKeys()) {

                if (!keys.next()) {
                    throw new SQLException("Could not get supplier order ID");
                }

                int generatedId = keys.getInt(1);

                // Inserts the transactions using the generated order Id
                InsertTransactions(generatedId, order, "SupplierTransaction", "supplier_order_id");

                order.SetId(generatedId);
                order.SetStatus(OrderStatus.ONGOING);
            }
        }      

        return order;
    }

    // Gets all transactions belonging to a specific supplier order
    public static ArrayList<Transaction> GetSupplierTransactions(int orderId) throws SQLException {
        return GetTransactions(orderId, "SupplierTransaction", "supplier_order_id");
    }

    // Gets a supplier order from the database, including its transactions
    public static SupplierOrder GetSupplierOrder(int orderId) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "SELECT date, finished FROM SupplierOrder WHERE supplier_order_id = ?";

        SupplierOrder order;

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet res = querier.ExecuteQuery(stmt)) {

                // Returns null if no supplier order with the given Id exists
                if (!res.next()) {
                    return null;
                }

                String dateValue = res.getString("date");
                LocalDate date = dateValue == null ? null : LocalDate.parse(dateValue);

                boolean finished = res.getBoolean("finished");

                order = new SupplierOrder(orderId, date, finished);
            }
        }

        // Gets and adds all transactions belonging to the supplier order
        ArrayList<Transaction> transactions = GetSupplierTransactions(orderId);

        for (Transaction transaction : transactions) {
            order.AddTransaction(transaction);
        }

        return order;
    }
}
