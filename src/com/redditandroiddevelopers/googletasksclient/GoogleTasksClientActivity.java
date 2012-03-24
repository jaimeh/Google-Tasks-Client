package com.redditandroiddevelopers.googletasksclient;



import android.app.Activity;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;



public class GoogleTasksClientActivity extends SherlockActivity {
	
	
	
	 
    /** Called when the activity is first created. */
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    
      
 		
        getSupportActionBar().setTitle("Reddit Google Tasks");

       setContentView(R.layout.main);
        
    }
    
    
   
}