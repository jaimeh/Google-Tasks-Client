package com.redditandroiddevelopers.googletasksclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.Tasks.TasksOperations.Insert;
import com.google.api.services.tasks.TasksRequest;
import com.google.api.services.tasks.model.Task;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class detailTasks extends SherlockActivity{
	String title;
	String id;
	com.google.api.services.tasks.Tasks service;
	final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	    
	final JsonFactory jsonFactory = new JacksonFactory();
	
	GoogleCredential credential = new GoogleCredential();
	GoogleAccountManager accountManager;
	 
	private static final String TAG = "Reddit Google Tasks";
	private static final Level LOGGING_LEVEL = Level.OFF;
	 
	String accountName;
	AddTaskActivity newActivity;
	Task newTask;
	 
	String timeagoString;
	String notesString;
	String idNotes;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {

       //add menu items to the action bar. look on onOptionsItemSelected to action code.

        menu.add(0,0,0,"Delete note");
       

        return true;
    }
	
	 
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


		switch(item.getItemId())
        {
            case 0:
            {
                //delete notes
            	new deleteTask().execute();
            	finish();
           	return true;

            }
            default:
        		return super.onOptionsItemSelected(item);
        }

    }
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	        Bundle bundle = this.getIntent().getExtras();
	        title = bundle.getString("title");
	        id = bundle.getString("taskID");
	        
	        
	        getSupportActionBar().setTitle(title);
	        
	        setContentView(R.layout.detail);
	        service = com.google.api.services.tasks.Tasks.builder(transport, jsonFactory)
	        .setApplicationName("RedditGoogleTasks/1.0")
	        .setHttpRequestInitializer(credential)
	        .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {

	          public void initialize(JsonHttpRequest request) throws IOException {
	            TasksRequest tasksRequest = (TasksRequest) request;
	            tasksRequest.setKey(ClientCredentials.KEY);
	          }
	        })
	        .build();
	        
	        
	        //get the accountName and authToken from sharedPreferences settings
	          String PREF_ACCOUNT_NAME = settings.getString("accountName", null);
	          String PREF_AUTH_TOKEN = settings.getString("authToken", null);
	         
	          Log.v(TAG, "PREF_ACCOUNT_NAME =" + PREF_ACCOUNT_NAME);
	          Log.v(TAG, "PREF_AUTH_TOKEN =" + PREF_AUTH_TOKEN);
	          
	       
	          
	            accountName = PREF_ACCOUNT_NAME;
	            credential.setAccessToken(PREF_AUTH_TOKEN);
	            
	             Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);
	           
	             accountManager = new GoogleAccountManager(getApplicationContext());
	        
	             
	             new getTask().execute();
	       
	        
	
	 }
	 
	 public class getTask extends AsyncTask<Void, Void, Void> {
		 /*
		 protected void onPostExecute(Task result) {
	        	
			 }
		  */
		 ProgressBar progressView = (ProgressBar)findViewById(R.id.marker_progress);
		 TextView noteTextNew = (TextView)findViewById(R.id.noteText);	     
     	 TextView updatedTextNew = (TextView)findViewById(R.id.timeAgoText);
     	 
		 protected void onPostExecute(Void result) {

	        	//hide progressbar
	        	progressView.setVisibility(ProgressBar.GONE);
	        	
	        	//show notes element
	        	noteTextNew.setVisibility(TextView.VISIBLE);
				updatedTextNew.setVisibility(TextView.VISIBLE);
				
	        	updatedTextNew.setText(timeagoString);
	        	noteTextNew.setText(notesString);
		        return;

		    }
		 
		@Override
		protected Void doInBackground(Void... params) {
			//show progress bar
			 progressView.setVisibility(ProgressBar.VISIBLE);
			 
			 //hide notes element
			 noteTextNew.setVisibility(TextView.GONE);
			 updatedTextNew.setVisibility(TextView.GONE);
			 
            try {
                Task result = service.tasks().get("@default", id).execute();
                newTask = result;
            	Log.v("", "task = " + result.toString());
            	
            	Log.v("", "update time = " + result.getUpdated().toString());
            	Log.v("", "NOTES = " + result.getNotes()); 
 				
            	notesString = result.getNotes();
 				//use timeAgo twitter style date
 	            long longDate= result.getUpdated().getValue();
 	       		CharSequence timeago = DateUtils.getRelativeTimeSpanString(longDate);
 	       		
 	       		timeagoString = timeago.toString();
 	       		idNotes = result.getId();
 	       		//noteTextNew.setText(result.getNotes());
 	       		Log.v("", "update time = " + timeagoString);
 	       		
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			return null;
		}
	 }
	 
	 public class deleteTask extends AsyncTask<Void, Void, Void> {
	
	
		 
		@Override
		protected Void doInBackground(Void... params) {
			 
            try {
            	service.tasks().delete("@default", idNotes).execute();
	
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			return null;
		}
	 }
}
