package bit.app.need4feed;

import bit.app.need4feed.type.Feed;
import bit.app.need4feed.type.Post;
import bit.app.need4feed.util.DatabaseHandler;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends SherlockFragmentActivity 
{
	ActionBar actionBar;
	
	TextView titleTextView;
	TextView dateTextView;
	TextView feedTextView;
	WebView contentWebView;
	
	long categoryId;
	long feedId;
	long postId;
	
	Feed sourceFeed;
	Post displayedPost;
	
	DatabaseHandler databaseHandler;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_post );
        actionBar = getSupportActionBar();
        
        MainApplication appContext = (MainApplication)getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        titleTextView = (TextView)findViewById( R.id.titleTextView );
        dateTextView = (TextView)findViewById( R.id.dateTextView );
        feedTextView = (TextView)findViewById( R.id.sourceTextView );
        contentWebView = (WebView)findViewById( R.id.contentWebView );
        
        // Fetch the message containing the feed id
        Intent intent = getIntent();
        categoryId = intent.getLongExtra( MainActivity.CATEGORY_ID, 0 );
        feedId = intent.getLongExtra( CategoryActivity.FEED_ID, 0 );
        postId = intent.getLongExtra( FeedActivity.POST_ID, 0 );
        
        displayedPost = databaseHandler.getPost( postId );
        
        if( displayedPost.getRead() == false )
        {
        	// mark the displayed post as read and update back to the database
        	displayedPost.setRead( true );
        	databaseHandler.updatePost( displayedPost );
        	
        	Toast.makeText( getApplicationContext(), 
	                        "Post marked as read.",
	                        Toast.LENGTH_SHORT ).show();
        }
        
        actionBar.setHomeButtonEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowTitleEnabled( false );
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
        
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>( getBaseContext(), 
        															R.layout.sherlock_spinner_dropdown_item, 
        		                                                    new String[] { "Post", "Feed", "Category", "Main" } );

		actionBar.setListNavigationCallbacks( navAdapter, new OnNavigationListener() 
		{
			public boolean onNavigationItemSelected( int itemPosition, long itemId ) 
			{
				Intent intent;
				switch( itemPosition )
				{
				case 0: // Post
					// Do nothing, stay put!
					return( true );
					
				case 1: // Feed
					intent = new Intent( PostActivity.this, FeedActivity.class );
					intent.putExtra( MainActivity.CATEGORY_ID, categoryId );
					intent.putExtra( CategoryActivity.FEED_ID, feedId );
					startActivity( intent );
					return( true );
					
				case 2: // Category
					intent = new Intent( PostActivity.this, CategoryActivity.class );
					intent.putExtra( MainActivity.CATEGORY_ID, categoryId );
					startActivity( intent );
					return( true );
					
				case 3: // Main
					intent = new Intent( PostActivity.this, MainActivity.class );
					startActivity( intent );
					return( true );
				default:
					return( false );
				}
			}
		} );
        
        // Insert title, feed and content in the view
        titleTextView.setText( displayedPost.getTitle() );
        
        dateTextView.setText( displayedPost.getPubDate() );
        
        sourceFeed = databaseHandler.getFeed( displayedPost.getFeedId() );
        feedTextView.setText( "Feed: " + sourceFeed.getTitle() );
        
        contentWebView.loadDataWithBaseURL( sourceFeed.getLink(), 
        		                            displayedPost.getDescription(), 
        		                            "text/html", "UTF-8", null );
        
        // Scale content and images to single column
        contentWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        
        // Clicking the title will open the web browser and take the user
        // to the specific post
        titleTextView.setOnClickListener( new OnClickListener()
        {
        	public void onClick( View view )
        	{
        		Intent intent = new Intent( Intent.ACTION_VIEW).setData( 
        				                    Uri.parse( displayedPost.getLink() ) );
				startActivity(intent);
        	}
        } );
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) 
    {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate( R.menu.activity_post, menu );
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
    		Intent intent = new Intent( PostActivity.this, FeedActivity.class );
			intent.putExtra( MainActivity.CATEGORY_ID, categoryId );
			intent.putExtra( CategoryActivity.FEED_ID, feedId );
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
