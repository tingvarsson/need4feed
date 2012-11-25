package bit.app.need4feed;

import bit.app.need4feed.AddFeedDialog.AddFeedDialogListener;
import bit.app.need4feed.RemoveFeedDialog.RemoveFeedDialogListener;
import bit.app.need4feed.type.Feed;
import bit.app.need4feed.type.FeedAdapter;
import bit.app.need4feed.util.DatabaseHandler;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryActivity extends SherlockFragmentActivity 
							  implements AddFeedDialogListener,
							             RemoveFeedDialogListener
{
	public final static String FEED_ID = "bit.app.need4feed.FEED_ID";
	
	ActionBar actionBar;
	ListView feedListView;
	FeedAdapter feedAdapter;
	
	long categoryId;
	
	DatabaseHandler databaseHandler;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category );
        actionBar = getSupportActionBar();        
        feedListView = (ListView)findViewById( R.id.feedListView );
        
        MainApplication appContext = (MainApplication)getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        // Fetch the message containing the category id
        Intent intent = getIntent();
        categoryId = intent.getLongExtra( MainActivity.CATEGORY_ID, 0 );
        
        actionBar.setHomeButtonEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowTitleEnabled( false );
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
        
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>( getBaseContext(), 
        															R.layout.sherlock_spinner_dropdown_item, 
        		                                                    new String[] { "Category", "Main" } );

		actionBar.setListNavigationCallbacks( navAdapter, new OnNavigationListener() 
		{
			public boolean onNavigationItemSelected( int itemPosition, long itemId ) 
			{
				Intent intent;
				switch( itemPosition )
				{
				case 0: // Category
					// Do nothing, stay put!
					return( true );
					
				case 1: // Main
					intent = new Intent( CategoryActivity.this, MainActivity.class );
					startActivity( intent );
					return( true );
				default:
					return( false );
				}
			}
		} );
        
        feedListView.setOnItemClickListener( new OnItemClickListener() 
        {
			public void onItemClick( AdapterView<?> parent, View view,
					                 int position, long id) 
			{
				Intent intent = new Intent( CategoryActivity.this, 
						                    FeedActivity.class );
				intent.putExtra( MainActivity.CATEGORY_ID, categoryId );
			    intent.putExtra( FEED_ID, ( (Feed)feedAdapter.getItem( position ) ).getId() );
			    startActivity( intent );
			}
		} );
        
        feedAdapter = new FeedAdapter( CategoryActivity.this, 
        		                       databaseHandler.getFeeds( categoryId ) );
        
        feedListView.setAdapter( feedAdapter );
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) 
    {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate( R.menu.activity_category, menu );
        return( true );
    }
     
    public boolean onPrepareOptionsMenu( Menu menu )
    {
        return( super.onPrepareOptionsMenu( menu ) );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) 
    {
    	FragmentManager fm = getSupportFragmentManager();
    	Bundle args = new Bundle();
        args.putLong( "categoryId", categoryId );
    	
    	switch( item.getItemId() )
    	{
    	case android.R.id.home:
    		Intent intent = new Intent( CategoryActivity.this, MainActivity.class );
			startActivity( intent );
    		break;
    		
    	case R.id.menu_settings:
    		
    		break;
    		
    	case R.id.menu_add_feed:
            AddFeedDialog addFeedDialog = new AddFeedDialog();
            addFeedDialog.setArguments( args );
            addFeedDialog.show( fm, "fragment_add_feed" );
    		break;
    		
    	case R.id.menu_remove_feed:
            RemoveFeedDialog removeFeedDialog = new RemoveFeedDialog();
            removeFeedDialog.setArguments( args );
            removeFeedDialog.show( fm, "fragment_remove_feed" );
    		break;
    	}
        return( super.onOptionsItemSelected( item ) );
    }

	public void onFinishAddFeedDialog() 
	{
		feedAdapter.setFeedList( databaseHandler.getFeeds( categoryId ) );
		feedAdapter.notifyDataSetChanged();
	}
    
    public void onFinishRemoveFeedDialog()
    {
    	feedAdapter.setFeedList( databaseHandler.getFeeds( categoryId ) );
    	feedAdapter.notifyDataSetChanged();
    }
}
