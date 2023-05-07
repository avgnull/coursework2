package utils;

import model.Driver;
import model.Manager;
import model.User;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Objects;

public class DbUtils {
    public static Connection connectToDb() throws ClassNotFoundException {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        String DB_URL = "jdbc:mysql://localhost/course_work_hibernate";
        String USER = "root";
        String PASS = "";
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void disconnect(Connection connection, Statement statement){
        try{
            if(connection!=null&&statement!=null){
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User validateUser(String login, String psw) throws SQLException, ClassNotFoundException {
        Connection connection = connectToDb();
        String sql = "SELECT * FROM user WHERE login = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, psw);
        ResultSet rs = preparedStatement.executeQuery();
        User user = null;
        while (rs.next()){
            if(Objects.equals(rs.getString(1), "Driver")){
                user = new Driver(rs.getString("login"),rs.getString("password"),rs.getString("name"),rs.getString("surname"),rs.getDate("birthDate").toLocalDate(),rs.getString("phoneNum"),rs.getDate("medCertificateDate").toLocalDate(), rs.getString("medCertificateNumber"),rs.getString("driverLicense"));
            }else{
                user = new Manager(rs.getString("login"),rs.getString("password"),rs.getString("name"),rs.getString("surname"),rs.getDate("birthDate").toLocalDate(),rs.getString("phoneNum"),rs.getString("email"),rs.getBoolean("isAdmin"));
            }
        }

        disconnect(connection, preparedStatement);
        return user;
    }

}
