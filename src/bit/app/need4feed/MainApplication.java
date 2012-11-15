package bit.app.need4feed;

import bit.app.need4feed.util.DatabaseHandler;
import android.app.Application;

public class MainApplication extends Application 
{
	private DatabaseHandler databaseHandler;
	 
    @Override
    public void onCreate() {
        super.onCreate();
        databaseHandler = new DatabaseHandler( this );
    }
     
    @Override
    public void onTerminate() 
    {
        super.onTerminate();
        databaseHandler.close();
    }
     
    public DatabaseHandler getDatabaseHandler() 
    {
        return( databaseHandler );
    }
}
