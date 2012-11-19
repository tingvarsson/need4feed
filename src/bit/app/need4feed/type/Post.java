package bit.app.need4feed.type;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post implements Comparable<Post>
{
	protected long id;
	protected long feedId;
	protected String title;
	protected String link;
	protected String description;
	protected String pubDate;
	protected String thumbnail;
	
	public Post() {}
	
	public Post( long id, long feedId, String title, String link, 
			     String description, String pubDate, String thumbnail )
	{
		this.id = id;
		this.feedId = feedId;
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
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
	
	public String getThumbnail() { return( this.thumbnail ); }
	public void setThumbnail( String thumbnail ) { this.thumbnail = thumbnail; }

	public int compareTo( Post p ) 
	{
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		
		try 
		{
			Date date = formatter.parse( this.getPubDate() );
			Date dateOther = formatter.parse( p.getPubDate() );
			
			return( date.compareTo( dateOther ) );
			
		} catch (ParseException e) {
			e.printStackTrace();
			// FIXME: How to properly handle this situation? aka. when we can't parse the date
			// Should the actual parsing be moved perhaps? to a setPubdate or similar..
			return( -1 );
		}
	}
}
