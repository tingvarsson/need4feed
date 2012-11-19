package bit.app.need4feed.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import bit.app.need4feed.type.Feed;
import bit.app.need4feed.type.Post;

import android.util.Log;

public class RssHandler extends DefaultHandler 
{
	public static final int POST_LIMIT = 30;
	
	private DatabaseHandler db;
	// Feed and Post objects to use for temporary storage
	private Feed currentFeed;
	private Post currentPost;
	
	private List<Post> postList;

	//Current characters being accumulated
	StringBuffer chars = new StringBuffer();
	
	public RssHandler( DatabaseHandler db )
	{
		this.db = db;
	}

	@Override
	public void startElement( String uri, String localName, 
			                  String qName, Attributes atts ) throws SAXException 
	{
		chars = new StringBuffer();

		if( localName.equalsIgnoreCase( "item" ) )
		{
			if( currentFeed == null )
			{
				Log.d( "RSS Handler", "Feed added. Title: " + currentPost.getTitle() +
					   " , Link: " + currentPost.getLink() );
				currentFeed = new Feed();
				currentFeed.setTitle( currentPost.getTitle() );
				currentFeed.setLink( currentPost.getLink() );
				currentFeed.setDescription( currentPost.getDescription() );
				
				// Abort the parsing
				throw new SAXException();
			}
			currentPost = new Post();
		}
	}

	@Override
	public void endElement( String uri, String localName, 
			                String qName ) throws SAXException 
	{
		if( ( localName.equalsIgnoreCase( "title" ) ) &&
			( currentPost.getTitle() == null ) )
		{
			currentPost.setTitle( chars.toString() );
			Log.d( "RSS Handler", "Title: " + chars.toString() );
		}
		
		if( ( localName.equalsIgnoreCase( "pubDate" ) ) &&
			( currentPost.getPubDate() == null ) )
		{
			currentPost.setPubDate( chars.toString() );
			Log.d( "RSS Handler", "PubDate: " + chars.toString() );
		}
		
		if( ( localName.equalsIgnoreCase( "thumbnail" ) ) &&
			( currentPost.getThumbnail() == null ) ) 
		{
			currentPost.setThumbnail( chars.toString() );
			Log.d( "RSS Handler", "Thumbnail: " + chars.toString() );
		}
		
		if( ( localName.equalsIgnoreCase( "link" ) ) &&
			( currentPost.getLink() == null ) )
		{
			currentPost.setLink( chars.toString() );
			Log.d( "RSS Handler", "Link: " + chars.toString() );
		}
		
		if( ( localName.equalsIgnoreCase( "description" ) ) &&
				( currentPost.getDescription() == null ) )
		{
			currentPost.setDescription( chars.toString() );
			Log.d( "RSS Handler", "Description: " + chars.toString() );
		}

		if( localName.equalsIgnoreCase( "item" ) ) 
		{
			// Check if it is a new post or not
			if( postList.size() > 0 )
			{
				if( currentPost.compareTo( postList.get( 0 ) ) <= 0 )
				{
					// TODO: Equal dates are now considered a post we don't want
					// But could be that it is an updated version of that post?? Should we check more?
					Log.d( "RSS Handler", "No more new posts." );
					throw new SAXException();
				}
			}
			
			// Finalize the post
			currentPost.setFeedId( currentFeed.getId() );
			
			// Add it to the database
			db.addPost( currentPost );
			Log.d( "RSS Handler", "Post added." );
			
			int tooManyPosts = db.getPostCount( currentFeed.getId() ) - POST_LIMIT;
			
			if( tooManyPosts > 0 )
			{
				// TODO: Remove oldest posts!
			}
		}
	}

	@Override
	public void characters( char ch[], int start, int length ) 
	{
		chars.append(new String(ch, start, length));
	}
	
	public String verifyFeed( String link )
	{
		// TODO: implement
		// 1. If it is a normal site, check for rss-link inside
		// 2. If rss => test new, else => test old as RSS link
		// return rss link if working, else null?
		return( link );
	}
	
	public Feed getFeed( String link ) 
	{
		URL url = null;
		
		currentFeed = null;
		currentPost = new Post();

		String feedLink = verifyFeed( link );
		
		if( feedLink == null )
		{
			return( null );
		}
		
		try 
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			url = new URL( feedLink );

			xr.setContentHandler( this );
			Log.d( "RSS Handler", "Fetching: " + feedLink );
			xr.parse( new InputSource( url.openStream() ) );
			Log.d( "RSS Handler", "Fetch complete." );

		} catch ( IOException e ) {
			Log.e( "RSS Handler IO", e.getMessage() + " >> " + e.toString() );
		} catch ( SAXException e ) {
			Log.e( "RSS Handler SAX", e.toString() );
		} catch ( ParserConfigurationException e ) {
			Log.e( "RSS Handler Parser Config", e.toString() );
		}
		
		if( currentFeed != null )
		{
			currentFeed.setFeedLink( feedLink );
		}

		return( currentFeed );
	}
	
	public void getAllLatestPosts()
	{
		List<Feed> feedList = db.getAllFeeds();
		
		for( Feed f: feedList )
		{
			getLatestPosts( f );
		}
	}
	
	public void getLatestPosts( Feed feed )
	{
		URL url = null;
		
		currentFeed = feed;
		currentPost = new Post();
		
		// Fetch the current posts from the database to compare with
		postList = db.getPosts( currentFeed.getId() );
		Collections.sort( postList );
		
		try 
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			url = new URL( feed.getFeedLink() );

			xr.setContentHandler( this );
			Log.d( "RSS Handler", "Fetching: " + feed.getFeedLink() );
			xr.parse( new InputSource( url.openStream() ) );
			Log.d( "RSS Handler", "Fetch complete." );

		} catch ( IOException e ) {
			Log.e( "RSS Handler IO", e.getMessage() + " >> " + e.toString() );
		} catch ( SAXException e ) {
			Log.e( "RSS Handler SAX", e.toString() );
		} catch ( ParserConfigurationException e ) {
			Log.e( "RSS Handler Parser Config", e.toString() );
		}
	}
}
