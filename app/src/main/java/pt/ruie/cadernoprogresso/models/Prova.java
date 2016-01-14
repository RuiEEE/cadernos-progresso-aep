package pt.ruie.cadernoprogresso.models;

import pt.ruie.cadernoprogresso.App;
import android.database.Cursor;
import android.util.Log;

public class Prova extends Desafio {
	
	public Prova(App app,int id,String titulo,String descricao){
		super(app,id,titulo,descricao);
	}
	
	public static Cursor getProvas(App app,int divisao,int etapa){
		return app.getDB().getReadableDatabase().rawQuery("SELECT * FROM PROVAS WHERE divisao="+divisao+" AND etapa="+etapa, null);
	}

	public boolean isConcluded(int divisao,int etapa){
		int _id = 0;
		Cursor c = app.getDB().getReadableDatabase().rawQuery("SELECT _id FROM PROVAS WHERE divisao="+divisao+" AND etapa="+etapa,null);
		c.moveToFirst();
		if(c.moveToFirst()){
			Log.d("PRova", "A devolver a minnha pita " + c.getInt(0));
			return app.getDB().getReadableDatabase().rawQuery("SELECT is_concluded FROM PROVAS WHERE _id="+c.getInt(0),null).moveToFirst();
		}

		return false;
	}
	
}
