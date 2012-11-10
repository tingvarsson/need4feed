package bit.app.need4feed;

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

import android.util.Log;

class RssHandler extends DefaultHandler 
{
	// Feed and Post objects to use for temporary storage
	private Post currentPost = new Post();
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

		}
		if( ( localName.equalsIgnoreCase( "pubDate" ) ) &&
			( currentPost.getPubDate() == null ) )
		{
			currentPost.setPubDate( chars.toString() );

		}
		if( ( localName.equalsIgnoreCase( "thumbnail" ) ) &&
			( currentPost.getThumbnail() == null ) ) 
		{
			currentPost.setThumbnail( chars.toString() );

		}
		if( ( localName.equalsIgnoreCase( "link" ) ) &&
			( currentPost.getLink() == null ) )
		{
			currentPost.setLink( chars.toString() );
		}

		if( localName.equalsIgnoreCase( "item" ) ) 
		{
			postList.add(currentPost);
			currentPost = new Post();
		}
	}

	@Override
	public void characters( char ch[], int start, int length ) 
	{
		chars.append(new String(ch, start, length));
	}
	
	
	public ArrayList<Post> getLatestPosts( String feedLink ) 
	{
		URL url = null;
		
		try 
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			url = new URL( feedLink );

			xr.setContentHandler( this );
			xr.parse( new InputSource( url.openStream() ) );

		} catch ( IOException e ) {
			Log.e("RSS Handler IO", e.getMessage() + " >> " + e.toString());
		} catch ( SAXException e ) {
			Log.e("RSS Handler SAX", e.toString());
		} catch ( ParserConfigurationException e ) {
			Log.e("RSS Handler Parser Config", e.toString());
		}

		return( postList );
	}
}
