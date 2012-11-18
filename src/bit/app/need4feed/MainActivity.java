package bit.app.need4feed;

import bit.app.need4feed.AddCategoryDialog.AddCategoryDialogListener;
import bit.app.need4feed.RemoveCategoryDialog.RemoveCategoryDialogListener;
import bit.app.need4feed.type.Category;
import bit.app.need4feed.type.CategoryAdapter;
import bit.app.need4feed.util.DatabaseHandler;

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

public class MainActivity extends SherlockFragmentActivity 
                          implements AddCategoryDialogListener, 
                                     RemoveCategoryDialogListener
{
	public final static String CATEGORY_ID = "bit.app.need4feed.CATEGORY_ID";
	
	ActionBar actionBar;
	ListView categoryListView;
	CategoryAdapter categoryAdapter;
	
	DatabaseHandler databaseHandler;

    @Override
    public void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        actionBar = getSupportActionBar();
        categoryListView = (ListView)findViewById( R.id.categoryListView );
        
        MainApplication appContext = (MainApplication)getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        categoryListView.setOnItemClickListener( new OnItemClickListener() 
        {
			public void onItemClick( AdapterView<?> parent, View view,
					                 int position, long id) 
			{
				Intent intent = new Intent( MainActivity.this, 
						                    CategoryActivity.class );
			    intent.putExtra( CATEGORY_ID, ( (Category)categoryAdapter.getItem( position ) ).getId() );
			    startActivity( intent );
			}
		} );
        
        categoryAdapter = new CategoryAdapter( MainActivity.this, 
                							   databaseHandler.getCategories() );
        
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
    	FragmentManager fm = getSupportFragmentManager();
    	
    	switch( item.getItemId() )
    	{
    	case R.id.menu_settings:
    		
    		break;
    		
    	case R.id.menu_add_category:
            AddCategoryDialog addCategoryDialog = new AddCategoryDialog();
            addCategoryDialog.show( fm, "fragment_add_category" );
    		break;
    		
    	case R.id.menu_remove_category:
            RemoveCategoryDialog removeCategoryDialog = new RemoveCategoryDialog();
            removeCategoryDialog.show( fm, "fragment_remove_category" );
    		break;
    	}
        return( super.onOptionsItemSelected( item ) );
    }
    
    public void onFinishAddCategoryDialog()
    {
    	categoryAdapter.setCategoryList( databaseHandler.getCategories() );
    	categoryAdapter.notifyDataSetChanged();
    }
    
    public void onFinishRemoveCategoryDialog()
    {
    	categoryAdapter.setCategoryList( databaseHandler.getCategories() );
    	categoryAdapter.notifyDataSetChanged();
    }
}
