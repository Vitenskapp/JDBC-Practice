package application;

import db.DB;
import db.DbException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {

    public static void main(String[] args) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DB.getConnection();
            /*
            preparedStatement = connection.prepareStatement(
                 "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, "Vinicius Alves");
            preparedStatement.setString(2, "viniciusalves@gmail.com");
            preparedStatement.setDate(3, new java.sql.Date(simpleDateFormat.parse("22/04/1985").getTime()));
            preparedStatement.setDouble(4, 3000.0);
            preparedStatement.setInt(5, 2);
            */

            preparedStatement = connection.prepareStatement("INSERT INTO department (Name) values ('D1'), ('D2')",
                    Statement.RETURN_GENERATED_KEYS
            );

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                while(resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println("Id = " + id);
                }
                System.out.println("Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows affected");
            }

        } catch(SQLException sqlException) {
            throw new DbException(sqlException.getMessage());
        } /* catch (ParseException e) {
            throw new RuntimeException(e);
        }*/ finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }

}