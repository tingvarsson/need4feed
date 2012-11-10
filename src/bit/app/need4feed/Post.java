package bit.app.need4feed;

public class Post 
{
	protected long id;
	protected long feedId;
	protected String title;
	protected String link;
	protected String description;
	protected String pubDate;
	protected String thumbnail;
	
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
	
	public String getThumbnail() { return( this.thumbnail ); }
	public void setThumbnail( String thumbnail ) { this.thumbnail = thumbnail; }
}
