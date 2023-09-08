package implementation;

import dto.Borrower;
import helper.DatabaseConnection;
import interfaces.IBorrower;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowerImpl implements IBorrower {

    private final String ADD_BORROWER = "INSERT INTO borrowers (name, tel, cin, date_of_birth) VALUES (?, ?, ?, ?)";
    private final String UPDATE_BORROWER = "UPDATE borrowers SET name = ?, tel = ?, cin = ?, date_of_birth = ? WHERE id = ?";
    private final String DELETE_BORROWER = "DELETE FROM borrowers WHERE cin = ?";
    private final String SELECT_BORROWER = "SELECT * FROM borrowers";

    @Override
    public void add(Borrower borrower) {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_BORROWER)) {
            preparedStatement.setString(1, borrower.getName());
            preparedStatement.setInt(2, borrower.getTel());
            preparedStatement.setString(3, borrower.getCin());
            preparedStatement.setDate(4, Date.valueOf(borrower.getDateOfBirth()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Borrower borrower) {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BORROWER)) {
            preparedStatement.setString(1, borrower.getName());
            preparedStatement.setInt(2, borrower.getTel());
            preparedStatement.setString(3, borrower.getCin());
            preparedStatement.setDate(4, Date.valueOf(borrower.getDateOfBirth()));
            preparedStatement.setInt(5, borrower.getId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String cin) {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BORROWER)) {
            preparedStatement.setString(1, cin);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Borrower> show() {
        List<Borrower> borrowers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BORROWER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int tel = Integer.parseInt(resultSet.getString("tel"));
                String cin = resultSet.getString("cin");
                String dateOfBirth = resultSet.getString("date_of_birth");
                Borrower borrower = new Borrower(id, name, tel, cin, dateOfBirth);
                borrowers.add(borrower);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return borrowers;
    }


}
