package pt.ruie.cadernoprogresso;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class App extends Application {

	MyDatabase mDatabase;
	static public boolean DEBUG = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());
		mDatabase = new MyDatabase(this);
		
	}
	
	public MyDatabase getDB(){
		return mDatabase;
	}
	
}
