package bit.app.need4feed;

import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedActivity extends SherlockActivity 
{
	public final static String POST_ID = "bit.app.need4feed.POST_ID";
	
	ActionBar actionBar;
	ListView postListView;
	List<Post> postList;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_feed );
        actionBar = getSupportActionBar();
        postListView = (ListView)findViewById( R.id.postListView );
        
        // Fetch the message containing the feed id
        Intent intent = getIntent();
        int feedId = intent.getIntExtra( CategoryActivity.FEED_ID, 0 );
        
        // TODO: Fetch posts for feedId, for now just get them from online
        DatabaseHandler databaseHandler = new DatabaseHandler( this );
        postList = databaseHandler.getPosts( feedId );
        RssHandler rssHandler = new RssHandler();
        postList = rssHandler.getLatestPosts( "http://www.sweclockers.com/feeds/news.xml" );
        
        postListView.setOnItemClickListener( new OnItemClickListener() 
        {
			public void onItemClick( AdapterView<?> parent, View view,
					                 int position, long id) 
			{
				Intent intent = new Intent( FeedActivity.this, 
						                    PostActivity.class );
			    intent.putExtra( POST_ID, position );
			    startActivity( intent );
			}
		} );
        
        postListView.setAdapter( new PostAdapter( FeedActivity.this, 
        		                                  postList ) );
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
        if( item.getItemId() == R.id.menu_settings ) 
        {

        }
        
        return( super.onOptionsItemSelected( item ) );
    }
}
