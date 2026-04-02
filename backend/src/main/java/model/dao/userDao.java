package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import error.NoConnection;
import model.dto.User;
import model.utils.DBConnection;

public class UserDao {

    public static Optional<User> getUserByLoginPassword(String login, String psw) throws NoConnection, SQLException {
        Connection conn = DBConnection.getConnection();

        String querry = """
                SELECT * FROM users WHERE login = ? AND password_hash = ?;
                """;
        PreparedStatement pstmt = conn.prepareStatement(querry);
        pstmt.setString(1, login);
        pstmt.setString(2, psw);

        ResultSet rs = pstmt.executeQuery();
        if(!rs.next()) {
            return Optional.empty();
        }
        return Optional.of(
                new User(
                    rs.getInt("id"),
                    rs.getString("login"),
                    rs.getString("password_hash")
                )
            );
    }

    public static Optional<User> insertUser(User user) throws NoConnection, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertUser'");
    }
    
}
