package com.redditandroiddevelopers.googletasksclient;



import android.app.Activity;
import android.os.Bundle;




public class GoogleTasksClientActivity extends Activity {
	
	
	
	 
    /** Called when the activity is first created. */
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*
        setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 		
        getSupportActionBar().setTitle("Reddit Google Tasks");
*/
        setContentView(R.layout.main);
        
    }
    
    
   
}