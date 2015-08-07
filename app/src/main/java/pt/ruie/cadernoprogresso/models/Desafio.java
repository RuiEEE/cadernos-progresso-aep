package pt.ruie.cadernoprogresso.models;

import pt.ruie.cadernoprogresso.App;

public class Desafio {
	
	App app;
	int id;
	String titulo;
	String descricao;
	
	public Desafio(App app,int id,String titulo,String descricao){
		this.app = app;
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
