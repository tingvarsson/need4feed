package bit.app.need4feed.util;

import java.util.ArrayList;
import java.util.List;

import bit.app.need4feed.type.Category;
import bit.app.need4feed.type.Feed;
import bit.app.need4feed.type.Post;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper 
{
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "feeds";
 
    // table names
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_FEEDS = "feeds";
    private static final String TABLE_POSTS = "posts";
 
    // category keys
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CATEGORY_NAME = "category_name";
    
    // feed keys
    private static final String KEY_FEED_ID = "feed_id";
    private static final String KEY_FEED_TITLE = "feed_title";
    private static final String KEY_FEED_LINK = "feed_link";
    private static final String KEY_FEED_FEEDLINK = "feed_feedlink";
    private static final String KEY_FEED_DESCRIPTION = "feed_description";
    private static final String KEY_FEED_CATEGORY = "feed_category";
    
    // post keys
    private static final String KEY_POST_ID = "id";
    private static final String KEY_POST_TITLE = "post_title";
    private static final String KEY_POST_LINK = "post_link";
    private static final String KEY_POST_DESCRIPTION = "post_description";
    private static final String KEY_POST_PUBDATE = "post_pubdate";
    private static final String KEY_POST_DATETIME = "post_datetime";
    private static final String KEY_POST_THUMBNAIL = "post_thumbnail";
    private static final String KEY_POST_READ = "post_read";
    private static final String KEY_POST_FEED = "post_feed";
    
    private SQLiteDatabase db;

    public DatabaseHandler( Context context ) 
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
        
        this.db = this.getWritableDatabase();
    }
    
	@Override
	public void onCreate( SQLiteDatabase db ) 
	{
		String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + 
			   "(" + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY ASC, " + 
			   KEY_CATEGORY_NAME + " TEXT" + ")";
		
        db.execSQL( CREATE_CATEGORIES_TABLE );
        
	    String CREATE_TABLE_FEEDS = "CREATE TABLE " + TABLE_FEEDS + 
			   "(" + KEY_FEED_ID + " INTEGER PRIMARY KEY ASC, " + 
			   KEY_FEED_TITLE + " TEXT, " + KEY_FEED_LINK + 
			   " TEXT, " + KEY_FEED_FEEDLINK + " TEXT, " + KEY_FEED_DESCRIPTION + 
			   " TEXT, " + KEY_FEED_CATEGORY + " INTEGER, FOREIGN KEY(" + 
			   KEY_FEED_CATEGORY + ") REFERENCES " + TABLE_CATEGORIES + "(" + 
			   KEY_CATEGORY_ID + ")" + ")";
		
	    db.execSQL( CREATE_TABLE_FEEDS );
	     
	    String CREATE_TABLE_POSTS = "CREATE TABLE " + TABLE_POSTS + 
			   "(" + KEY_POST_ID + " INTEGER PRIMARY KEY ASC, " + 
			   KEY_POST_TITLE + " TEXT, " + KEY_POST_LINK + " TEXT, " + 
			   KEY_POST_DESCRIPTION + " TEXT, " + KEY_POST_PUBDATE + " TEXT, " + 
			   KEY_POST_DATETIME + " LONG, " + KEY_POST_THUMBNAIL + " TEXT, " + 
			   KEY_POST_READ + " INTEGER, " + KEY_POST_FEED + 
			   " INTEGER, FOREIGN KEY(" + KEY_POST_FEED +  
			   ") REFERENCES " + TABLE_FEEDS + "(" + KEY_FEED_ID + ")" + ")";
		
	    db.execSQL( CREATE_TABLE_POSTS );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) 
	{
		// Drop older tables if existed
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_CATEGORIES );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_FEEDS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_POSTS );
 
        // Create tables again
        onCreate( db );
	}
	
	
	public void addCategory( Category c )
	{
	    ContentValues values = new ContentValues();
	    values.put( KEY_CATEGORY_NAME, c.getName() );
	 
	    //  Insert the row, returned is the rowid used as category id
	    c.setId( this.db.insert( TABLE_CATEGORIES, null, values ) );
	    
	    Log.d( "Database Handler", "Category added, id: " + 
	           Long.toString( c.getId() ) + " , name: " + c.getName() );
	}
	
	public void addFeed( Feed f )
	{
	    ContentValues values = new ContentValues();
	    values.put( KEY_FEED_TITLE, f.getTitle() );
	    values.put( KEY_FEED_LINK, f.getLink() );
	    values.put( KEY_FEED_FEEDLINK, f.getFeedLink() );
	    values.put( KEY_FEED_DESCRIPTION, f.getDescription() );
	    values.put( KEY_FEED_CATEGORY, f.getCategoryId() );
	 
	    //  Insert the row, returned is the rowid used as feed id
	    f.setId( this.db.insert( TABLE_FEEDS, null, values ) );
	    
	    Log.d( "Database Handler", "Feed added, id: " + Long.toString( f.getId() ) +
	    	   " , title: " + f.getTitle() + " , link: " + f.getLink() + 
	    	   " , feed link: " + f.getFeedLink() + " , description: " + 
	    	   f.getDescription() + " , category id: " + Long.toString( f.getCategoryId() ) );
	}
	
	public void addPost( Post p )
	{
	    ContentValues values = new ContentValues();
	    values.put( KEY_POST_TITLE, p.getTitle() );
	    values.put( KEY_POST_LINK, p.getLink() );
	    values.put( KEY_POST_DESCRIPTION, p.getDescription() );
	    values.put( KEY_POST_PUBDATE, p.getPubDate() );
	    values.put( KEY_POST_DATETIME, p.getDateTime() );
	    values.put( KEY_POST_THUMBNAIL, p.getThumbnail() );
	    values.put( KEY_POST_READ, p.getRead() ? 1 : 0 );
	    values.put( KEY_POST_FEED, p.getFeedId() );
	 
	    //  Insert the row, returned is the rowid used as feed id
	    p.setId( this.db.insert( TABLE_POSTS, null, values ) );
	    
	    Log.d( "Database Handler", "Post added, id: " + Long.toString( p.getId() ) +
	    	   " , title: " + p.getTitle() + " , link: " + p.getLink() + 
	    	   " , description: " + p.getDescription() + " , pubdate: " + 
	    	   p.getPubDate() + " , thumbnail: " + p.getThumbnail() + 
	    	   " , category id: " + Long.toString( p.getFeedId() ) );
	}
	
	public void updateCategory( Category c )
	{
	    ContentValues values = new ContentValues();
	    values.put( KEY_CATEGORY_NAME, c.getName() );
	 
	    // updating row
	    this.db.update( TABLE_CATEGORIES, values, KEY_CATEGORY_ID + " = ?",
	               new String[] { String.valueOf( c.getId() ) } );
	}
	
	public void updateFeed( Feed f )
	{
	    ContentValues values = new ContentValues();
	    values.put( KEY_FEED_TITLE, f.getTitle() );
	    values.put( KEY_FEED_LINK, f.getLink() );
	    values.put( KEY_FEED_FEEDLINK, f.getFeedLink() );
	    values.put( KEY_FEED_DESCRIPTION, f.getDescription() );
	    values.put( KEY_FEED_CATEGORY, f.getCategoryId() );
	 
	    // updating row
	    this.db.update( TABLE_FEEDS, values, KEY_FEED_ID + " = ?",
	               new String[] { String.valueOf( f.getId() ) } );
	}
	
	public void updatePost( Post p )
	{
	    ContentValues values = new ContentValues();
	    values.put( KEY_POST_TITLE, p.getTitle() );
	    values.put( KEY_POST_LINK, p.getLink() );
	    values.put( KEY_POST_DESCRIPTION, p.getDescription() );
	    values.put( KEY_POST_PUBDATE, p.getPubDate() );
	    values.put( KEY_POST_DATETIME, p.getDateTime() );
	    values.put( KEY_POST_THUMBNAIL, p.getThumbnail() );
	    values.put( KEY_POST_READ, p.getRead() ? 1 : 0 );
	    values.put( KEY_POST_FEED, p.getFeedId() );
	 
	    // updating row
	    this.db.update( TABLE_POSTS, values, KEY_POST_ID + " = ?",
	               new String[] { String.valueOf( p.getId() ) } );
	}
	
	public void deleteCategory( long categoryId )
	{
		List<Feed> feedList = getFeeds( categoryId );
		
		// Delete all posts that belongs to the feeds as well as the feeds
		// that belong to category id
		for( Feed f : feedList )
		{
			deletePostOfFeed( f.getId() );
			deleteFeed( f.getId() );
		}
		
		this.db.delete( TABLE_CATEGORIES, KEY_CATEGORY_ID + " = ?",
	               new String[] { String.valueOf( categoryId ) } );
	}
	
	public void deleteFeed( long feedId )
	{
		// Delete all posts belonging to the feed
		deletePostOfFeed( feedId );
		
		// Delete the actual feed
		this.db.delete( TABLE_FEEDS, KEY_FEED_ID + " = ?",
	               new String[] { String.valueOf( feedId ) } );
	}
	
	public void deletePostOfFeed( long feedId )
	{
		this.db.delete( TABLE_POSTS, KEY_POST_FEED + " = ?",
	               new String[] { String.valueOf( feedId ) } );
	}
	
	public void deletePost( long postId )
	{
		this.db.delete( TABLE_POSTS, KEY_POST_ID + " = ?",
	               new String[] { String.valueOf( postId ) } );
	}
	
	public List<Category> getCategories()
	{
		List<Category> categoryList = new ArrayList<Category>();
		
		// Select All Query
	    String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES + 
	    		             " ORDER BY "+ KEY_CATEGORY_NAME + " COLLATE NOCASE";
	 
	    Cursor cursor = this.db.rawQuery( selectQuery, null );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	            Category c = new Category( cursor.getString(1) );
	            c.setId( Integer.parseInt( cursor.getString(0) ) );
	            categoryList.add( c );
	        } while( cursor.moveToNext() );
	    }
	    
	    cursor.close();
	    
		return( categoryList );
	}
	
	public String[] getCategoryNames()
	{
		ArrayList<String> categoryNames = new ArrayList<String>();
		
		// Select All Query
	    String selectQuery = "SELECT " + KEY_CATEGORY_NAME + " FROM " + TABLE_CATEGORIES + 
	    		             " ORDER BY "+ KEY_CATEGORY_NAME + " COLLATE NOCASE";
	 
	    Cursor cursor = this.db.rawQuery( selectQuery, null );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	        	categoryNames.add( cursor.getString( 0 ) );
	        } while( cursor.moveToNext() );
	    }
	    
	    cursor.close();
	    
	    // Return it as a String[], the new String[0] is to force the function
	    // to accept the type
		return( (String[])categoryNames.toArray( new String[0] ) );
	}
	
	public List<Feed> getAllFeeds()
	{
		List<Feed> feedList = new ArrayList<Feed>();
		
		// Select All Query
	    String selectQuery = "SELECT * FROM " + TABLE_FEEDS +
	                         " ORDER BY "+ KEY_FEED_TITLE + " COLLATE NOCASE";
	 
	    Cursor cursor = this.db.rawQuery( selectQuery, null );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	        	Feed f = new Feed( Integer.parseInt( cursor.getString( 0 ) ),
	    		                   Integer.parseInt( cursor.getString( 5 ) ),
	                               cursor.getString( 1 ),
	                               cursor.getString( 2 ),
	                               cursor.getString( 3 ),
	                               cursor.getString( 4 ) );
	        	feedList.add( f );
	        } while( cursor.moveToNext() );
	    }
	    
	    cursor.close();
		
		return( feedList );
	}
	
	public List<Feed> getFeeds( long categoryId )
	{
		List<Feed> feedList = new ArrayList<Feed>();
		
		// Select All Query
	    String selectQuery = "SELECT * FROM " + TABLE_FEEDS +  
				             " WHERE " + KEY_FEED_CATEGORY + " =? " + 
	                         " ORDER BY "+ KEY_FEED_TITLE + " COLLATE NOCASE";
	 
	    Cursor cursor = this.db.rawQuery( selectQuery, 
	    		                          new String[] { Long.toString( categoryId ) } );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	        	Feed f = new Feed( Integer.parseInt( cursor.getString( 0 ) ),
	    		                   Integer.parseInt( cursor.getString( 5 ) ),
	                               cursor.getString( 1 ),
	                               cursor.getString( 2 ),
	                               cursor.getString( 3 ),
	                               cursor.getString( 4 ) );
	        	feedList.add( f );
	        } while( cursor.moveToNext() );
	    }
	    
	    cursor.close();
		
		return( feedList );
	}
	
	public String[] getFeedNames( long categoryId )
	{
		ArrayList<String> feedNames = new ArrayList<String>();
		
		// Select All Query
	    String selectQuery = "SELECT " + KEY_FEED_TITLE + " FROM " + TABLE_FEEDS + 
	    					 " WHERE " + KEY_FEED_CATEGORY + " =? " + 
	                         " ORDER BY "+ KEY_FEED_TITLE + " COLLATE NOCASE";
	 
	    Cursor cursor = this.db.rawQuery( selectQuery, 
                                          new String[] { Long.toString( categoryId ) } );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	        	feedNames.add( cursor.getString( 0 ) );
	        } while( cursor.moveToNext() );
	    }
	    
	    cursor.close();
		
		return( (String[])feedNames.toArray( new String[0] ) );
	}
	
	public Feed getFeed( long feedId )
	{
		Feed f = null;
		
	    Cursor cursor = this.db.query( TABLE_FEEDS, new String[] { KEY_FEED_ID,
	    													  KEY_FEED_TITLE, 
	    		                                              KEY_FEED_LINK,
	    		                                              KEY_FEED_FEEDLINK,
	    		                                              KEY_FEED_DESCRIPTION,
	    		                                              KEY_FEED_CATEGORY }, 
	                              KEY_FEED_ID + "=?",
	                              new String[] { String.valueOf( feedId ) }, 
	                              null, null, null, null );
	    
	    if( cursor != null )
	    {
	        cursor.moveToFirst();
	        
	        // FIXE: What if empty cursor? Double check all db funcs on this.
	        
	        f = new Feed( Integer.parseInt( cursor.getString( 0 ) ),
	    		          Integer.parseInt( cursor.getString( 5 ) ),
	                      cursor.getString( 1 ),
	                      cursor.getString( 2 ),
	                      cursor.getString( 3 ),
                          cursor.getString( 4 ) );
	    }
	 
	    
	    
	    cursor.close();
		
		return( f );
	}
	
	public List<Post> getPosts( long feedId )
	{
		List<Post> postList = new ArrayList<Post>();
		
		// Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_POSTS + " WHERE " + 
		                     KEY_POST_FEED + " =?" + " ORDER BY " + 
	    		             KEY_POST_DATETIME + " DESC";
	 
	    Cursor cursor = this.db.rawQuery( selectQuery, 
                                          new String[] { Long.toString( feedId ) } );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	        	Post p = new Post( cursor.getInt( 0 ),
	    		                   cursor.getInt( 8 ),
	                               cursor.getString( 1 ),
	                               cursor.getString( 2 ),
	                               cursor.getString( 3 ),
	                               cursor.getString( 4 ),
	                               cursor.getLong( 5 ),
	                               cursor.getString( 6 ),
	                               ( cursor.getInt( 7 ) == 1 ) );
	        	postList.add( p );
	        } while( cursor.moveToNext() );
	    }
	    
	    cursor.close();
	    
		return( postList );
	}
	
	public Post getPost( long postId )
	{
	    Cursor cursor = this.db.query( TABLE_POSTS, new String[] { KEY_POST_ID,
	    													  KEY_POST_TITLE, 
	    		                                              KEY_POST_LINK,
	    		                                              KEY_POST_DESCRIPTION,
	    		                                              KEY_POST_PUBDATE,
	    		                                              KEY_POST_DATETIME,
	    		                                              KEY_POST_THUMBNAIL,
	    		                                              KEY_POST_READ,
	    		                                              KEY_POST_FEED }, 
	    		                  KEY_POST_ID + "=?",
	                              new String[] { String.valueOf( postId ) }, 
	                              null, null, null, null);
	    
	    if( cursor != null )
	    {
	        cursor.moveToFirst();
	    }
	 
	    Post p = new Post( cursor.getInt( 0 ),
                           cursor.getInt( 8 ),
                           cursor.getString( 1 ),
                           cursor.getString( 2 ),
                           cursor.getString( 3 ),
                           cursor.getString( 4 ),
                           cursor.getLong( 5 ),
                           cursor.getString( 6 ),
                           ( cursor.getInt( 7 ) == 1 ) );
		
	    cursor.close();
	    
		return( p );
	}
	
	public Post getNewerPost( Post oldPost )
	{
	    Cursor cursor = this.db.query( TABLE_POSTS, new String[] { KEY_POST_ID,
	    													  KEY_POST_TITLE, 
	    		                                              KEY_POST_LINK,
	    		                                              KEY_POST_DESCRIPTION,
	    		                                              KEY_POST_PUBDATE,
	    		                                              KEY_POST_DATETIME,
	    		                                              KEY_POST_THUMBNAIL,
	    		                                              KEY_POST_READ,
	    		                                              KEY_POST_FEED }, 
	    		                  KEY_POST_FEED + "=? AND " + KEY_POST_DATETIME + ">?",
	                              new String[] { String.valueOf( oldPost.getFeedId() ),
	    		                                 String.valueOf( oldPost.getDateTime() ) }, 
	                              null, null, KEY_POST_DATETIME, null);
	    
	    if( cursor != null )
	    {
	        cursor.moveToFirst();
	    }
	 
	    Post p = new Post( cursor.getInt( 0 ),
                           cursor.getInt( 8 ),
                           cursor.getString( 1 ),
                           cursor.getString( 2 ),
                           cursor.getString( 3 ),
                           cursor.getString( 4 ),
                           cursor.getLong( 5 ),
                           cursor.getString( 6 ),
                           ( cursor.getInt( 7 ) == 1 ) );
		
	    cursor.close();
	    
		return( p );
	}
	
	public Post getOlderPost( Post oldPost )
	{
	    Cursor cursor = this.db.query( TABLE_POSTS, new String[] { KEY_POST_ID,
	    													  KEY_POST_TITLE, 
	    		                                              KEY_POST_LINK,
	    		                                              KEY_POST_DESCRIPTION,
	    		                                              KEY_POST_PUBDATE,
	    		                                              KEY_POST_DATETIME,
	    		                                              KEY_POST_THUMBNAIL,
	    		                                              KEY_POST_READ,
	    		                                              KEY_POST_FEED }, 
	    		                  KEY_POST_FEED + "=? AND " + KEY_POST_DATETIME + "<?",
	                              new String[] { String.valueOf( oldPost.getFeedId() ),
	    		                                 String.valueOf( oldPost.getDateTime() ) }, 
	                              null, null, KEY_POST_DATETIME + " DESC", null);
	    
	    if( cursor != null )
	    {
	        cursor.moveToFirst();
	    }
	 
	    Post p = new Post( cursor.getInt( 0 ),
                           cursor.getInt( 8 ),
                           cursor.getString( 1 ),
                           cursor.getString( 2 ),
                           cursor.getString( 3 ),
                           cursor.getString( 4 ),
                           cursor.getLong( 5 ),
                           cursor.getString( 6 ),
                           ( cursor.getInt( 7 ) == 1 ) );
		
	    cursor.close();
	    
		return( p );
	}
	
	public int getPostCount( long feedId )
	{
		int count;
		
		String countQuery = "SELECT  * FROM " + TABLE_POSTS + " WHERE " + 
                            KEY_POST_FEED + " =?";
        
        Cursor cursor = this.db.rawQuery( countQuery, 
        		                          new String[] { Long.toString( feedId ) } );
        count = cursor.getCount();
        
        cursor.close();
 
        // return count
        return( count );
	}
	
	public int getUnreadPostCount( long feedId )
	{
		int count;
		
		String countQuery = "SELECT  * FROM " + TABLE_POSTS + " WHERE " + 
                            KEY_POST_FEED + " =? AND " + KEY_POST_READ + " =?";
        
        Cursor cursor = this.db.rawQuery( countQuery, 
        		                          new String[] { Long.toString( feedId ), 
        		                                         Integer.toString( 0 ) } );
        count = cursor.getCount();
        
        cursor.close();
 
        // return count
        return( count );
	}
	
	public int getAllUnreadPostCount()
	{
		int count;
		
		String countQuery = "SELECT  * FROM " + TABLE_POSTS + " WHERE " + 
                            KEY_POST_READ + " =?";
        
        Cursor cursor = this.db.rawQuery( countQuery, 
        		                          new String[] { Integer.toString( 0 ) } );
        count = cursor.getCount();
        
        cursor.close();
 
        // return count
        return( count );
	}
}
