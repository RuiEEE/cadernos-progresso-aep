package pt.ruie.cadernoprogresso.fragments;

import pt.ruie.cadernoprogresso.App;
import pt.ruie.cadernoprogresso.CursorCache;
import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.ProvaAdapter;
import pt.ruie.cadernoprogresso.R;
import pt.ruie.cadernoprogresso.models.Divisao;
import pt.ruie.cadernoprogresso.models.Especialidade;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CadernoFragment extends Fragment {
	
	ListView lv;
	MainActivity act;
	App app;
	
	View fragment_view;
	
	ProvaAdapter mProvaAdapter;
	
	int divisao;
	int etapa;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		act = (MainActivity)getActivity();
		app = (App)act.getApplication();
		fragment_view = inflater.inflate(R.layout.fragment_list, container, false);
		
		initVariables(fragment_view);
		
		return fragment_view;
	}
	
	public void initVariables(View v){		
		lv = (ListView)v.findViewById(R.id.lv);
		lv.setDividerHeight(0);
	}
	
	public void setup(int divisao,int etapa){
		this.divisao = divisao;
		this.etapa = etapa;
	}
	
	public void updateListView(){
		CursorCache mCache = CursorCache.getInstance(app);
		mProvaAdapter = new ProvaAdapter(act, mCache.getProvas(divisao, etapa),divisao);
		lv.setAdapter(mProvaAdapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateListView();
	}
	
	

}
