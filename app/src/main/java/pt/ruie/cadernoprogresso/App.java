package pt.ruie.cadernoprogresso;

import java.io.IOException;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;

public class App extends Application {
	
	DBHelper mDatabase;
	static public boolean DEBUG = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mDatabase = new DBHelper(this);
		try {
			
			if(PreferenceManager.getDefaultSharedPreferences(this).getInt("provas_db_version", 0) < DBHelper.DB_VERSION){
				Log.d("App","doing the update?");
				mDatabase.forceUpdate();
			}
			mDatabase.createDatabase();
			mDatabase.openDataBase();
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
	}
	
	public DBHelper getDB(){
		return mDatabase;
	}
	
}
