package bit.app.need4feed;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AddCategoryDialog extends DialogFragment 
{
    public interface AddCategoryDialogListener
    {
        void onFinishAddCategoryDialog( String categoryName );
    }

    private EditText categoryNameEditText;
    private Button cancelButton;
    private Button addButton;

    public AddCategoryDialog() 
    {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) 
    {
    	// Setup the layout along with a title
    	View view = inflater.inflate( R.layout.fragment_add_category, container );
    	getDialog().setTitle( "Add Category" );
    	
    	// Find all the required components
        categoryNameEditText = (EditText)view.findViewById( R.id.txt_category_name );
        cancelButton = (Button)view.findViewById( R.id.add_category_cancel );
        addButton = (Button)view.findViewById( R.id.add_category_ok );

        // Show soft keyboard automatically
        categoryNameEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode( LayoutParams.SOFT_INPUT_STATE_VISIBLE );
        
        // Setup an editor action listerner
        categoryNameEditText.setOnEditorActionListener( new OnEditorActionListener()
        {
        	public boolean onEditorAction( TextView v, int actionId, KeyEvent event ) 
            {
                if( EditorInfo.IME_ACTION_DONE == actionId ) 
                {
                	actionDone();
                    return( true );
                }
                return( false );
            }
        } );
        
        // Setup on click button listeners
        
        cancelButton.setOnClickListener( new OnClickListener() 
        {
			public void onClick( View v ) 
			{
				dismiss();
			}
		} );
        
        addButton.setOnClickListener( new OnClickListener() 
        {	
			public void onClick( View v ) 
			{
				actionDone();
			}
		} );

        return( view );
    }
    
    private void actionDone()
    {
    	// Return input text to activity
    	AddCategoryDialogListener activity = (AddCategoryDialogListener)getActivity();
        activity.onFinishAddCategoryDialog( categoryNameEditText.getText().toString() );
        dismiss();
    }
}
