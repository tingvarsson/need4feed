package bit.app.need4feed;

import java.util.Collections;
import java.util.List;

import bit.app.need4feed.AddCategoryDialog.EditNameDialogListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends SherlockFragmentActivity implements EditNameDialogListener
{
	public final static String CATEGORY_ID = "bit.app.need4feed.CATEGORY_ID";
	
	ActionBar actionBar;
	ListView categoryListView;
	List<Category> categoryList;
	CategoryAdapter categoryAdapter;
	
	DatabaseHandler databaseHandler;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        actionBar = getSupportActionBar();
        categoryListView = (ListView)findViewById( R.id.categoryListView );
        
        databaseHandler = new DatabaseHandler( this );
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
        
        categoryAdapter = new CategoryAdapter( MainActivity.this, categoryList );
        
        categoryListView.setAdapter( categoryAdapter );
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
        	FragmentManager fm = getSupportFragmentManager();
            AddCategoryDialog addCategoryDialog = new AddCategoryDialog();
            addCategoryDialog.show(fm, "fragment_add_category");
        }
        
        return( super.onOptionsItemSelected( item ) );
    }
    
    public void onFinishEditDialog( String inputText ) 
    {
    	Category newCategory = new Category( inputText );
    	databaseHandler.addCategory( newCategory );
    	categoryList.add( newCategory );
    	Collections.sort( categoryList );
    	categoryAdapter.notifyDataSetChanged();
    }
}
