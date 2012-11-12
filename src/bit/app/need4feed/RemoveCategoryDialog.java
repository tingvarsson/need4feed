package bit.app.need4feed;

import android.support.v4.app.DialogFragment;

public class RemoveCategoryDialog extends DialogFragment {
	
	public interface RemoveCategoryDialogListener
	{
		void onFinishRemoveCategoryDialog( int categoryId );
	}

	public RemoveCategoryDialog() 
	{
		// Empty constructor required for DialogFragment
	}

}
