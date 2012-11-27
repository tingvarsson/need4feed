package bit.app.need4feed;

import java.util.List;

import bit.app.need4feed.type.Category;
import bit.app.need4feed.util.DatabaseHandler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class RemoveCategoryDialog extends DialogFragment 
{
	public interface RemoveCategoryDialogListener
	{
		void onFinishRemoveCategoryDialog();
	}
	
	List<Category> categoryList;
	
	private RemoveCategoryDialogListener removeCategoryDialogListener;
	private DatabaseHandler databaseHandler;

	public RemoveCategoryDialog() 
	{
		// Empty constructor required for DialogFragment
	}
	
	@Override
    public void onAttach( android.app.Activity activity ) 
    {
        super.onAttach( activity );
        try
        {
        	removeCategoryDialogListener = (RemoveCategoryDialogListener)activity;
        }
        catch( ClassCastException e )
        {
            // The hosting activity does not implemented the interface AddCategoryDialogListener
            throw new ClassCastException(activity.toString() + " must implement RemoveCategoryDialogListener");
        }
    }
 
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) 
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getActivity() );
 
        dialogBuilder.setTitle("Remove Category");
        
        // Setup the content: radio button list of categories
        MainApplication appContext = (MainApplication)getActivity().getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        dialogBuilder.setSingleChoiceItems( databaseHandler.getCategoryNames(), -1, null );
 
        // Setup buttons
        dialogBuilder.setPositiveButton( "Remove", new OnClickListener() 
        {
            public void onClick( DialogInterface dialog, int which ) 
            {
                AlertDialog alert = (AlertDialog)dialog;
                int position = alert.getListView().getCheckedItemPosition();
                
                // Validate that it is a correct postion
                if( position >= 0 )
                {
	                categoryList = databaseHandler.getCategories();
	                databaseHandler.deleteCategory( categoryList.get( position ).getId() );
	                removeCategoryDialogListener.onFinishRemoveCategoryDialog();
	                
	                Toast.makeText( getActivity().getApplicationContext(), 
    				                "Category " + categoryList.get( position ).getName() + " removed.",
    				                Toast.LENGTH_SHORT ).show();
                }
                else
                {
                	Toast.makeText( getActivity().getApplicationContext(), 
    				                "No category selected.",
    				                Toast.LENGTH_SHORT ).show();
                }
            }
        } );
 
        dialogBuilder.setNegativeButton( "Cancel", null );
 
        // Finalize the dialog and return it
        AlertDialog dialog = dialogBuilder.create();
        return( dialog );
    }
}
