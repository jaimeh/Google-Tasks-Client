package com.redditandroiddevelopers.googletasksclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;

import com.google.api.client.http.apache.ApacheHttpTransport;

import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.Tasks.TasksOperations.Insert;
import com.google.api.services.tasks.TasksRequest;
import com.google.api.services.tasks.model.Task;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import com.redditandroiddevelopers.googletasksclient.ClientCredentials;



public class GoogleTasksClientActivity extends SherlockActivity {


	 /** Logging level for HTTP requests/responses. */
	  private static final Level LOGGING_LEVEL = Level.OFF;

	  private static final String TAG = "TasksSample";

	  // This must be the exact string, and is a special for alias OAuth 2 scope
	  // "https://www.googleapis.com/auth/tasks"
	  private static final String AUTH_TOKEN_TYPE = "Manage your tasks";

	  private static final int MENU_ACCOUNTS = 0;

	  private static final int REQUEST_AUTHENTICATE = 0;

	  final HttpTransport transport = AndroidHttp.newCompatibleTransport();

	  final JsonFactory jsonFactory = new JacksonFactory();

	  static final String PREF_ACCOUNT_NAME = "accountName";

	  static final String PREF_AUTH_TOKEN = "authToken";

	  GoogleAccountManager accountManager;

	  SharedPreferences settings;

	  String accountName;

	  GoogleCredential credential = new GoogleCredential();

	  com.google.api.services.tasks.Tasks service;

	  List<String> taskTitles = new ArrayList<String>();

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {

       //add menu items to the action bar. look on onOptionsItemSelected to action code.

        menu.add(0,0,0,"Add New Task");
        menu.add(0,1,0,"Search");
        menu.add(0,2,0,"Settings");
        menu.add(0,3,0,"Refresh");

        return true;
    }


	//to Download All List and add items to listview.
	class RetreiveTask extends AsyncTask<String, Void, GoogleTasksClientActivity> {

	    ListView listView = (ListView) findViewById(R.id.mylist);
	    @Override
		protected GoogleTasksClientActivity doInBackground(String... urls) {

		    try {
		      List<Task> tasks = service.tasks().list("@default").execute().getItems();
		      if (tasks != null) {
		        for (Task task : tasks) {
		          taskTitles.add(task.getTitle());
		          Log.v(TAG, "HELLO" + task.getTitle());
		        }
		      } else {
		        taskTitles.add("No tasks.");
		      }

		    } catch (IOException e) {
		      handleGoogleException(e);
		    }

		    return null;


	    }


