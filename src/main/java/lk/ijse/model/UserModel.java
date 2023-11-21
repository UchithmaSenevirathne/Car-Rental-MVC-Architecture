package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public static boolean searchUser(String userName, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE userName = ? AND password = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static String generateNextLogId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT logId FROM login ORDER BY logId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentLogId = null;

        if (resultSet.next()) {
            currentLogId = resultSet.getString(1);
            return splitLogId(currentLogId);
        }
        return splitLogId(null);
    }

    private static String splitLogId(String currentLogId) {
        if (currentLogId != null) {
            String[] split = currentLogId.split("L");
            int id = Integer.parseInt(split[1]);
            id++;
            return "L" + id;
        }
        return "L1";
    }

    public static boolean saveLogin(String logId, String userName, String date, String time) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO login VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, logId);
        pstm.setString(2, userName);
        pstm.setString(3, date);
        pstm.setString(4, time);

        return pstm.executeUpdate() > 0;
    }
}
