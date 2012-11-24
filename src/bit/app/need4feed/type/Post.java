package bit.app.need4feed.type;

public class Post implements Comparable<Post>
{
	protected long id;
	protected long feedId;
	protected String title;
	protected String link;
	protected String description;
	protected String pubDate;
	protected Long dateTime;
	protected String thumbnail;
	
	public Post() {}
	
	public Post( long id, long feedId, String title, String link, 
			     String description, String pubDate, long dateTime, String thumbnail )
	{
		this.id = id;
		this.feedId = feedId;
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.dateTime = dateTime;
		this.thumbnail = thumbnail;
	}
	
	public long getId() { return( this.id ); }
	public void setId( long id ) { this.id = id; }
	
	public long getFeedId() { return( this.feedId ); }
	public void setFeedId( long feedId ) { this.feedId = feedId; }
	
	public String getTitle() { return( this.title ); }
	public void setTitle( String title ) { this.title = title; }
	
	public String getLink() { return( this.link ); }
	public void setLink( String link ) { this.link = link; }
	
	public String getDescription() { return( this.description ); }
	public void setDescription( String description ) { this.description = description; }
	
	public String getPubDate() { return( this.pubDate ); }
	public void setPubDate( String pubDate ) { this.pubDate = pubDate; }
	
	public long getDateTime() { return( this.dateTime ); }
	public void setDateTime( long dateTime ) { this.dateTime = dateTime; }
	
	public String getThumbnail() { return( this.thumbnail ); }
	public void setThumbnail( String thumbnail ) { this.thumbnail = thumbnail; }

	public int compareTo( Post p ) 
	{
		return( this.dateTime.compareTo( p.dateTime ) );
	}
}
