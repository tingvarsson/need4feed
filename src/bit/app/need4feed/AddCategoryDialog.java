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
        void onFinishAddCategoryDialog( String categoryName );
    }

    public AddCategoryDialog() 
    {
        // Empty constructor required for DialogFragment
    }
    
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) 
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getActivity() );
 
        dialogBuilder.setTitle("Add Category");
        
        final EditText input = new EditText( getActivity() );
        dialogBuilder.setView( input );
        
        dialogBuilder.setPositiveButton( "Add", new OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which) 
            {
            	AddCategoryDialogListener activity = (AddCategoryDialogListener)getActivity();
                activity.onFinishAddCategoryDialog( input.getText().toString() );
                dismiss();
            }
        } );
        
        dialogBuilder.setNegativeButton( "Cancel", null );
        
        AlertDialog dialog = dialogBuilder.create();
        return( dialog );
    }
}
