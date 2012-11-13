package bit.app.need4feed;

import bit.app.need4feed.AddFeedDialog.AddFeedDialogListener;
import bit.app.need4feed.RemoveFeedDialog.RemoveFeedDialogListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
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
        categoryId = intent.getIntExtra( MainActivity.CATEGORY_ID, 0 );
        
        feedListView.setOnItemClickListener( new OnItemClickListener() 
        {
			public void onItemClick( AdapterView<?> parent, View view,
					                 int position, long id) 
			{
				Intent intent = new Intent( CategoryActivity.this, 
						                    FeedActivity.class );
			    intent.putExtra( FEED_ID, position );
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
    	case R.id.menu_settings:
    		
    		break;
    		
    	case R.id.menu_add_feed:
            AddFeedDialog addFeedDialog = new AddFeedDialog();
            addFeedDialog.setArguments( args );
            addFeedDialog.show( fm, "fragment_add_feed" );
    		break;
    		
    	case R.id.menu_remove_category:
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
