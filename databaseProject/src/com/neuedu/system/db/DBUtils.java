/**
 *  数据库连接管理类
 */
package com.neuedu.system.db;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtils
{
    //驱动串路径
    private static String driver=null;
    //描述数据库的网络标识,以及数据库名称,打开数据库时候所用的编码格式等
    private static String url=null;
    private static String username=null;
    private static String password=null;
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
    /**
     * 类中方法均为static时，将构造函数设为private
     */
    private DBUtils(){}

    /**
     * 静态块，在第一次载入内存时执行
     */
    static
    {
        try{
            //获取资源文件
            ResourceBundle bundle=ResourceBundle.getBundle("DBOptions");
            //根据资源文件按名取值（资源文件是有名无序的）
            driver=bundle.getString("driver");
            url=bundle.getString("url");
            username=bundle.getString("username");
            password=bundle.getString("password");

            Class.forName(driver);
            System.out.println("Loading driver successfully!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**-----------------------------------------------------------------------------------
     * Connection Statement ResultSet处理
     -------------------------------------------------------------------------------------*/

    private static Connection getConnection() throws Exception{

        Connection conn=threadLocal.get();
        if(conn==null){
            conn = DriverManager.getConnection(url, username, password);
            threadLocal.set(conn);
        }
        return conn;
    }

    public static PreparedStatement prepareStatement(String sql) throws Exception{
        return DBUtils.prepareStatement(sql,false);
    }

    /**
     * 重载，对于需要返回主键值的PreparedStatement调用此方法
     * @param sql
     * @param bool
     * @return
     * @throws Exception
     */
    public static PreparedStatement prepareStatement(String sql,boolean bool)throws Exception{
        if(bool){
            return DBUtils.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        }
        else {
            return DBUtils.getConnection().prepareStatement(sql);
        }
    }

    /**
     * 关闭自动提交
     * @throws Exception
     */
    public static void closeAutoCommit() throws Exception{
        DBUtils.getConnection().setAutoCommit(false);
    }

    /**
     * 开启自动提交
     * @throws Exception
     */
    public static void startAutoCommit() throws Exception{
        DBUtils.getConnection().setAutoCommit(true);
    }

    /**
     * 提交
     * @throws Exception
     */
    public static void commit()throws Exception{
        DBUtils.getConnection().commit();
    }

    /**
     * 回滚
     */
    public static void rollback(){
        try {
            DBUtils.getConnection().rollback();
        }catch (Exception e){
            try {
                DBUtils.getConnection().rollback();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**------------------------------------------------------------------------------------
     * 关闭数据库连接
     -------------------------------------------------------------------------------------*/

    public static void close() throws Exception{
        threadLocal.set(null);
        DBUtils.getConnection().close();
    }

    /**
     * 重载，用于销毁连接数据库时创建的对象
     * @param conn
     */
    public static void close(Connection conn){
        try
        {
            if(conn!=null)
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement pstm){
        try{
            if(pstm!=null){
                pstm.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
