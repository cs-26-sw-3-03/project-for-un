import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;




public class CustomerOrderQuerier extends OrderQuerier {

    public static CustomerOrder MakeCustomerOrder(CustomerOrder order) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "INSERT INTO CustomerOrder (date) VALUES (?)";

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql, true)) {

            stmt.setString(1, order.GetDate().toString());

            querier.Execute(stmt);

            try (ResultSet keys = stmt.getGeneratedKeys()) {

                if (!keys.next()) {
                    throw new SQLException("Could not get customer order ID");
                }

                int generatedId = keys.getInt(1);

                InsertTransactions(
                    querier,
                    generatedId,
                    order,
                    "CustomerTransaction",
                    "customer_order_id"
                );

                order.SetId(generatedId);
            }
        }
        
        return order;
    }

        public static ArrayList<Transaction> GetCustomerTransactions(int orderId) throws SQLException {

        Querier querier = Querier.GetInstance();

        return GetTransactions(
            querier,
            orderId,
            "CustomerTransaction",
            "customer_order_id"
        );
    }

        public static CustomerOrder GetCustomerOrder(int orderId) throws SQLException {

        Querier querier = Querier.GetInstance();

        String sql = "SELECT date FROM Customerorder WHERE customer_order_id = ?";

        CustomerOrder order;

        try (PreparedStatement stmt = querier.MakePreparedStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet res = querier.ExecuteQuery(stmt)) {

                if (!res.next()) {
                    return null;
                }

                String dateValue = res.getString("date");
                LocalDate date = dateValue == null ? null : LocalDate.parse(dateValue);

                order = new CustomerOrder(date);
                order.SetId(orderId);
            }
        } 

        ArrayList<Transaction> transactions = GetCustomerTransactions(orderId);

        for (Transaction transaction : transactions) {
            order.AddTransaction(transaction);
        }

        return order;
    }
}