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

public class MainActivity extends SherlockActivity 
{
	public final static String CATEGORY_ID = "bit.app.need4feed.CATEGORY_ID";
	
	ActionBar actionBar;
	ListView categoryListView;
	List<Category> categoryList;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        actionBar = getSupportActionBar();
        categoryListView = (ListView)findViewById( R.id.categoryListView );
        
        DatabaseHandler databaseHandler = new DatabaseHandler( this );
        categoryList = databaseHandler.getCategories();
        
        categoryListView.setOnItemClickListener( new OnItemClickListener() 
        {
			public void onItemClick( AdapterView<?> parent, View view,
					                 int position, long id) 
			{
				Intent intent = new Intent( MainActivity.this, 
						                    CategoryActivity.class );
			    intent.putExtra( CATEGORY_ID, position );
			    startActivity( intent );
			}
		} );
        
        categoryListView.setAdapter( new CategoryAdapter( MainActivity.this, 
        		                                          categoryList ) );
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) 
    {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate( R.menu.activity_main, menu );
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
        if( item.getItemId() == R.id.menu_add_category )
        {

        }
        
        return( super.onOptionsItemSelected( item ) );
    }
}
