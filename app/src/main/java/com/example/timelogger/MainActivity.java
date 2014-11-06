package com.example.timelogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	static SharedPreferences sharedPref;
	static SharedPreferences.Editor editor;
	
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		editor = sharedPref.edit();
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			if(position ==0)
			return PlaceholderFragment.newInstance(position + 1);
			else
				return PlaceholderFragmentTwo.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		boolean clicked = false;
		Button clock;
		static int sectionNumberClass;
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			final Time now = new Time();
			
			now.setToNow();
			
			clock = (Button) rootView.findViewById(R.id.button1);
			
			
			
			if(sharedPref.getString("clockInTime","00:00:00").equals("nothing")||!sharedPref.contains("clockInTime")||sharedPref==(null))
			{
				clock.setText("Clock In");
			}
			else
			{
				clock.setText("Clock Out");
				clicked=true;
			}
			
			clock.setOnClickListener(new OnClickListener()
					{
				 
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String clockInTime;
							String clockOutTime;
							String timeWorked;
							if(!clicked){
								clockInTime=getTime();
								showSimpleDialog(getActivity(),"Clock In Successful","You successfully clocked in at " + clockInTime);
								clock.setText("Clock Out");
								clicked=true;
								editor.putString("clockInTime", clockInTime);
								editor.commit();
								//new dbConnect(getActivity()).runConnect();
								
							
						}
						else{
							clockInTime=sharedPref.getString("clockInTime","00:00:00");
							clockOutTime=getTime();
							timeWorked=getTimeWorked(clockInTime,clockOutTime);		
							showSimpleDialog(getActivity(),"Clock Out Successful","You worked from " + clockInTime+" to "+clockOutTime+" for a total of "+timeWorked);
							clock.setText("Clock In");
							clicked=false;
							editor.putString("clockInTime", "nothing");
							editor.commit();
												
						}
						}

						
						
					});
			return rootView;
		}
	}

	public static class PlaceholderFragmentTwo extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER_TWO = "section_number_two";
		boolean clicked = false;
		Button clock;
		static int sectionNumberClass;
		ListView listView;
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragmentTwo newInstance(int sectionNumber) {
			PlaceholderFragmentTwo fragment = new PlaceholderFragmentTwo();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER_TWO, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragmentTwo() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_tabtwo, container,
					false);
			 listView = (ListView) rootView.findViewById(R.id.listView1);
			 String[] values = new String[] { "Sunday", 
                     "Monday",
                     "Tuesday",
                     "Wednesday", 
                     "Thursday", 
                     "Friday", 
                     "Saturday"
                    };
			  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		              android.R.layout.simple_list_item_1, android.R.id.text1, values);
		    
		    
		            // Assign adapter to ListView
		            listView.setAdapter((ListAdapter)adapter); 
		            
		            // ListView Item Click Listener
		            listView.setOnItemClickListener(new OnItemClickListener() {
		 
		                  @Override
		                  public void onItemClick(AdapterView<?> parent, View view,
		                     int position, long id) {
		                    
		                   // ListView Clicked item index
		                   int itemPosition     = position;
		                   
		                   // ListView Clicked item value
		                   String  itemValue    = (String) listView.getItemAtPosition(position);
		                      
		                    // Show Alert 
		                    Toast.makeText(getActivity(),
		                      "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
		                      .show();
		                 
		                  }
		    
		             }); 
		
			return rootView;
		}
	}
	
	public static String getTime()
	{
		String myTime;
		Time now=new Time();
	    now.setToNow();
		String hour;
		String minute;
		String second;
		int hr=now.hour;
		int min=now.minute;
		int sec=now.second;
		hour=now.hour+"";
		minute=now.minute+"";
		second=now.second+"";
		if(hr>12)
		hour=(now.hour-12)+"";
		if(hour.length()==1)
		hour="0"+hour;
		if(minute.length()==1)
			minute="0"+minute;
		if(second.length()==1)
			second="0"+second;
		if(hr>12)
			myTime=hour+":"+minute+":"+second+" pm";
		else
			myTime=hour+":"+minute+":"+second+" am";
		return myTime;
		
	}
	
	public static String getTimeWorked(String clockInTime,
			String clockOutTime) {
		// TODO Auto-generated method stub
		int clockInHour = Integer.parseInt(clockInTime.substring(0, 2));
		int clockOutHour = Integer.parseInt(clockOutTime.substring(0, 2));
		int clockInMinute = Integer.parseInt(clockInTime.substring(3, 5));
		int clockOutMinute = Integer.parseInt(clockOutTime.substring(3, 5));
		int clockInSecond = Integer.parseInt(clockInTime.substring(6,8));
		int clockOutSecond = Integer.parseInt(clockOutTime.substring(6,8));
		int hourDifference;
		int minuteDifference;
		int secondDifference;
		String hour;
		String minute;
		String second;
		
		
		hourDifference = clockOutHour-clockInHour;
		
		if(clockInMinute>clockOutMinute)
		{
			hourDifference--;
			minuteDifference=clockOutMinute+60-clockInMinute;
		}
		else
		{
			minuteDifference = clockOutMinute-clockInMinute;
		}
		if(clockInSecond>clockOutSecond)
		{
			minuteDifference--;
			secondDifference=clockOutSecond+60-clockInSecond;
		}
		else
		{
			secondDifference=clockOutSecond-clockInSecond;
		}
		
		hour=hourDifference+"";
		minute=minuteDifference+"";
		second=secondDifference+"";
		if(hour.length()==1)
		hour="0"+hour;
		if(minute.length()==1)
		minute="0"+minute;
		if(second.length()==1)
		second="0"+second;
		
		String timeWorked = hour+":"+minute+":"+second;
		
		return timeWorked;
	}
	
	@SuppressWarnings("deprecation")
	public static void showSimpleDialog(Context context, String title, String message)
	{
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
	    dialog.setTitle(title);
	    dialog.setMessage(message);
	    dialog.setButton("Ok",new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	            // here you can add functions

	           }
	    });
	    dialog.setButton2("Cancel",new DialogInterface.OnClickListener() {
	       public void onClick(DialogInterface dialog, int id) {
	        // here you can add functions
	               dialog.dismiss();
	    }
	    });
	    dialog.show();
	}
	
	
	public static void submitLog(String credentials)
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
	
		public static void weeklyLogData(String credentials)
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
