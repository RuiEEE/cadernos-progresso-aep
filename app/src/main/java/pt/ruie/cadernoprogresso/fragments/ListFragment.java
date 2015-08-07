package pt.ruie.cadernoprogresso.fragments;

import pt.ruie.cadernoprogresso.App;
import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.R;
import pt.ruie.cadernoprogresso.models.Divisao;
import pt.ruie.cadernoprogresso.models.Especialidade;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListFragment extends Fragment {
	
	int current_page;
	int divisao;
	MainActivity act;
	App app;
	View fragment_view;
	
	ListView lv;
	EspecialidadeAdapter mAdapter;
	
	public ListFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		act = (MainActivity)getActivity();
		app = (App)act.getApplication();
		fragment_view = inflater.inflate(R.layout.fragment_list, container, false);
		
		initVariables(fragment_view);
		
		return fragment_view;
	}
	
	public void initVariables(View v){
		lv = (ListView)v.findViewById(R.id.lv);
	}
	
	public void setup(int divisao){
		this.divisao = divisao;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateListView();
	}
	
	public void updateListView(){
		mAdapter = new EspecialidadeAdapter(act, Especialidade.getEspecialidades(app,divisao));
		lv.setAdapter(mAdapter);
	}
	
	
	public class EspecialidadeAdapter extends CursorAdapter {

		LayoutInflater mInflater;
		
		public EspecialidadeAdapter(Context context, Cursor c) {
			super(context, c);
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public void bindView(View v, Context context, Cursor cursor) {
			
			ViewHolder holder = (ViewHolder) v.getTag();
			
			holder.tvTitle.setText(cursor.getString(cursor.getColumnIndex("label")));
			holder.rlContainer.setTag(cursor.getInt(cursor.getColumnIndex("_id")));
			holder.rlContainer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int id = (Integer)v.getTag();
					act.swapEspFragment(id);
				}
			});
			
		}
		
		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup parent) {
			View v = mInflater.inflate(R.layout.row_especialidade, parent, false);
			ViewHolder holder = new ViewHolder();
			
			holder.tvTitle = (TextView)v.findViewById(R.id.tvTitle);
			holder.rlContainer = (RelativeLayout)v.findViewById(R.id.rlContainer);
			
			v.setTag(holder);
			return v;
		}
		
		class ViewHolder {
			TextView tvTitle;
			RelativeLayout rlContainer;
		}
		
	}

}
