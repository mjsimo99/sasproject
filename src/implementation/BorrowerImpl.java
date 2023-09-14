package implementation;

import dto.Borrower;
import helper.DatabaseConnection;
import interfaces.IBorrower;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowerImpl implements IBorrower {

    private static final String ADD_BORROWER = "INSERT INTO borrowers (name, tel, cin) VALUES (?, ?, ?)";
    private static final String UPDATE_BORROWER = "UPDATE borrowers SET name = ?, tel = ?, cin = ? WHERE id = ?";
    private static final String DELETE_BORROWER = "DELETE FROM borrowers WHERE cin = ?";
    private static final String SELECT_BORROWER = "SELECT * FROM borrowers";

    @Override
    public void add(Borrower borrower) {
        Connection connection = DatabaseConnection.getConn();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_BORROWER)) {
            preparedStatement.setString(1, borrower.getName());
            preparedStatement.setInt(2, borrower.getTel());
            preparedStatement.setString(3, borrower.getCin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Borrower borrower) {
        Connection connection = DatabaseConnection.getConn();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BORROWER)) {
            preparedStatement.setString(1, borrower.getName());
            preparedStatement.setInt(2, borrower.getTel());
            preparedStatement.setString(3, borrower.getCin());
            preparedStatement.setInt(4, borrower.getId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String cin) {
        Connection connection = DatabaseConnection.getConn();
        try (
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
        Connection connection = DatabaseConnection.getConn();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BORROWER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int tel = Integer.parseInt(resultSet.getString("tel"));
                String cin = resultSet.getString("cin");
                Borrower borrower = new Borrower(id, name, tel, cin);
                borrowers.add(borrower);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return borrowers;
    }
    @Override
    public Borrower getBorrowerById(int borrowerId) {
        Connection connection = DatabaseConnection.getConn();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM borrowers WHERE id = ?")) {
            preparedStatement.setInt(1, borrowerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String borrowerName = resultSet.getString("name");
                int telephoneNumber = resultSet.getInt("tel");
                String cin = resultSet.getString("cin");

                return new Borrower(borrowerId, borrowerName, telephoneNumber, cin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
