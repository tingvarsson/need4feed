package bit.app.need4feed.type;


public class Category implements Comparable<Category>
{
	private long id;
	private String name;
	
	public Category() {}
	
	public Category( String name ) { this.name = name; }

	public long getId() { return( this.id ); }
	public void setId( long id ) { this.id = id; }
	
	public String getName() { return( this.name ); }
	public void setName( String name ) { this.name = name; }

	public int compareTo( Category another ) 
	{
		return( this.getName().compareToIgnoreCase( another.getName() ) );
	}
}
