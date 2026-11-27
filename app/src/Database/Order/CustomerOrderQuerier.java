import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;




public class CustomerOrderQuerier extends OrderQuerier {


    // Creates a customer order in the database, gets the generated order ID and inserts its transactions
    public static CustomerOrder MakeCustomerOrder(CustomerOrder order) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "INSERT INTO CustomerOrder (date) VALUES (?)";

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql, true)) {

            stmt.setString(1, order.GetDate().toString());

            querier.Execute(stmt);

            // Gets the automatically generated customer order ID
            try (ResultSet keys = stmt.getGeneratedKeys()) {

                if (!keys.next()) {
                    throw new SQLException("Could not get customer order ID");
                }

                int generatedId = keys.getInt(1);

                // Inserts the transactions using the generated order id
                InsertTransactions(generatedId, order, "CustomerTransaction", "customer_order_id");

                order.SetId(generatedId);
            }
        }
        
        return order;
    }

        // Gets all transactions belonging to a specific customer order
        public static ArrayList<Transaction> GetCustomerTransactions(int orderId) throws SQLException {
            return GetTransactions(orderId, "CustomerTransaction", "customer_order_id");
    }

        // gets a customer order from the database, including its transactions
        public static CustomerOrder GetCustomerOrder(int orderId) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "SELECT date FROM CustomerOrder WHERE customer_order_id = ?";

        CustomerOrder order;

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet res = querier.ExecuteQuery(stmt)) {

                //returns null if no customer order with the given id exists
                if (!res.next()) {
                    return null;
                }

                String dateValue = res.getString("date");
                LocalDate date = dateValue == null ? null : LocalDate.parse(dateValue);

                order = new CustomerOrder(orderId, date);
            }
        } 

        // Gets and adds all transactions belonging to the customer order
        ArrayList<Transaction> transactions = GetCustomerTransactions(orderId);

        for (Transaction transaction : transactions) {
            order.AddTransaction(transaction);
        }

        return order;
    }
}