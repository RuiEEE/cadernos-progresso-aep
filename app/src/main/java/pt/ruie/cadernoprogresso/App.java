package pt.ruie.cadernoprogresso;

import android.app.Application;

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
