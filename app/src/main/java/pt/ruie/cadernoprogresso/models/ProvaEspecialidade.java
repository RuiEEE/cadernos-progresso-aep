package pt.ruie.cadernoprogresso.models;

import pt.ruie.cadernoprogresso.App;
import android.database.Cursor;

public class ProvaEspecialidade extends Desafio {
	
	public static final String TABLE = "provas_especialidade";
	
	int especialidade_id;
	
	public ProvaEspecialidade(App app,int id,int especialidade_id,String titulo,String descricao){
		super(app,id,titulo,descricao);
		this.especialidade_id = especialidade_id;
	}
	
	public int getEspecialidade_id(){
		return especialidade_id;
	}
	
	public static Cursor getProvas(App app,int especialidade_id){
		return app.getDB().getReadableDatabase().rawQuery("SELECT * FROM PROVAS_ESPECIALIDADE WHERE especialidade_id="+especialidade_id, null);
	}
	
}
