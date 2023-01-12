package com.mayikt.dao;

import com.mayikt.entity.UserEntity;
import com.mayikt.utils.MayiktJdbcUtils;

import java.sql.*;

public class UserDao2 {
    /**
     * register user
     * think: what if user use existed phone number and register again?
     */
    public int registerUser(UserEntity userEntity){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = MayiktJdbcUtils.getConnection();

            connection.setAutoCommit(false);            // start jdbc transaction

            statement = connection.createStatement();
            String insertUserSql = "INSERT INTO mayikt_users VALUES("+userEntity.getId()+", '"+
                    userEntity.getPhone()+"', '"+userEntity.getPwd()+"')";
            System.out.println("insertSQL: "+insertUserSql);
            int result = statement.executeUpdate(insertUserSql);
            // pretend bugs in the code
            int j=1/1;
            connection.commit(); // submit the transaction
            return result;
        }catch (Exception e){
            e.printStackTrace();
            try {
                connection.rollback(); //rollback the transaction
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return 0;
        }finally {
            MayiktJdbcUtils.closeConnection(statement,connection);
        }

    }

    /**
     * get user info using phone number
     * @param phone
     * @return
     */
    public UserEntity getUserByPhone(String phone){
        if(phone==null || phone.length()==0){
            return null;
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet=null;
        try{
            connection = MayiktJdbcUtils.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select * from mayikt_users where phone='"+phone+"'");
            boolean result = resultSet.next();
            if(!result){
                return null;
            }else{
                Long dbId = resultSet.getLong("id");
                String dbPhone = resultSet.getString("phone");
                String dbPwd = resultSet.getString("pwd");
                UserEntity userEntity = new UserEntity(dbId, dbPhone, dbPwd);
                return userEntity;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            MayiktJdbcUtils.closeConnection(resultSet,statement,connection);
        }
    }

    /**
     *  User login (fixed sql injection vulnerability)
     * @param userEntity
     * @return
     */
    public UserEntity login(UserEntity userEntity){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet=null;
        try{
            connection = MayiktJdbcUtils.getConnection();
            //fixed sql injection vulnerability
            String loginSql ="select * from mayikt_users where phone=? and pwd=?;";
            statement=connection.prepareStatement(loginSql);
            statement.setString(1, userEntity.getPhone());
            statement.setString(2, userEntity.getPwd());
            resultSet=statement.executeQuery(loginSql);
            boolean result = resultSet.next();
            if(!result){
                return null;
            }else{
                Long dbId = resultSet.getLong("id");
                String dbPhone = resultSet.getString("phone");
                String dbPwd = resultSet.getString("pwd");
                UserEntity user1 = new UserEntity(dbId, dbPhone, dbPwd);
                return user1;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            MayiktJdbcUtils.closeConnection(resultSet,statement,connection);
        }
    }
}
