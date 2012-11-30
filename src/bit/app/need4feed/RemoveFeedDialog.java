package bit.app.need4feed;

import java.util.List;

import bit.app.need4feed.type.Feed;
import bit.app.need4feed.util.DatabaseHandler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class RemoveFeedDialog extends DialogFragment 
{
	public interface RemoveFeedDialogListener
	{
		void onFinishRemoveFeedDialog();
	}
	
	List<Feed> feedList;
	
	private RemoveFeedDialogListener removeFeedDialogListener;
	private DatabaseHandler databaseHandler;
	
	private long categoryId;

	public RemoveFeedDialog() 
	{
		// Empty constructor required for DialogFragment
	}
	
	@Override
    public void onAttach( android.app.Activity activity ) 
    {
        super.onAttach( activity );
        try
        {
        	removeFeedDialogListener = (RemoveFeedDialogListener)activity;
        }
        catch( ClassCastException e )
        {
            // The hosting activity does not implemented the interface RemoveFeedDialogListener
            throw new ClassCastException(activity.toString() + " must implement RemoveFeedDialogListener");
        }
    }
 
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) 
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getActivity() );
 
        dialogBuilder.setTitle( "Remove Feed" );
        
        categoryId = getArguments().getLong( "categoryId", -1 );
        
        // Setup the content: radio button list of categories
        MainApplication appContext = (MainApplication)getActivity().getApplicationContext();
        databaseHandler = appContext.getDatabaseHandler();
        
        dialogBuilder.setSingleChoiceItems( databaseHandler.getFeedNames( categoryId ), -1, null );
 
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
	                feedList = databaseHandler.getFeeds( categoryId );
	                databaseHandler.deleteFeed( feedList.get( position ).getId() );
	                removeFeedDialogListener.onFinishRemoveFeedDialog();
	                
	                Toast.makeText( getActivity().getApplicationContext(), 
			                        "Feed " + feedList.get( position ).getTitle() + " removed.",
			                        Toast.LENGTH_SHORT ).show();
                }
                else
                {
                	Toast.makeText( getActivity().getApplicationContext(), 
			                        "No feed selected.",
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
