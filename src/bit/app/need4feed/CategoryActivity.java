package bit.app.need4feed;

import java.util.List;

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

public class CategoryActivity extends Activity 
{
	public final static String FEED_ID = "bit.app.need4feed.FEED_ID";
	
	ListView feedListView;
	List<Feed> feedList;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category );
        
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
        getMenuInflater().inflate( R.menu.activity_category, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) 
    {
        switch ( item.getItemId() ) 
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask( this );
                return true;
        }
        return super.onOptionsItemSelected( item );
    }

}
