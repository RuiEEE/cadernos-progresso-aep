package pt.ruie.cadernoprogresso;

import pt.ruie.cadernoprogresso.models.Prova;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

public class CursorCache {

	private static CursorCache mCursorCache;
	App app;
	
	private CursorCache(App app){
		this.app = app;
	}
	
	public static CursorCache getInstance(App app){
		if(mCursorCache == null)
			mCursorCache = new CursorCache(app);
		
		return mCursorCache;
	}
	
	public Cursor getProvas(int divisao,int etapa){
		return Prova.getProvas(app, divisao, etapa);
	}
	
}
