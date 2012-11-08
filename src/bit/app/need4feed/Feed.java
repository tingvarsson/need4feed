package bit.app.need4feed;

public class Feed 
{
	protected long id;
	protected long categoryId;
	protected String name;
	
	public Feed( String name ) { this.name = name; }
	
	public long getId() { return( this.id ); }
	public void setId( long id ) { this.id = id; }
	
	public long getCategoryId() { return( this.categoryId ); }
	public void setCategoryId( long categoryId ) { this.categoryId = categoryId; }
	
	public String getName() { return( this.name ); }
	public void setName( String name ) { this.name = name; }
}
