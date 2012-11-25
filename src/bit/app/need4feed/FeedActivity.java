package bit.app.need4feed;

import bit.app.need4feed.type.Post;
import bit.app.need4feed.type.PostAdapter;
import bit.app.need4feed.util.DatabaseHandler;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedActivity extends SherlockFragmentActivity 
{
	public final static String POST_ID = "bit.app.need4feed.POST_ID";
	public final static int REQUEST_UPDATE = 1;
	
	ActionBar actionBar;
	ListView postListView;
	PostAdapter postAdapter;
	
	long categoryId;
	long feedId;
	
	DatabaseHandler databaseHandler;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_feed );
        actionBar = getSupportActionBar();
        postListView = (ListView)findViewById( R.id.postListView );
        
        MainApplication appContext = (MainApplication)getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        // Fetch the message containing the feed id
        Intent intent = getIntent();
        categoryId = intent.getLongExtra( MainActivity.CATEGORY_ID, 0 );
        feedId = intent.getLongExtra( CategoryActivity.FEED_ID, 0 );
        
        actionBar.setHomeButtonEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowTitleEnabled( false );
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
        
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>( getBaseContext(), 
        															R.layout.sherlock_spinner_dropdown_item, 
        		                                                    new String[] { "Feed", "Category", "Main" } );

		actionBar.setListNavigationCallbacks( navAdapter, new OnNavigationListener() 
		{
			public boolean onNavigationItemSelected( int itemPosition, long itemId ) 
			{
				Intent intent;
				switch( itemPosition )
				{
				case 0: // Feed
					// Do nothing, stay put!
					return( true );
					
				case 1: // Category
					intent = new Intent( FeedActivity.this, CategoryActivity.class );
					intent.putExtra( MainActivity.CATEGORY_ID, categoryId );
					startActivity( intent );
					return( true );
					
				case 2: // Main
					intent = new Intent( FeedActivity.this, MainActivity.class );
					startActivity( intent );
					return( true );
				default:
					return( false );
				}
			}
		} );
        
        postListView.setOnItemClickListener( new OnItemClickListener() 
        {
			public void onItemClick( AdapterView<?> parent, View view,
					                 int position, long id) 
			{
				Intent intent = new Intent( FeedActivity.this, 
						                    PostActivity.class );
				intent.putExtra( MainActivity.CATEGORY_ID, categoryId );
				intent.putExtra( CategoryActivity.FEED_ID, feedId );
			    intent.putExtra( POST_ID, ( (Post)postAdapter.getItem( position ) ).getId() );
			    startActivityForResult( intent, REQUEST_UPDATE );
			}
		} );
			    
		postAdapter = new PostAdapter( FeedActivity.this, 
				                       databaseHandler.getPosts( feedId ) );
        
        postListView.setAdapter( postAdapter );
    }
    
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent intent ) 
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if( requestCode == REQUEST_UPDATE ) 
        {
        	postAdapter.setPostList( databaseHandler.getPosts( feedId ) );
        	postAdapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) 
    {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate( R.menu.activity_feed, menu );
        return( true );
    }
     
    public boolean onPrepareOptionsMenu( Menu menu )
    {
        return( super.onPrepareOptionsMenu( menu ) );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) 
    {
    	switch( item.getItemId() )
    	{
    	case android.R.id.home:
    		Intent intent = new Intent( FeedActivity.this, CategoryActivity.class );
			intent.putExtra( MainActivity.CATEGORY_ID, categoryId );
			startActivity( intent );
    		break;
    		
    	case R.id.menu_settings:
    		
    		break;
    	
		default:
    			
			break;
    			
    	}
        
        return( super.onOptionsItemSelected( item ) );
    }
}
