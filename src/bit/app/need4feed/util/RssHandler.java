package bit.app.need4feed.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
	// Feed and Post objects to use for temporary storage
	private Feed currentFeed;
	private Post currentPost;
	private ArrayList<Post> postList = new ArrayList<Post>();

	//Current characters being accumulated
	StringBuffer chars = new StringBuffer();

	@Override
	public void startElement( String uri, String localName, 
			                  String qName, Attributes atts ) 
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
			Log.d( "RSS Handler", "Post added." );
			postList.add( currentPost );
			currentPost = new Post();
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
	
	public ArrayList<Post> getLatestPosts( Feed feed )
	{
		URL url = null;
		
		currentFeed = feed;
		currentPost = new Post();
		
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

		return( postList );
	}
}
