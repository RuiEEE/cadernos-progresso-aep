package pt.ruie.cadernoprogresso.models;

import pt.ruie.cadernoprogresso.App;
import android.database.Cursor;

public class Especialidade {

	App app;
	int id;
	int divisao;
	String label;
	
	public final static String TABLE = "especialidades";
	
	public Especialidade(App app,int id,String label,int divisao){
		this.app = app;
		this.id = id;
		this.label = label;
		this.divisao = divisao;
	}
	
	public Especialidade(App app,int id){
		this.app = app;
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDivisao(){
		return divisao;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void load(){
		Cursor c = app.getDB().getReadableDatabase().rawQuery("SELECT * FROM "+TABLE+" WHERE _id="+id, null);
		
		if(c.moveToFirst()){
			divisao = c.getInt(c.getColumnIndex("divisao"));
			label = c.getString(c.getColumnIndex("label"));
		} else {
			throw new NullPointerException("Esta especialiade não existe!");
		}
		
	}
	
	public static Cursor getProvas(App app,int id){
		return app.getDB().getReadableDatabase().rawQuery("SELECT * FROM "+TABLE+" WHERE _id="+id, null);
	}
	
	public static Cursor getEspecialidades(App app,int divisao){
		return app.getDB().getReadableDatabase().rawQuery("SELECT * FROM "+TABLE+" WHERE divisao="+divisao, null);
	}
	
}