		@Override
		protected void onPostExecute(GoogleTasksClientActivity feed) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
		    		  R.layout.list_item,
		    		  taskTitles);
			  listView.setAdapter(adapter);
	    }
	 }



	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


		switch(item.getItemId())
        {
            case 0:
            {
                //Open AddTaskActivity to add new task.
                Intent myIntent = new Intent(GoogleTasksClientActivity.this, AddTaskActivity.class);
                GoogleTasksClientActivity.this.startActivity(myIntent);

           	return true;
            }
            case 1:
            {
           Toast.makeText(this, "Search selected",
    	             Toast.LENGTH_SHORT).show();
           	return true;
            }
            case 2:
            {
           Toast.makeText(this, "Settings selected",
    	             Toast.LENGTH_SHORT).show();
           	return true;
            }
            case 3:
            {
           Toast.makeText(this, "refreshing task list",
                     Toast.LENGTH_SHORT).show();
           refresh();
            return true;
            }
            default:
        		return super.onOptionsItemSelected(item);
        }

    }

    /** Called when the activity is first created. */
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(this);



        setContentView(R.layout.main);

        service = com.google.api.services.tasks.Tasks.builder(transport, jsonFactory)
        .setApplicationName("RedditGoogleTasks/1.0")
        .setHttpRequestInitializer(credential)
        .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {

          @Override
		public void initialize(JsonHttpRequest request) throws IOException {
            TasksRequest tasksRequest = (TasksRequest) request;
            tasksRequest.setKey(ClientCredentials.KEY);

          }
        })
        .build();

    	  //settings = getPreferences(MODE_PRIVATE);

    	  accountName = settings.getString(PREF_ACCOUNT_NAME, null);
            credential.setAccessToken(settings.getString(PREF_AUTH_TOKEN, null));
             Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);

         accountManager = new GoogleAccountManager(getApplicationContext());
         gotAccount();


    }
	//Put oauthToken on sharedPreferences
	  void setAuthToken(String authToken) {
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putString(PREF_AUTH_TOKEN, authToken);
		    editor.commit();
		    credential.setAccessToken(authToken);
		  }

	 void gotAccount() {
		    Account account = accountManager.getAccountByName(accountName);
		    if (account == null) {
		      chooseAccount();
		      return;
		    }
		    if (credential.getAccessToken() != null) {
		      //download task list
		        new RetreiveTask().execute();
		      return;
		    }
		    accountManager.manager.getAuthToken(
		        account, AUTH_TOKEN_TYPE, true, new AccountManagerCallback<Bundle>() {

		          @Override
				public void run(AccountManagerFuture<Bundle> future) {
		            try {
		              Bundle bundle = future.getResult();
		              if (bundle.containsKey(AccountManager.KEY_INTENT)) {
		                Intent intent = bundle.getParcelable(AccountManager.KEY_INTENT);
		                intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_NEW_TASK);
		                startActivityForResult(intent, REQUEST_AUTHENTICATE);
		              } else if (bundle.containsKey(AccountManager.KEY_AUTHTOKEN)) {
		                setAuthToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));
		                new RetreiveTask().execute();
		              }
		            } catch (Exception e) {
		              Log.e(TAG, e.getMessage(), e);
		            }
		          }
		        }, null);
		  }

	 private void chooseAccount() {
		    accountManager.manager.getAuthTokenByFeatures(GoogleAccountManager.ACCOUNT_TYPE,
		        AUTH_TOKEN_TYPE,
		        null,
		        GoogleTasksClientActivity.this,
		        null,
		        null,
		        new AccountManagerCallback<Bundle>() {

		          @Override
				public void run(AccountManagerFuture<Bundle> future) {
		            Bundle bundle;
		            try {
		              bundle = future.getResult();
		              setAccountName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
		              setAuthToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));
		              //download task list
		              new RetreiveTask().execute();
		            } catch (OperationCanceledException e) {
		              // user canceled
		            } catch (AuthenticatorException e) {
		              Log.e(TAG, e.getMessage(), e);
		            } catch (IOException e) {
		              Log.e(TAG, e.getMessage(), e);
		            }
		          }
		        },
		        null);
		  }
	 //Put account name on sharedPreferences
	 void setAccountName(String accountName) {
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putString(PREF_ACCOUNT_NAME, accountName);
		    editor.commit();
		    this.accountName = accountName;
		  }



	  void handleGoogleException(IOException e) {
		    if (e instanceof GoogleJsonResponseException) {
		      GoogleJsonResponseException exception = (GoogleJsonResponseException) e;
		      // TODO(yanivi): should only try this once to avoid infinite loop
		      if (exception.getStatusCode() == 401) {
		        accountManager.invalidateAuthToken(credential.getAccessToken());
		        credential.setAccessToken(null);
		        SharedPreferences.Editor editor2 = settings.edit();
		        editor2.remove(PREF_AUTH_TOKEN);
		        editor2.commit();
		        gotAccount();
		        return;
		      }
		    }
		    Log.e(TAG, e.getMessage(), e);
		  }

	  //Currently to refresh listView, we need to restart the main activity. this will be temporary fix. ||rheza
	    public void refresh() {

	        Intent intent = getIntent();
	        overridePendingTransition(0, 0);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        finish();

	        overridePendingTransition(0, 0);
	        startActivity(intent);
	    }


}