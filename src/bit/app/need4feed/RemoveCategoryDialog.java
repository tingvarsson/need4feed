package bit.app.need4feed;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class RemoveCategoryDialog extends DialogFragment 
{
	public interface RemoveCategoryDialogListener
	{
		void onFinishRemoveCategoryDialog( int categoryPosition );
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
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement RemoveCategoryDialogListener");
        }
    }
 
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) 
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getActivity() );
 
        dialogBuilder.setTitle("Remove Category");
        
        MainApplication appContext = (MainApplication)getActivity().getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        categoryList = databaseHandler.getCategories();
        String[] categoryNames = new String[ categoryList.toArray().length ];
        for( int i = 0; i < categoryList.toArray().length; i++ )
        {
        	categoryNames[ i ] = categoryList.get( i ).getName();
        }
        
        dialogBuilder.setSingleChoiceItems( categoryNames, 0, null );
 
        dialogBuilder.setPositiveButton( "Remove", new OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which) 
            {
                AlertDialog alert = (AlertDialog)dialog;
                int position = alert.getListView().getCheckedItemPosition();
                databaseHandler.deleteCategory( categoryList.get( position ).getId() );
                removeCategoryDialogListener.onFinishRemoveCategoryDialog( position );
            }
        } );
 
        dialogBuilder.setNegativeButton( "Cancel", null );
 
        AlertDialog dialog = dialogBuilder.create();
        return( dialog );
    }
}
