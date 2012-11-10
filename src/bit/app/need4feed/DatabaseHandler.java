package bit.app.need4feed;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String KEY_FEED_CATEGORY = "feed_category";
    
    // post keys
    private static final String KEY_POST_ID = "id";
    private static final String KEY_POST_TITLE = "post_title";
    private static final String KEY_POST_LINK = "post_link";
    private static final String KEY_POST_DESCRIPTION = "post_description";
    private static final String KEY_POST_PUBDATE = "post_pubdate";
    private static final String KEY_POST_THUMBNAIL = "post_thumbnail";
    private static final String KEY_POST_FEED = "post_feed";

    public DatabaseHandler( Context context ) 
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + 
			   "(" + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY ASC," + 
			   KEY_CATEGORY_NAME + " TEXT" + ")";
		
        db.execSQL( CREATE_CATEGORIES_TABLE );
        
	    String CREATE_TABLE_FEEDS = "CREATE TABLE " + TABLE_FEEDS + 
			   "(" + KEY_FEED_ID + " INTEGER PRIMARY KEY ASC," + 
			   KEY_FEED_TITLE + " TEXT," + KEY_FEED_LINK + 
			   " TEXT, FOREIGN KEY(" + KEY_FEED_CATEGORY + ") REFERENCES " +
			   TABLE_CATEGORIES + "(" + KEY_CATEGORY_ID + ")" + ")";
		
	    db.execSQL( CREATE_TABLE_FEEDS );
	     
	    String CREATE_TABLE_POSTS = "CREATE TABLE " + TABLE_POSTS + 
			   "(" + KEY_POST_ID + " INTEGER PRIMARY KEY ASC," + 
			   KEY_POST_TITLE + " TEXT," + KEY_POST_LINK + " TEXT," + 
			   KEY_POST_DESCRIPTION + " TEXT," + KEY_POST_PUBDATE + " TEXT," + 
			   KEY_POST_THUMBNAIL + " TEXT, FOREIGN KEY(" + KEY_POST_FEED + 
			   ") REFERENCES " + TABLE_CATEGORIES + "(" + KEY_FEED_ID + ")" + ")";
		
	    db.execSQL( CREATE_TABLE_POSTS );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
 
        // Create tables again
        onCreate(db);
	}
	
	
	public void addCategory( Category c )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put( KEY_CATEGORY_NAME, c.getName() );
	 
	    //  Insert the row, returned is the rowid used as category id
	    c.setId( db.insert( TABLE_CATEGORIES, null, values ) );
	    
	    db.close();
	}
	
	public void addFeed( Feed f )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put( KEY_FEED_TITLE, f.getTitle() );
	    values.put( KEY_FEED_LINK, f.getLink() );
	    values.put( KEY_FEED_CATEGORY, f.getCategoryId() );
	 
	    //  Insert the row, returned is the rowid used as feed id
	    f.setId( db.insert( TABLE_FEEDS, null, values ) );
	    
	    db.close();
	}
	
	public void addPost( Post p )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put( KEY_POST_TITLE, p.getTitle() );
	    values.put( KEY_POST_LINK, p.getLink() );
	    values.put( KEY_POST_DESCRIPTION, p.getDescription() );
	    values.put( KEY_POST_PUBDATE, p.getPubDate() );
	    values.put( KEY_POST_THUMBNAIL, p.getThumbnail() );
	    values.put( KEY_POST_FEED, p.getFeedId() );
	 
	    //  Insert the row, returned is the rowid used as feed id
	    p.setId( db.insert( TABLE_POSTS, null, values ) );
	    
	    db.close();
	}
	
	public int updateCategory( Category c )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_CATEGORY_NAME, c.getName() );
	 
	    // updating row
	    return db.update( TABLE_CATEGORIES, values, KEY_CATEGORY_ID + " = ?",
	                      new String[] { String.valueOf( c.getId() ) } );
	}
	
	public int updateFeed( Feed f )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put( KEY_FEED_TITLE, f.getTitle() );
	    values.put( KEY_FEED_LINK, f.getLink() );
	    values.put( KEY_FEED_CATEGORY, f.getCategoryId() );
	 
	    // updating row
	    return db.update( TABLE_CATEGORIES, values, KEY_FEED_ID + " = ?",
	                      new String[] { String.valueOf( f.getId() ) } );
	}
	
	public int updatePost( Post p )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put( KEY_POST_TITLE, p.getTitle() );
	    values.put( KEY_POST_LINK, p.getLink() );
	    values.put( KEY_POST_DESCRIPTION, p.getDescription() );
	    values.put( KEY_POST_PUBDATE, p.getPubDate() );
	    values.put( KEY_POST_THUMBNAIL, p.getThumbnail() );
	    values.put( KEY_POST_FEED, p.getFeedId() );
	 
	    // updating row
	    return db.update( TABLE_CATEGORIES, values, KEY_POST_ID + " = ?",
	                      new String[] { String.valueOf( p.getId() ) } );
	}
	
	public void deleteCategory( long categoryId )
	{
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete( TABLE_CATEGORIES, KEY_CATEGORY_ID + " = ?",
	               new String[] { String.valueOf( categoryId ) } );
	    db.close();
	}
	
	public void deleteFeed( long feedId )
	{
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete( TABLE_FEEDS, KEY_FEED_ID + " = ?",
	               new String[] { String.valueOf( feedId ) } );
	    db.close();
	}
	
	public void deletePost( long postId )
	{
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete( TABLE_POSTS, KEY_POST_ID + " = ?",
	               new String[] { String.valueOf( postId ) } );
	    db.close();
	}
	
	public List<Category> getCategories()
	{
		List<Category> categoryList = new ArrayList<Category>();
		
		// Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery( selectQuery, null );
	 
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
	    
		return( categoryList );
	}
	
	public List<Feed> getFeeds( long categoryId )
	{
		List<Feed> feedList = new ArrayList<Feed>();
		
		// Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_FEEDS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery( selectQuery, null );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	        	Feed f = new Feed( Integer.parseInt( cursor.getString( 0 ) ),
	    		                   Integer.parseInt( cursor.getString( 3 ) ),
	                               cursor.getString( 1 ),
	                               cursor.getString( 2 ) );
	        	feedList.add( f );
	        } while( cursor.moveToNext() );
	    }
		
		return( feedList );
	}
	
	public Feed getFeed( long feedId )
	{
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query( TABLE_FEEDS, new String[] { KEY_FEED_ID,
	    													  KEY_FEED_TITLE, 
	    		                                              KEY_FEED_LINK,
	    		                                              KEY_FEED_CATEGORY }, 
	                              KEY_FEED_ID + "=?",
	                              new String[] { String.valueOf( feedId ) }, 
	                              null, null, null, null);
	    
	    if( cursor != null )
	    {
	        cursor.moveToFirst();
	    }
	 
	    Feed f = new Feed( Integer.parseInt( cursor.getString( 0 ) ),
	    		           Integer.parseInt( cursor.getString( 3 ) ),
	                       cursor.getString( 1 ),
	                       cursor.getString( 2 ) );
		
		return( f );
	}
	
	public List<Post> getPosts( long feedId )
	{
		List<Post> postList = new ArrayList<Post>();
		
		// Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_POSTS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery( selectQuery, null );
	 
	    // looping through all rows and adding to list
	    if( cursor.moveToFirst() ) 
	    {
	        do
	        {
	        	Post p = new Post( Integer.parseInt( cursor.getString( 0 ) ),
	    		                   Integer.parseInt( cursor.getString( 6 ) ),
	                               cursor.getString( 1 ),
	                               cursor.getString( 2 ),
	                               cursor.getString( 3 ),
	                               cursor.getString( 4 ),
	                               cursor.getString( 5 ) );
	        	postList.add( p );
	        } while( cursor.moveToNext() );
	    }
	    
		return( postList );
	}
	
	public Post getPost( long postId )
	{
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query( TABLE_POSTS, new String[] { KEY_POST_ID,
	    													  KEY_POST_TITLE, 
	    		                                              KEY_POST_LINK,
	    		                                              KEY_POST_DESCRIPTION,
	    		                                              KEY_POST_PUBDATE,
	    		                                              KEY_POST_THUMBNAIL,
	    		                                              KEY_POST_FEED }, 
	    		                  KEY_POST_ID + "=?",
	                              new String[] { String.valueOf( postId ) }, 
	                              null, null, null, null);
	    
	    if( cursor != null )
	    {
	        cursor.moveToFirst();
	    }
	 
	    Post p = new Post( Integer.parseInt( cursor.getString( 0 ) ),
	    		           Integer.parseInt( cursor.getString( 6 ) ),
	                       cursor.getString( 1 ),
	                       cursor.getString( 2 ),
	                       cursor.getString( 3 ),
	                       cursor.getString( 4 ),
	                       cursor.getString( 5 ) );
		
		return( p );
	}
}
