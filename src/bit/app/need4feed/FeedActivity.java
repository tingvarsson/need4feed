package bit.app.need4feed;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class FeedActivity extends Activity 
{
	public final static String POST_ID = "bit.app.need4feed.POST_ID";
	
	ListView postListView;
	ArrayList<Post> postList = new ArrayList<Post>();

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_feed );
        //getActionBar().setDisplayHomeAsUpEnabled( true );
        
        postListView = (ListView)findViewById( R.id.postListView );
        
        // Fetch the message containing the feed id
        Intent intent = getIntent();
        int feedId = intent.getIntExtra( CategoryActivity.FEED_ID, 0 );
        
        // TODO: Fetch posts for feedId, for now a dummy list
        postList.add( new Post( "Post A", "post details" ) );
        postList.add( new Post( "Post B", "post details" ) );
        postList.add( new Post( "Post C", "post details" ) );
        postList.add( new Post( "Post D", "post details" ) );
        postList.add( new Post( "Post E", "post details" ) );
        postList.add( new Post( "Post F", "post details" ) );
        postList.add( new Post( "Post G", "post details" ) );
        postList.add( new Post( "Post H", "post details" ) );
        postList.add( new Post( "Post A", "post details" ) );
        postList.add( new Post( "Post B", "post details" ) );
        postList.add( new Post( "Post C", "post details" ) );
        postList.add( new Post( "Post D", "post details" ) );
        postList.add( new Post( "Post E", "post details" ) );
        postList.add( new Post( "Post F", "post details" ) );
        postList.add( new Post( "Post G", "post details" ) );
        postList.add( new Post( "Post H", "post details" ) );
        
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
        getMenuInflater().inflate( R.menu.activity_feed, menu );
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
