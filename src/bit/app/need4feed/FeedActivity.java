package bit.app.need4feed;

import java.util.List;

import bit.app.need4feed.type.Post;
import bit.app.need4feed.type.PostAdapter;
import bit.app.need4feed.util.DatabaseHandler;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedActivity extends SherlockFragmentActivity 
{
	public final static String POST_ID = "bit.app.need4feed.POST_ID";
	
	ActionBar actionBar;
	ListView postListView;
	PostAdapter postAdapter;
	List<Post> postList;
	
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
        long feedId = intent.getLongExtra( CategoryActivity.FEED_ID, 0 );
        
        // Fetch posts for feedId
        postList = databaseHandler.getPosts( feedId );
        
        postListView.setOnItemClickListener( new OnItemClickListener() 
        {
			public void onItemClick( AdapterView<?> parent, View view,
					                 int position, long id) 
			{
				Intent intent = new Intent( FeedActivity.this, 
						                    PostActivity.class );
			    intent.putExtra( POST_ID, ( (Post)postAdapter.getItem( position ) ).getId() );
			    startActivity( intent );
			}
		} );
			    
		postAdapter = new PostAdapter( FeedActivity.this, postList );
        
        postListView.setAdapter( postAdapter );
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
