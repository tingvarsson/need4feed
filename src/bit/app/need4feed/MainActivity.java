package bit.app.need4feed;

import java.util.ArrayList;

import bit.app.need4feed.Category;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity 
{
	public final static String CATEGORY_ID = "bit.app.need4feed.CATEGORY_ID";
	
	ListView categoryListView;
	ArrayList<Category> categoryList = new ArrayList<Category>();

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        categoryListView = (ListView)findViewById( R.id.categoryListView );
        
        // TODO: Fetch all categories, for now a dummy list
        categoryList.add( new Category( "Cat A" ) );
        categoryList.add( new Category( "Cat B" ) );
        categoryList.add( new Category( "Cat C" ) );
        categoryList.add( new Category( "Cat D" ) );
        categoryList.add( new Category( "Cat E" ) );
        categoryList.add( new Category( "Cat F" ) );
        categoryList.add( new Category( "Cat G" ) );
        categoryList.add( new Category( "Cat H" ) );
        categoryList.add( new Category( "Cat A" ) );
        categoryList.add( new Category( "Cat B" ) );
        categoryList.add( new Category( "Cat C" ) );
        categoryList.add( new Category( "Cat D" ) );
        categoryList.add( new Category( "Cat E" ) );
        categoryList.add( new Category( "Cat F" ) );
        categoryList.add( new Category( "Cat G" ) );
        categoryList.add( new Category( "Cat H" ) );
        
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
        getMenuInflater().inflate( R.menu.activity_main, menu );
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
