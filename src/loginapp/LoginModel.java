package loginapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbUtil.dbConnection;

//This is mvc model view class
// we are going to use this in our login controller class to fetch data.

public class LoginModel {
    Connection connection;

    public LoginModel() {
        //this is just to make sure that we are connected to database.
        try
        {
            this.connection=dbConnection.getConnection();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        if(this.connection==null)
        {
            System.exit(1);
        }
    }

    public  boolean isDatabaseConnected()
    {
        return this.connection !=null;
    }

    public  boolean isLogin(String user, String pass,String opt) throws Exception
    {
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM login where username = ? and password = ? and divison = ? ";
        try
        {

            pr = this.connection.prepareStatement(sql);
            pr.setString(1,user);
            pr.setString(2,pass);
            pr.setString(3,opt);

            rs=pr.executeQuery();

            boolean boll1;

            if(rs.next())
            {
                return true;
            }
            return false;
        }
        catch (SQLException ex)
        {
            return false;
        }
        finally {

                pr.close();
                rs.close();
        }
    }
}
