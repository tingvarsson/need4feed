package bit.app.need4feed;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class PostActivity extends Activity 
{
	TextView titleTextView;
	TextView feedTextView;
	WebView contentWebView;
	
	Post currentPost;

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
        currentPost = new Post( "Testeru", "Test as well" );
        
        // Insert title, feed and content in the view
        titleTextView.setText( currentPost.getTitle() );
        
        // TODO: Change so it fetched the name of the feed
        feedTextView.setText( "From random feed" );
        
        contentWebView.loadData( currentPost.description, 
        		                 "text/html; charset=UTF-8", null);
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
