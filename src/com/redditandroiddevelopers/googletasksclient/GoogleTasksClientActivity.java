package com.redditandroiddevelopers.googletasksclient;




import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;


public class GoogleTasksClientActivity extends SherlockActivity {
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
       //add menu items to the action bar. look on onOptionsItemSelected to action code.
		
        menu.add(0,0,0,"Add New Task"); 
        menu.add(0,1,0,"Search"); 
        menu.add(0,2,0,"Settings");    

       

         
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		 
		switch(item.getItemId())
        {
            case 0:
            {
          	Toast.makeText(this, "Add selected",
    	              Toast.LENGTH_SHORT).show();
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
            default:
        		return super.onOptionsItemSelected(item);
        }
    	
    }
	 
    /** Called when the activity is first created. */
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Reddit Google Tasks");

        setContentView(R.layout.main);
        
    }
    
	 
   
}