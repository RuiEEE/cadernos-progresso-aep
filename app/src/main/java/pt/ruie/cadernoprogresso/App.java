package pt.ruie.cadernoprogresso;

import java.io.IOException;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;

public class App extends Application {

	MyDatabase mDatabase;
	static public boolean DEBUG = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mDatabase = new MyDatabase(this);
		
	}
	
	public MyDatabase getDB(){
		return mDatabase;
	}
	
}
