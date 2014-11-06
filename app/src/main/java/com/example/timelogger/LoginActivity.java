package com.example.timelogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.timelogger.dbConnect.CheckTask;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends ActionBarActivity {

	Button login;
	EditText id;
	EditText pass;
	public String loginGood="nothing";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);
		login =(Button) findViewById(R.id.button1);
		id=(EditText)findViewById(R.id.editText1);
		pass=(EditText)findViewById(R.id.editText2);
		login.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final dbConnect connector=new dbConnect(LoginActivity.this);
						Log.e("login", "ff");
						
						new CheckTask().execute(id.getText().toString(), pass.getText().toString());
						Log.e("login", "here");
				
						        	 /*if(!connector.loginGood.equals("nothing")){
											Log.e("login", connector.loginGood);
											startActivity(new Intent(LoginActivity.this,MainActivity.class));}*/
				
						    
						
						
					}
					
				});
		/*if (savedInstanceState == null) {
			showFragment();
		}*/
	}
	
	/*public void showFragment() {
       
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.layout.fragment_login) == null) {
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right_custom, R.anim.abc_fade_out);
            transaction.replace(R.id.container,new PlaceholderFragment());
            transaction.commit();
        }
    }*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*public static class PlaceholderFragment extends Fragment {

		Button login;
		EditText id;
		EditText pass;
		
		public PlaceholderFragment() {
		}
		
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			login =(Button) rootView.findViewById(R.id.button1);
			id=(EditText)rootView.findViewById(R.id.editText1);
			pass=(EditText)rootView.findViewById(R.id.editText2);
			login.setOnClickListener(new OnClickListener()
					{

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							final dbConnect connector=new dbConnect(getActivity());
							Log.e("login", "ff");
							connector.runCheck(id.getText().toString(), pass.getText().toString());
							Log.e("login", "here"+connector.loginGood);
					
							        	 if(!connector.loginGood.equals("nothing")){
												Log.e("login", connector.loginGood);
												startActivity(new Intent(getActivity(),MainActivity.class));}
					
							    
							
							
						}
						
					});
			
			return rootView;
		}
		
		

		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
		}
		
		
	}*/
	public void goToNextActivity()
	{
		startActivity(new Intent(LoginActivity.this,MainActivity.class));
	}
	
	public class CheckTask extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... params) {
        	Log.e("here", "3");
        	return checkLoginCredentials(params[0],params[1]);
            //return loginGood;
        }

        @Override
        public void onPostExecute(String result) {
        	Log.e("here", "4");
        	loginGood=result;
        	//Log.e("result", loginGood);
        	if(!result.equals(""
        			+ "nothing"))
        	goToNextActivity();
        		
        	
        	
        }

        @Override
        public void onPreExecute() {
        }

        @Override
        public void onProgressUpdate(Void... values) {
        	
        }
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
        
		return "nothing";
	}

}
