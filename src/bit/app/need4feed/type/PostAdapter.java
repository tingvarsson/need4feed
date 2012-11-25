package bit.app.need4feed.type;

import java.util.List;

import bit.app.need4feed.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends BaseAdapter 
{
	private Activity activity;
	private List<Post> postList;
	private static LayoutInflater inflater = null;
	
	ViewHolder holder;

	public PostAdapter( Activity a, List<Post> pList ) 
	{
		activity = a;
		postList = pList;
		inflater = (LayoutInflater)activity
				   .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setPostList( List<Post> pList )
	{
		postList = pList;
	}

	public int getCount() 
	{
		return( postList.toArray().length );
	}

	public Object getItem( int position ) 
	{
		return( postList.get( position ) );
	}

	public long getItemId( int position ) 
	{
		return( this.postList.get( position ).getId() );
	}
	
	public static class ViewHolder
	{
		public ImageView thumb;
		public TextView label;
		public TextView details;
	}

	public View getView( int position, View convertView, ViewGroup parent ) 
	{
		View vi = convertView;

		if( convertView == null ) 
		{
			vi = inflater.inflate( R.layout.row_post, null );
			holder = new ViewHolder();
			holder.thumb = (ImageView)vi.findViewById(R.id.thumb_row_post );
			holder.label = (TextView)vi.findViewById( R.id.title_row_post );
			holder.details = (TextView)vi.findViewById(R.id.details_row_post );
			
			vi.setTag( holder );
		} 
		else
		{
			holder = (ViewHolder)vi.getTag();
		}

		holder.label.setText( postList.get( position ).getTitle() );
		// Mark un-read posts with bold font
		if( postList.get( position ).getRead() == false )
		{
			holder.label.setTypeface( null, Typeface.BOLD );
		}
		else
		{
			holder.label.setTypeface( null, Typeface.NORMAL );
		}
		holder.details.setText( postList.get( position ).getPubDate() );

		return( vi );
	}

}
