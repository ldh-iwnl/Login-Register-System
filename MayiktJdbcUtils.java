package com.mayikt.utils;
import java.sql.ResultSet;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class MayiktJdbcUtils {
    /**
     * 1. 将我们的构造方法私有化 -- 工具类不需要new出来
     */
    private MayiktJdbcUtils(){
    }
    /**
     * 2. 定义工具 需要声明变量
     */
    private static String driverClass;
    private static String url;
    private static String user;
    private static String password;
    /**
     * 3. 使用静态代码块 来给我们声明好的jdbc赋值
     */
    static{
      try{
          // 读取config.properties 文件 IO 路径用相对路径
          InputStream resourceAsStream = MayiktJdbcUtils.class.getClassLoader().
                  getResourceAsStream("config.properties");
          //赋值给声明好的变量
          Properties properties = new Properties();
          properties.load(resourceAsStream);
          driverClass = properties.getProperty("driverClass");
          url = properties.getProperty("url");
          user = properties.getProperty("user");
          password = properties.getProperty("password");
          //注册驱动类
      }catch (Exception e){
          e.printStackTrace();
      }
    }
    /**
     * 4. 封装连接方法
     */
    public static Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 5. 封装释放链接方法
     */
    public static void closeConnection(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if (resultSet != null)
                resultSet.close();
            if(statement!=null)
                statement.close();
            if(connection!=null)
                connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void closeConnection(Statement statement, Connection connection){
        closeConnection(null, statement, connection);
    }

    public static void main(String[] args) {
        System.out.println(MayiktJdbcUtils.driverClass);
    }
}
