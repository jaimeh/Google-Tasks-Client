package com.redditandroiddevelopers.googletasksclient;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.preference.PreferenceManager;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;




import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import com.google.api.services.tasks.TasksRequest;
import com.google.api.services.tasks.model.Task;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTaskActivity extends SherlockActivity{

    /** Logging level for HTTP requests/responses. */
    private static final Level LOGGING_LEVEL = Level.OFF;

    private static final String TAG = "TasksSample";

    // This must be the exact string, and is a special for alias OAuth 2 scope
    // "https://www.googleapis.com/auth/tasks"

    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    
    final JsonFactory jsonFactory = new JacksonFactory();

    

    GoogleAccountManager accountManager;

    

    String accountName;

    GoogleCredential credential = new GoogleCredential();

    com.google.api.services.tasks.Tasks service;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       
        setContentView(R.layout.addtask);
        
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
        
     
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
       //add menu items to the action bar. look on onOptionsItemSelected to action code.
        
        menu.add(0,0,0,"Save"); 
        menu.add(0,1,0,"Cancel"); 
           
      
        return true;
    }
    
   
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        
        switch(item.getItemId())
        {
            case 0:
            {
            
          
             //add task
            new addTask().execute();
             
           
            return true;
            }
            case 1:
            {
           Toast.makeText(this, "Search selected",
                     Toast.LENGTH_SHORT).show();
            return true;
            }
          
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }
    //add task module
 class addTask extends AsyncTask<String, Void, AddTaskActivity> {

       
        
        protected AddTaskActivity doInBackground(String... urls) {
            final EditText titleText = (EditText) findViewById(R.id.editTitle);
            final EditText noteText = (EditText) findViewById(R.id.editNote);
            
            Task task = new Task();
          
            task.setTitle(titleText.getText().toString());
            task.setNotes(noteText.getText().toString());
            

            
            try {
                Task result = service.tasks().insert("@default", task).execute();
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //close addTask Activity
            finish();
            return null;
            
            
        }


      
     }
}