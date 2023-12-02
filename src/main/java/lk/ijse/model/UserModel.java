package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            if(id >= 10){
                return "L0" + id;
            }else if(id >= 100){
                return "L" + id;
            }
            return "L00" + id;
        }
        return "L001";
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

    public List<UserDTO> getAllAdmins() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user where role = 'ADM'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<UserDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String userName = resultSet.getString(1);
            String pwd = resultSet.getString(2);
            String email = resultSet.getString(3);
            String roll = resultSet.getString(4);

            var dto = new UserDTO(userName, pwd, email, roll);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public String getPassword(String userName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT password FROM user WHERE userName = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userName);

        ResultSet resultSet = pstm.executeQuery();

        String password = null;

        while (resultSet.next()) {
            password = resultSet.getString(1);
        }
        return password;
    }

    public boolean checkAdmin(String userName, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user where userName = ? and password = ? and role = 'ADM'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        //List<UserDTO> dtoList = new ArrayList<>();
        pstm.setString(1, userName);
        pstm.setString(2, password);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String user_Name = resultSet.getString(1);
            String pwd = resultSet.getString(2);
            String email = resultSet.getString(3);
            String roll = resultSet.getString(4);

            var dto = new UserDTO(user_Name, pwd, email, roll);
            //dtoList.add(dto);
            System.out.println("dto :  "+dto);

            if(dto.equals(null)){
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean deleteAdmin(String userName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM user WHERE userName = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userName);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateAdmin(UserDTO userDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE user SET password = ?, email = ?, role = ? WHERE userName = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userDto.getPassword());
        pstm.setString(2, userDto.getEmail());
        pstm.setString(3, userDto.getRole());
        pstm.setString(4, userDto.getUserName());

        return pstm.executeUpdate()>0;
    }

    public boolean saveAdmin(UserDTO userDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?)");

        pstm.setString(1, userDto.getUserName());
        pstm.setString(2, userDto.getPassword());
        pstm.setString(3, userDto.getEmail());
        pstm.setString(4, userDto.getRole());

        return pstm.executeUpdate() > 0;
    }

    public boolean isSuperAdm(String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT password FROM user WHERE userName = 'Sadmin'");

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String pwd = resultSet.getString(1);

            if(pwd.equals(password)){
                return true;
            }
        }
        return false;
    }

    public boolean checkUserName(String userName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user where userName = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userName);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String user_Name = resultSet.getString(1);
            String pwd = resultSet.getString(2);
            String email = resultSet.getString(3);
            String roll = resultSet.getString(4);

            var dto = new UserDTO(user_Name, pwd, email, roll);

            System.out.println("dto :  "+dto);

            if(dto.equals(null)){
                return false;
            }
        }
        return true;
    }

    public String getEmail(String userName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT email FROM user WHERE userName = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userName);

        ResultSet resultSet = pstm.executeQuery();

        String email = null;

        while (resultSet.next()) {
            email = resultSet.getString(1);
        }
        return email;
    }

    public boolean changePwd(String confirmPwd, String userName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE user SET password = ? WHERE userName = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, confirmPwd);
        pstm.setString(2, userName);

        return pstm.executeUpdate()>0;
    }
}
