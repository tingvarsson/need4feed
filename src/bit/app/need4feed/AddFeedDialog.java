package bit.app.need4feed;

import bit.app.need4feed.util.DatabaseHandler;
import bit.app.need4feed.util.RssHandler;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.Toast;

public class AddFeedDialog extends DialogFragment 
{
	public interface AddFeedDialogListener
    {
        void onFinishAddFeedDialog();
    }
    
    private AddFeedDialogListener addFeedDialogListener;
    private DatabaseHandler databaseHandler;
    
    private long categoryId;
    
    private android.app.Activity activity;

	public AddFeedDialog() 
	{
		// Empty constructor required for DialogFragment
	}
	
	@Override
    public void onAttach( android.app.Activity activity ) 
    {
        super.onAttach( activity );
        this.activity = activity;
        try
        {
        	addFeedDialogListener = (AddFeedDialogListener)activity;
        }
        catch( ClassCastException e )
        {
            // The hosting activity does not implemented the interface AddFeedDialogListener
            throw new ClassCastException(activity.toString() + " must implement AddFeedDialogListener");
        }
    }
    
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) 
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getActivity() );
 
        dialogBuilder.setTitle( "Add Feed" );
        
        categoryId = getArguments().getLong( "categoryId", -1 );
        
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
            	
            	// Validate that it is a non-empty string and that the
            	// category id is valid
            	if( ( !inputString.equals( "" ) ) && ( categoryId >= 0 ) )
            	{
            		new AddFeedTask().execute( inputString );
            	}
            }
        } );
        
        dialogBuilder.setNegativeButton( "Cancel", null );
        
        // Finalize the dialog and return it
        AlertDialog dialog = dialogBuilder.create();
        return( dialog );
    }
    
    private class AddFeedTask extends AsyncTask<String, Integer, Boolean> 
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
		}
	
		@Override
		protected Boolean doInBackground( String... params ) 
		{
			String feedLink = params[ 0 ];
			
			RssHandler rssHandler = new RssHandler( databaseHandler );
			
        	return( rssHandler.getFeed( feedLink, categoryId ) );
	   }
	
	   @Override
	   protected void onProgressUpdate( Integer... values ) 
	   {
	      super.onProgressUpdate( values );
	   }
	
	   @Override
	   protected void onPostExecute( Boolean success ) 
	   {
	      super.onPostExecute( success );
	      if( success )
	      {
	    	  addFeedDialogListener.onFinishAddFeedDialog();
	    	  
	    	  Toast.makeText( activity.getApplicationContext(), 
		                      "Feed added.",
		                      Toast.LENGTH_SHORT ).show();
	      }
	      else
	      {
	    	  Toast.makeText( activity.getApplicationContext(), 
		                      "Failed to add feed.",
		                      Toast.LENGTH_SHORT ).show();
	      }
	   }
   }
}
