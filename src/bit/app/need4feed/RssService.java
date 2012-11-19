package bit.app.need4feed;

import java.util.Timer;
import java.util.TimerTask;

import bit.app.need4feed.util.DatabaseHandler;
import bit.app.need4feed.util.RssHandler;

import android.app.IntentService;
import android.content.Intent;

public class RssService extends IntentService 
{
	private static final long DELAY_TIME = 100;
	private static final long UPDATE_TIME = 1000 * 60 * 1;

	public RssService() 
	{
		super( "rss_service" );
	}

    Timer t = new Timer(); 
    
    @Override 
    protected void onHandleIntent( Intent intent ) 
    { 
        t.schedule( new TimerTask() { 
            @Override 
            public void run() 
            { 
                // Execute
            	MainApplication appContext = (MainApplication)getApplicationContext();
                DatabaseHandler databaseHandler = appContext.getDatabaseHandler();
                
            	RssHandler rssHandler = new RssHandler( databaseHandler );
            	
            	rssHandler.getAllLatestPosts();
            } 
        }, DELAY_TIME, UPDATE_TIME ); 
    }
}
