package bit.app.need4feed;

public class Feed  implements Comparable<Feed>
{
	protected long id;
	protected long categoryId;
	protected String title;
	protected String link;
	// TODO: Also available, do we need/want it? protected String description;
	
	public Feed( long categoryId, String title, String link ) 
	{ 
		this.categoryId = categoryId;
		this.title = title;
		this.link = link;
	}
	
	public Feed( long id, long categoryId, String title, String link ) 
	{ 
		this.id = id;
		this.categoryId = categoryId;
		this.title = title;
		this.link = link;
	}
	
	public long getId() { return( this.id ); }
	public void setId( long id ) { this.id = id; }
	
	public long getCategoryId() { return( this.categoryId ); }
	public void setCategoryId( long categoryId ) { this.categoryId = categoryId; }
	
	public String getTitle() { return( this.title ); }
	public void setTitle( String title ) { this.title = title; }
	
	public String getLink() { return( this.link ); }
	public void setLink( String link ) { this.link = link; }
	
	public int compareTo( Feed another ) 
	{
		return( this.getTitle().compareToIgnoreCase( another.getTitle() ) );
	}
}
