package bit.app.need4feed;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

public class PostActivity extends SherlockFragmentActivity 
{
	ActionBar actionBar;
	
	TextView titleTextView;
	TextView feedTextView;
	WebView contentWebView;
	
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
        feedTextView = (TextView)findViewById( R.id.sourceTextView );
        contentWebView = (WebView)findViewById( R.id.contentWebView );
        
        // Fetch the message containing the feed id
        Intent intent = getIntent();
        int postId = intent.getIntExtra( FeedActivity.POST_ID, 0 );
        
        displayedPost = databaseHandler.getPost( postId );
        
        // Insert title, feed and content in the view
        titleTextView.setText( displayedPost.getTitle() );
        
        sourceFeed = databaseHandler.getFeed( displayedPost.getFeedId() );
        feedTextView.setText( sourceFeed.getTitle() );
        
        contentWebView.loadData( displayedPost.getDescription(), 
        		                 "text/html; charset=UTF-8", null);
        
        // Clicking the title will open the web browser and take the user
        // to the specific post
        titleTextView.setOnClickListener( new OnClickListener()
        {
        	public void onClick( View view )
        	{
        		Intent intent = new Intent( Intent.ACTION_VIEW).setData( 
        				                    Uri.parse("http://www.sweclockers.com/feeds/news.xml" ) );//displayedPost.getLink() ) );
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
        if( item.getItemId() == R.id.menu_settings ) 
        {

        }
        
        return( super.onOptionsItemSelected( item ) );
    }
}
