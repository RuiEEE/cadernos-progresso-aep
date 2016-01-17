package pt.ruie.cadernoprogresso;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

	MyDatabase mDatabase;
	static public boolean DEBUG = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());
		deleteDatabaseOnce();
		mDatabase = new MyDatabase(this);
	}

	public void deleteDatabaseOnce(){
		if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("delete_database",false)) {
			boolean deleted = deleteDatabase("provas");
			Log.d("App", "Deleted database: " + deleted);
			PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("delete_database",true).commit();
		}
	}
	
	public MyDatabase getDB(){
		return mDatabase;
	}
	
}
