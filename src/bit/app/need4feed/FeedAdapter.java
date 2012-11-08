package bit.app.need4feed;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedAdapter extends BaseAdapter 
{
	private Activity activity;
	private ArrayList<Feed> feedList;
	private static LayoutInflater inflater = null;
	
	ViewHolder holder;

	public FeedAdapter( Activity a, ArrayList<Feed> fList ) 
	{
		activity = a;
		feedList = fList;
		inflater = (LayoutInflater)activity
				   .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() 
	{
		return( this.feedList.toArray().length );
	}

	public Object getItem( int position ) 
	{
		return( this.feedList.get( position ) );
	}

	public long getItemId( int position ) 
	{
		// TODO: Used for? this.feedList.get( position ).getId );
				return( position );
	}
	
	public static class ViewHolder 
	{
		public TextView label;
	}

	public View getView( int position, View convertView, ViewGroup parent ) 
	{
		View vi = convertView;

		if (convertView == null) 
		{
			vi = inflater.inflate( R.layout.row_feed, null );
			holder = new ViewHolder();
			holder.label = (TextView)vi.findViewById( R.id.title_row_feed );
			vi.setTag( holder );
		} 
		else
		{
			holder = (ViewHolder)vi.getTag();
		}

		holder.label.setText( feedList.get( position ).getName() );

		return( vi );
	}
}
