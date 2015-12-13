
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.*;
public class Reader 
{
    public static void main(String args[])
	{
		Connection conn=null;
		String url="jdbc:mysql://localhost:3307/";
		String dbName="pizza";
		String driver="com.mysql.jdbc.Driver";
		String userName="root";
		String password="priyanka";
		
		
		try
		{ 
			Class.forName(driver).newInstance();
			conn=DriverManager.getConnection(url+dbName,userName,password);
			String query="select * from orderss";
			
			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=st.executeQuery(query);
			String temp1,temp2,temp3;
			int temp4;
			CallableStatement cs=null;
			String myp="{call ExtraCheesy()}";
			
		
			while(rs.next())
			{
				temp1=rs.getString(1);
				
				temp2=rs.getString(2);
			
				temp3=rs.getString(3);
				
				temp4=rs.getInt(4);
				
				
				System.out.println(temp1+"  "+temp2+"  "+temp3+"  "+temp4);
				
				
				
			}
			rs.previous();
			rs.updateString(1,"Harish");
			rs.updateRow();
			rs.last();
			rs.next();
			
			//rs.updateString(1,"Apple");
			//rs.updateString(2,"Personal");
			//rs.updateString(3,"Salami");
			//rs.updateInt(4, 1);
			
			//rs.insertRow();
			rs.first();
			rs.next();
			rs.deleteRow();
			
			cs=conn.prepareCall(myp);
			ResultSet rs2=cs.executeQuery();
			System.out.println("Customers who ordered extra cheese");
			while(rs2.next())
			{
				temp1=rs2.getString(1);
				
				System.out.println(temp1);
			}
			
			
			
			
		}
		catch(Exception e)
		{
			System.out.println("hello"+e);
		}
		
	}
	

}
