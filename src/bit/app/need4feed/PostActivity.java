package bit.app.need4feed;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class PostActivity extends Activity 
{
	TextView titleTextView;
	TextView feedTextView;
	WebView contentWebView;
	
	Feed sourceFeed;
	Post displayedPost;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_post );
        
        titleTextView = (TextView)findViewById( R.id.titleTextView );
        feedTextView = (TextView)findViewById( R.id.sourceTextView );
        contentWebView = (WebView)findViewById( R.id.contentWebView );
        
        // Fetch the message containing the feed id
        Intent intent = getIntent();
        int postId = intent.getIntExtra( FeedActivity.POST_ID, 0 );
        
        // TODO: Fetch the post with postId
        displayedPost = new Post();
        
        // Insert title, feed and content in the view
        titleTextView.setText( "Post Title" );//displayedPost.getTitle() );
        
        // TODO: Fetch feed to show feed's name as a subtitle
        feedTextView.setText( "From random feed" );//sourceFeed.getTitle() );
        
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
        getMenuInflater().inflate( R.menu.activity_post, menu );
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) 
    {
        switch( item.getItemId() ) 
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask( this );
                return true;
        }
        return super.onOptionsItemSelected( item );
    }
}
