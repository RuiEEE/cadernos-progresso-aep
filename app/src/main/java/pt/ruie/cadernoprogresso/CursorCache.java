package pt.ruie.cadernoprogresso;

import pt.ruie.cadernoprogresso.models.Prova;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

public class CursorCache {
	
	SparseArray<Cursor> cursors;
	
	private static CursorCache mCursorCache;
	App app;
	
	private CursorCache(App app){
		this.app = app;
		cursors = new SparseArray<Cursor>();
	}
	
	public static CursorCache getInstance(App app){
		if(mCursorCache == null)
			mCursorCache = new CursorCache(app);
		
		return mCursorCache;
	}
	
	public Cursor getProvas(int divisao,int etapa){
		
		Cursor res = null;
		
		if(containsCursor(divisao, etapa)){
			res = cursors.get(Integer.valueOf(divisao+""+etapa));
//			Log.d("cursor cache","Tem cursor");
		} else {
//			Log.d("cursor cache","NAO Tem cursor - buscar bd");
			res = Prova.getProvas(app, divisao, etapa);
			cursors.put(Integer.valueOf(divisao+""+etapa), res);
		}
		
		return res;
		
	}
	
	public boolean containsCursor(int divisao,int etapa){
		return cursors.get(Integer.valueOf(divisao+""+etapa)) != null;
	}
	
	
}
