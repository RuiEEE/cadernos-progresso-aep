package pt.ruie.cadernoprogresso.models;

import pt.ruie.cadernoprogresso.App;
import android.database.Cursor;

public class Prova extends Desafio {
	
	public Prova(App app,int id,String titulo,String descricao){
		super(app,id,titulo,descricao);
	}
	
	public static Cursor getProvas(App app,int divisao,int etapa){
		return app.getDB().getReadableDatabase().rawQuery("SELECT * FROM PROVAS WHERE divisao="+divisao+" AND etapa="+etapa, null);
	}
	
}
