package bit.app.need4feed;

public class Feed 
{
	protected long id;
	protected long categoryId;
	protected String title;
	protected String link;
	// TODO: Also available, do we need/want it? protected String description;
	
	// TODO: Add url to constructor
	public Feed( String title ) { this.title = title; }
	
	public long getId() { return( this.id ); }
	public void setId( long id ) { this.id = id; }
	
	public long getCategoryId() { return( this.categoryId ); }
	public void setCategoryId( long categoryId ) { this.categoryId = categoryId; }
	
	public String getTitle() { return( this.title ); }
	public void setTitle( String title ) { this.title = title; }
	
	public String getLink() { return( this.link ); }
	public void setLink( String link ) { this.link = link; }
}
