package com.example.timelogger;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class dbConnect extends LoginActivity{
	
	Context context;
	public String loginGood="nothing";
	
	public dbConnect(Context context)
	{
		this.context=context;
	}

	public void runConnect()
	{
		new ConnectTask().execute();
	}
	
	public void runCheck(String employeeID, String employeePassword)
	{
		Log.e("getting", "here");
		new CheckTask().execute(employeeID,employeePassword);
		
	}

	public String checkLoginCredentials(String employeeID, String employeePassword){
		final String url = "jdbc:mysql://swe2313.com:3306/";
        final String dbName = "swe2313_db01";
        final String driver = "com.mysql.jdbc.Driver";
        final String userName = "webapp01swe2313";
        final String password = "ajV5615wefsZX8";
        //String employeeID = "222222";//what user enters
        //String employeePassword = "222222";//what user enters
        int locationId= 2;//what user enters
        ArrayList <String> users = new ArrayList<String>();
        try {
     
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url+dbName,userName,password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select employee_id from employee");
        while(rs.next()){
        	users.add(rs.getString("employee_id"));
        }
        if(users.contains(employeeID))
         rs = stmt.executeQuery("select password from employee where employee_id = "+ employeeID);
        rs.next();
        Log.e("credentials", "1");
        if(rs.getString("password").equals(employeePassword)){
        	Log.e("credentials", "2");
        	System.out.println("Logged in Successfully!");
        	stmt.close();
            conn.close();
            Log.e("credentials", employeeID + " " + locationId);
            
        	return employeeID + " " + locationId ;
        	
        }
       
        stmt.close();
        conn.close();
        } 
        catch(NullPointerException npe)
        {
        	npe.printStackTrace();
        }
        catch (Exception e) {
        e.printStackTrace();
        }
        
		return null;
	}
	
	
	
	public void  connect (){
		  String url = "jdbc:mysql://swe2313.com:3306/";
        String dbName = "swe2313_db01";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "webapp01swe2313";
        String password = "ajV5615wefsZX8";
        try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(url+dbName,userName,password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from employee");
        while(rs.next()){
      	  int employeeID = rs.getInt("employee_id");
      	  String firstName = rs.getString("first_name");
      	  String lastName = rs.getString("last_name");
      	  float payRate = rs.getFloat("pay_rate");
      	  String employeePassword = rs.getString("password");
      	  Log.e("data",employeeID + " "+ firstName + " "+ lastName + " "+ payRate+ " "+ employeePassword );
        }
        stmt.close();
        conn.close();
        System.out.println("successfully connected!");
        } 
        catch(MySQLNonTransientConnectionException e)
        {
      	  e.printStackTrace();
        }
        catch (Exception e) {
        e.printStackTrace();
        }
        }
	
	
	public class ConnectTask extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... params) {
        	
        	connect();
            return null;
        }

        @Override
        public void onPostExecute(String result) {
        }

        @Override
        public void onPreExecute() {
        }

        @Override
        public void onProgressUpdate(Void... values) {
        }
    }
	
	public class CheckTask extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... params) {
        	
        	return checkLoginCredentials(params[0],params[1]);
            //return loginGood;
        }

        @Override
        public void onPostExecute(String result) {
        	
        	loginGood=result;
        	//Log.e("result", loginGood);
        	
        		
        	
        	
        }

        @Override
        public void onPreExecute() {
        }

        @Override
        public void onProgressUpdate(Void... values) {
        	
        }
    }

	public static void dataForWeeklyLog(String credentials)
	{
		final String url = "jdbc:mysql://swe2313.com:3306/";
        final String dbName = "swe2313_db01";
        final String driver = "com.mysql.jdbc.Driver";
        final String userName = "webapp01swe2313";
        final String password = "ajV5615wefsZX8";
        	  String clockIn = "12:22:21"; //will be stored in application until clock out
        	  String clockOut = "12:22:31"; // once clocked out, will execute insert query to store all information of work day
        	  String hoursWorked = "00:00:10";
        	  String info = credentials;
        	  int employeeUser;
        	  int locationId;
        	  
        	   
        	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        	  Calendar cal = Calendar.getInstance();
        	  employeeUser=Integer.parseInt(info.substring(0, info.indexOf(' ')));
        	  locationId=Integer.parseInt(info.substring(info.indexOf(' ')+1));
        	  
        	  try 
        {
            
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url+dbName,userName,password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("insert into employee_work_day values(%d, %d, '%s', '%s', '%s', %d, %d, %d)",employeeUser, locationId, clockIn, clockOut, hoursWorked,Calendar.MONTH, Calendar.DATE, Calendar.YEAR));
            
            stmt.close();
            conn.close();
        }
        	
        	
        	  catch (Exception e)
            {
                e.printStackTrace();
            }
        	
        
	}
	
		public static void displayLog(String credentials)
	{
			final String url = "jdbc:mysql://swe2313.com:3306/";
	        final String dbName = "swe2313_db01";
	        final String driver = "com.mysql.jdbc.Driver";
	        final String userName = "webapp01swe2313";
	        final String password = "ajV5615wefsZX8";
	        String info = credentials;
	        double payRate;
	        double amountMade;
        	String clockIn = "";
      	  	String clockOut = "";
      	  	String hoursWorked = "";
      	  	int locationId;
      	  	int employeeId;
	        int employeeUser=Integer.parseInt(info.substring(0, info.indexOf(' ')));
	        try 
	        {
	            
	            Class.forName(driver).newInstance();
	            Connection conn = DriverManager.getConnection(url+dbName,userName,password);
	            Statement stmt = conn.createStatement();
	            PreparedStatement pstmt = null;
	            String sql = "SELECT employee.pay_rate, "
	            					+ "employee_work_day.clock_in, " 
	            					+ "employee_work_day.clock_out, " 
	            					+ "employee_work_day.location_id, " 
	            					+ "(employee_work_day.employee_id), "
	            					+ "employee_work_day.total_hours_worked "
	            				 + "FROM (swe2313_db01.employee_work_day employee_work_day "
	            				 + "INNER JOIN swe2313_db01.locations locations "
	            				 + "ON (employee_work_day.location_id = locations.location_id)) "
	            				 + "INNER JOIN swe2313_db01.employee employee "
	            				 + "ON (employee_work_day.employee_id = employee.employee_id) "
	            					+ "where employee_work_day.employee_id = " + employeeUser  
	            					+ " order by employee_work_day.employee_id DESC "
	            					+ "Limit 7";
	           
	            pstmt = conn.prepareStatement(sql);
	            ResultSet rs = pstmt.executeQuery(sql);
	            rs.last();
	            int max = rs.getRow();
	            rs = pstmt.executeQuery(sql);
	            for(int i = 0; i<max; i++)
	            {
	            	rs.next();
	            	payRate = rs.getFloat("pay_rate");
	            	clockIn = rs.getString("clock_in");
	          	  	clockOut = rs.getString("clock_out");
	          	  	hoursWorked = rs.getString("total_hours_worked");
	          	  	locationId = rs.getInt("location_id");
	          	  	employeeId = rs.getInt("employee_id");
	          	  	rs = stmt.executeQuery(String.format("select address_line_1 from locations where location_id = %d",locationId ));
	          	  	rs.next();
	          	  	String address = rs.getString("address_line_1");
	          	  	amountMade = 10.50; // amount made is whatever hoursWorked * payRate is
	          	  	System.out.println(employeeId +" "+ address +" "+ payRate +" "+ clockIn +" "+ clockOut +" "+ hoursWorked +" "+ amountMade);
	          	  	rs = pstmt.executeQuery(sql);
	          	  		for(int h = 0; h<i+1; h++)
	          	  			{
	          	  				rs.next();	
	          	  			}
	            }
	            stmt.close();
	            conn.close(); 
	        }
	        	catch (Exception e)
            {
                e.printStackTrace();
            }
	        
	}

	}


	
	

