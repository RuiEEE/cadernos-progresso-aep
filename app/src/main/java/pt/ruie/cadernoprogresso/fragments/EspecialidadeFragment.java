package pt.ruie.cadernoprogresso.fragments;

import pt.ruie.cadernoprogresso.App;
import pt.ruie.cadernoprogresso.CursorCache;
import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.R;
import pt.ruie.cadernoprogresso.models.Desafio;
import pt.ruie.cadernoprogresso.models.Divisao;
import pt.ruie.cadernoprogresso.models.Especialidade;
import pt.ruie.cadernoprogresso.models.ProvaEspecialidade;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EspecialidadeFragment extends Fragment {
	
	int especialidade_id;
	MainActivity act;
	App app;
	View fragment_view;
	
	ListView lv;
	ChallengeSpecAdapter mAdapter;

	MainActivity mActivity;
	
	Especialidade e;
	int divisao;
	
	public EspecialidadeFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		act = (MainActivity)getActivity();
		app = (App)act.getApplication();
		fragment_view = inflater.inflate(R.layout.fragment_list, container, false);

		mActivity = (MainActivity)getActivity();
		
		especialidade_id = getArguments().getInt("id");
		
		Especialidade e = new Especialidade(app,especialidade_id);
		e.load();
		
		divisao = e.getDivisao();
		
		switch(e.getDivisao()){
		case Divisao.ALCATEIA:
			mActivity.setActionBarTitle("Alcateia - Esp. " + e.getLabel());
			break;
		case Divisao.TES:
			mActivity.setActionBarTitle("Tribo de Escoteiros - Esp. " + e.getLabel());
			break;
		case Divisao.TEX:
			mActivity.setActionBarTitle("Tribo de Exploradores - Esp. " + e.getLabel());
			break;
		case Divisao.CLA:
			mActivity.setActionBarTitle("Clã - Esp. "+e.getLabel());
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
		mAdapter = new ChallengeSpecAdapter(act, ProvaEspecialidade.getProvas(app, especialidade_id));
		lv.setAdapter(mAdapter);
	}

	class ChallengeSpecAdapter extends CursorAdapter implements CompoundButton.OnCheckedChangeListener {

		LayoutInflater mInflater;

		public ChallengeSpecAdapter(MainActivity context, Cursor c) {
			super(context, c);
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public void bindView(View v, Context context, Cursor cursor) {

			ViewHolder holder = (ViewHolder) v.getTag();

			int realId = cursor.getInt(cursor.getColumnIndex("_id"));
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
			String desc = cursor.getString(cursor.getColumnIndex("descricao"));

			int assinada = cursor.getInt(cursor.getColumnIndex("is_concluded"));

			ProvaHelper ph = new ProvaHelper(realId,assinada);

			holder.tvTitle.setText(id+" - "+ Html.fromHtml(titulo));
			holder.tvDesc.setText(Html.fromHtml(desc));

			if(assinada == Desafio.ASSINADO){
				holder.check.setChecked(true);
				holder.rlBot.setBackgroundResource(R.drawable.round_bot_concl);
			} else {
				holder.check.setChecked(false);
				holder.rlBot.setBackgroundResource(R.drawable.round_bot);
			}

			switch (divisao) {
				case Divisao.ALCATEIA:
					holder.rlTop.setBackgroundResource(R.drawable.round_top_alc);
					holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));
					break;
				case Divisao.TES:
					holder.rlTop.setBackgroundResource(R.drawable.round_top_tes);
					break;
				case Divisao.TEX:
					holder.rlTop.setBackgroundResource(R.drawable.round_top_tex);
					break;
				case Divisao.CLA:
					holder.rlTop.setBackgroundResource(R.drawable.round_top_cla);
					break;
			}

			if(desc.equals("")){
				holder.tvDesc.setVisibility(View.GONE);
			} else {
				holder.tvDesc.setVisibility(View.VISIBLE);
			}

			holder.check.setTag(ph);
			holder.check.setOnCheckedChangeListener(this);
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup parent) {
			View v = mInflater.inflate(R.layout.row_prova, parent, false);
			ViewHolder holder = new ViewHolder();

			holder.strip = v.findViewById(R.id.div_color);
			holder.tvTitle = (TextView)v.findViewById(R.id.tvTitle);
			holder.tvDesc = (TextView)v.findViewById(R.id.tvDesc);

			holder.rlTop = (RelativeLayout)v.findViewById(R.id.rlTop);
			holder.rlBot = (RelativeLayout)v.findViewById(R.id.rlBot);

			holder.check = (CheckBox)v.findViewById(R.id.checkbox);

			v.setTag(holder);
			return v;
		}

		@Override
		public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
			final ProvaHelper ph = (ProvaHelper)compoundButton.getTag();

			if(b)
				ph.assinar();
			else
				ph.desassinar();

			swapCursor(CursorCache.getInstance(act.app).getProvas(divisao,especialidade_id));
			notifyDataSetChanged();
		}

		class ViewHolder {
			TextView tvTitle;
			TextView tvDesc;
			RelativeLayout rlTop;
			RelativeLayout rlBot;
			CheckBox check;
			View strip;
		}

		class ProvaHelper {
			int id;
			int assinada;

			public ProvaHelper(int id,int assinada){
				this.id = id;
				this.assinada = assinada;
			}

			public void assinar(){
				ContentValues cv = new ContentValues();
				cv.put("is_concluded",1);
				act.app.getDB().getReadableDatabase().update("provas",cv,"_id="+id,null);
			}

			public void desassinar(){
				ContentValues cv = new ContentValues();
				cv.put("is_concluded",0);
				act.app.getDB().getReadableDatabase().update("provas",cv,"_id="+id,null);
			}
		}
	}
	

}
