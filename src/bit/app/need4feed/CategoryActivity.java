package bit.app.need4feed;

import java.util.List;

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

public class CategoryActivity extends SherlockFragmentActivity 
{
	public final static String FEED_ID = "bit.app.need4feed.FEED_ID";
	
	ActionBar actionBar;
	ListView feedListView;
	List<Feed> feedList;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category );
        actionBar = getSupportActionBar();        
        feedListView = (ListView)findViewById( R.id.feedListView );
        
        // Fetch the message containing the category id
        Intent intent = getIntent();
        int categoryId = intent.getIntExtra( MainActivity.CATEGORY_ID, 0 );
        
        // TODO: Fetch all feeds for categoryId, for now a dummy list
        DatabaseHandler databaseHander = new DatabaseHandler( this );
        feedList = databaseHander.getFeeds( categoryId );
        
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
        
        feedListView.setAdapter( new FeedAdapter( CategoryActivity.this, 
        		                                  feedList ) );
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
        if( item.getItemId() == R.id.menu_settings ) 
        {

        }
        if( item.getItemId() == R.id.menu_add_feed )
        {

        }
        if( item.getItemId() == R.id.menu_remove_category )
        {

        }
        
        return( super.onOptionsItemSelected( item ) );
    }
}
