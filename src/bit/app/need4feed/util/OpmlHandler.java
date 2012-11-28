package bit.app.need4feed.util;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import bit.app.need4feed.type.Category;

public class OpmlHandler extends DefaultHandler 
{
	public static final int POST_LIMIT = 30;
	
	private DatabaseHandler databaseHandler;
	private RssHandler rssHandler;
	
	// Category and Feed objects to use for temporary storage
	private Category currentCategory;
	private String currentFeedLink;
	
	//Current characters being accumulated
	StringBuffer chars = new StringBuffer();
	
	public OpmlHandler( DatabaseHandler db )
	{
		databaseHandler = db;
		rssHandler = new RssHandler( databaseHandler );
	}

	@Override
	public void startElement( String uri, String localName, String qName, 
			                  Attributes atts ) throws SAXException 
	{
		chars = new StringBuffer();

		if( localName.equalsIgnoreCase( "outline" ) )
		{
			if( currentCategory == null )
			{
				// New category
				currentCategory = new Category();
				currentCategory.setName( atts.getValue("text") );
				
				// Add it to the database rigth away so we get its id
				databaseHandler.addCategory( currentCategory );
			}
			else if( ( currentFeedLink == null ) && 
					 ( atts.getValue( "type" ).equalsIgnoreCase( "link" ) ) )
			{
				currentFeedLink = atts.getValue( "url" );
				
				// New feed that contains a link
				if( rssHandler.getFeed( currentFeedLink, currentCategory.getId() ) )
				{
					// Successfully added the feed
				}
				else
				{
					// Failed to add feed
				}	
			}
			else
			{
				// 3rd nested level of outlines, means trouble as we only
				// expect two levels; categories and feeds
				
				// TODO: Throw exception of some sort
				throw new SAXException();
			}
		}
	}

	@Override
	public void endElement( String uri, String localName, String qName ) throws SAXException 
	{
		if( localName.equalsIgnoreCase( "outline" ) ) 
		{
			if( currentCategory == null )
			{
				// FIXME: This shouldn't be possible, means we have an ending 
				// outline without a start!
			}
			else if( currentFeedLink == null )
			{
				// No open feeds means we are closing the category
				if( currentCategory.getName().equals( "" ) )
				{
					// Invalid category name, remove the category and any added
					// feeds
					databaseHandler.deleteCategory( currentCategory.getId() );
				}
				
				// Clear the current category to make room for a potential new
				currentCategory = null;
			}
			else
			{
				// As we don't allow deeper than 2 nested outlines this has to
				// mean the end of a feed
				
				// clear the current feed link to make room for a potential new
				currentFeedLink = null;
			}
		}
	}

	@Override
	public void characters( char ch[], int start, int length )
	{
		chars.append(new String(ch, start, length));
	}
	
	public void importFeeds()
	{
		URL url; // TODO: replace url and url.openStream with file and file.openStream i guess.
		
		currentCategory = null;
		currentFeedLink = null;
		
		try 
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			url = new URL( currentFeedLink );

			xr.setContentHandler( this );
			xr.parse( new InputSource( url.openStream() ) );

		} catch ( IOException e ) {
			Log.e( "OPML Handler IO", e.getMessage() + " >> " + e.toString() );
		} catch ( SAXException e ) {
			Log.e( "OPML Handler SAX", e.toString() );
		} catch ( ParserConfigurationException e ) {
			Log.e( "OPML Handler Parser Config", e.toString() );
		}
	}
	
	public void exportFeeds()
	{
		// TODO: Implement
	}
}
