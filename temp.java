import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.*;

/*<applet code="Choose attributes for an ordered pizza :)" width=500 height=50>
</applet>*/
public class temp extends JApplet implements ActionListener,ItemListener {

	JTextField jtf1;
	JLabel jlbl1;
	JLabel jlbl2;
	JLabel jlbl3;
	JLabel jlbl4;
	JLabel jlbl5;
	String name;
	String size;
	String t;
	String tops[]={"Pepperoni","Jalapeno","Olives","Margherita","Salami","Corn"};
	JComboBox<String> jcb;
	int ec,cost=0;
	
	public void init()
	{
		try
		{
			SwingUtilities.invokeAndWait(
					new Runnable(){
						public void run(){
							makeGUI();
						}
					});
		}
		catch(Exception e)
		{
			System.out.println("Cant create cause of "+e);
		}
	}
	private void makeGUI()
	{
		setLayout(new FlowLayout());
		jlbl1=new JLabel("Enter your name");
		add(jlbl1);
		jtf1=new JTextField(30);
		add(jtf1);
		jtf1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				name=new String(jtf1.getText());
			}
			
		});
		jlbl2=new JLabel("Select size");
		add(jlbl2);
		JRadioButton b1=new JRadioButton("Personal");
		b1.addActionListener(this);
		add(b1);
		JRadioButton b2=new JRadioButton("Medium");
		b2.addActionListener(this);
		add(b2);
		JRadioButton b3=new JRadioButton("Large");
		b3.addActionListener(this);
		add(b3);
		ButtonGroup bg=new ButtonGroup();
		bg.add(b1);
		bg.add(b2);
		bg.add(b3);
		jlbl3=new JLabel("Select Topping");
		jcb=new JComboBox<String>(tops);
		add(jlbl3);
		add(jcb);
		jcb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				t=(String)jcb.getSelectedItem();
				if(t.equals("Pepperoni"))cost+=200;
				if(t.equals("Jalapeno"))cost+=200;
				if(t.equals("Olives"))cost+=210;
				if(t.equals("Margherita"))cost+=200;
				if(t.equals("Salami"))cost+=250;
				if(t.equals("Corn"))cost+=100;
			
		}});
		JCheckBox cb=new JCheckBox("Extra Cheese");
		cb.addItemListener(this);
		add(cb);
		JButton jb=new JButton("Place Order");
		jb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
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
					String pquery="insert into orderss values(?,?,?,?)";
					java.sql.PreparedStatement ps=conn.prepareStatement(pquery);
					ps.setString(1,name);
					ps.setString(2, size);
					ps.setString(3, t);
					ps.setInt(4, ec);
					jlbl5=new JLabel("");
					add(jlbl5);
					conn.setAutoCommit(false);
					Savepoint spt1 = conn.setSavepoint("svpt1");
					ps.executeUpdate();
					jlbl5.setText("Total Price is "+cost);
					
					Scanner sc=new Scanner(System.in);
					String s=sc.nextLine();
					if(s.equals("Cancel order"))
					{
						 conn.rollback(spt1);
						 
					      
					}
					else
					{
					conn.setAutoCommit(true);
					ps.close();
					conn.close();
					}
				}
				catch(Exception e)
				{
					System.out.println("DB here "+e);
				}
				
							
			
			
			
		}});
        add(jb);
        

		
	}
		public void actionPerformed(ActionEvent ae)
			{
				
				size=new String(ae.getActionCommand());
				if(size.equals("Personal"))cost+=100;
				if(size.equals("Medium"))cost+=150;
				if(size.equals("Large"))cost+=175;
				
				
				
				
			}
		public void itemStateChanged(ItemEvent ie)
		{
			JCheckBox cb=(JCheckBox)ie.getItem();
			if(cb.isSelected())
				{ec=1;
				cost+=50;
				
				
				}
			else
				ec=0;
		}
		
		
		
	
}
				
		
		
		
	


