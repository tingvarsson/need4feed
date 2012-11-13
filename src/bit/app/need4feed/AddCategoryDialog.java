package bit.app.need4feed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class AddCategoryDialog extends DialogFragment 
{
    public interface AddCategoryDialogListener
    {
        void onFinishAddCategoryDialog();
    }
    
    private AddCategoryDialogListener addCategoryDialogListener;
    private DatabaseHandler databaseHandler;

    public AddCategoryDialog() 
    {
        // Empty constructor required for DialogFragment
    }
    
    @Override
    public void onAttach( android.app.Activity activity ) 
    {
        super.onAttach( activity );
        try
        {
        	addCategoryDialogListener = (AddCategoryDialogListener)activity;
        }
        catch( ClassCastException e )
        {
            // The hosting activity does not implemented the interface AddCategoryDialogListener
            throw new ClassCastException(activity.toString() + " must implement AddCategoryDialogListener");
        }
    }
    
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) 
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getActivity() );
 
        dialogBuilder.setTitle("Add Category");
        
        // Setup a handle to the databaseHandler
        MainApplication appContext = (MainApplication)getActivity().getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        // Setup the content: a string input view
        final EditText input = new EditText( getActivity() );
        dialogBuilder.setView( input );
        
        // Setup buttons
        dialogBuilder.setPositiveButton( "Add", new OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which) 
            {
            	String inputString = input.getText().toString();
            	
            	// Validate that it is a non-empty string
            	if( !inputString.equals( "" ) )
            	{
	            	Category newCategory = new Category( inputString );
	            	databaseHandler.addCategory( newCategory );
	            	addCategoryDialogListener.onFinishAddCategoryDialog();
            	}
            }
        } );
        
        dialogBuilder.setNegativeButton( "Cancel", null );
        
        // Finalize the dialog and return it
        AlertDialog dialog = dialogBuilder.create();
        return( dialog );
    }
}
