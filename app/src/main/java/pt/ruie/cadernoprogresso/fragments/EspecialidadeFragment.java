package pt.ruie.cadernoprogresso.fragments;

import pt.ruie.cadernoprogresso.App;
import pt.ruie.cadernoprogresso.CursorCache;
import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.ProvaAdapter;
import pt.ruie.cadernoprogresso.R;
import pt.ruie.cadernoprogresso.models.Divisao;
import pt.ruie.cadernoprogresso.models.Especialidade;
import pt.ruie.cadernoprogresso.models.Prova;
import pt.ruie.cadernoprogresso.models.ProvaEspecialidade;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EspecialidadeFragment extends Fragment {
	
	int especialidade_id;
	MainActivity act;
	App app;
	View fragment_view;
	
	ListView lv;
	ProvaAdapter mAdapter;
	
	Especialidade e;
	int divisao;
	
	public EspecialidadeFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		act = (MainActivity)getActivity();
		app = (App)act.getApplication();
		fragment_view = inflater.inflate(R.layout.fragment_list, container, false);
		
		especialidade_id = getArguments().getInt("id");
		
		Especialidade e = new Especialidade(app,especialidade_id);
		e.load();
		
		divisao = e.getDivisao();
		
		switch(e.getDivisao()){
		case Divisao.ALCATEIA:
			getActivity().getActionBar().setTitle("Alcateia - Esp. "+e.getLabel());
			break;
		case Divisao.TES:
			getActivity().getActionBar().setTitle("Tribo de Escoteiros - Esp. "+e.getLabel());
			break;
		case Divisao.TEX:
			getActivity().getActionBar().setTitle("Tribo de Exploradores - Esp. "+e.getLabel());
			break;
		case Divisao.CLA:
			getActivity().getActionBar().setTitle("Clã - Esp. "+e.getLabel());
			break;
		}
		
		initVariables(fragment_view);
		
		return fragment_view;
	}
	
	public void initVariables(View v){
		
		lv = (ListView)v.findViewById(R.id.lv);
		lv.setDividerHeight(0);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateListView();
	}
	
	public void updateListView(){
		mAdapter = new ProvaAdapter(act, ProvaEspecialidade.getProvas(app, especialidade_id),divisao);
		lv.setAdapter(mAdapter);
	}
	

}
