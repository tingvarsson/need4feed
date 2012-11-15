package bit.app.need4feed.type;

import java.util.List;

import bit.app.need4feed.R;
import bit.app.need4feed.R.id;
import bit.app.need4feed.R.layout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter 
{
	private Activity activity;
	private List<Category> categoryList;
	private static LayoutInflater inflater = null;
	
	ViewHolder holder;

	public CategoryAdapter( Activity a, List<Category> cList )
	{
		activity = a;
		categoryList = cList;
		inflater = (LayoutInflater)activity
				   .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setCategoryList( List<Category> cList )
	{
		categoryList = cList;
	}

	public int getCount() 
	{
		return( this.categoryList.toArray().length );
	}

	public Object getItem( int position ) 
	{
		return( this.categoryList.get( position ) );
	}

	public long getItemId( int position ) 
	{
		// TODO: Used for? this.categoryList.get( position ).getId );
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
			vi = inflater.inflate( R.layout.row_category, null );
			holder = new ViewHolder();
			holder.label = (TextView)vi.findViewById( R.id.title_row_category );
			vi.setTag( holder );
		} 
		else
		{
			holder = (ViewHolder)vi.getTag();
		}

		holder.label.setText( categoryList.get( position ).getName() );

		return( vi );
	}
}
