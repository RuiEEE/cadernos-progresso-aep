package pt.ruie.cadernoprogresso.fragments;

import pt.ruie.cadernoprogresso.App;
import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.R;
import pt.ruie.cadernoprogresso.models.Desafio;
import pt.ruie.cadernoprogresso.models.Divisao;
import pt.ruie.cadernoprogresso.models.Prova;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CadernoFragment extends Fragment {
	
	ListView lv;
	MainActivity act;
	App app;
	
	View fragment_view;

    ChallengeAdapter mProvaAdapter;
	
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
		mProvaAdapter = new ChallengeAdapter(act, Prova.getProvas(app, divisao, etapa));
		lv.setAdapter(mProvaAdapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateListView();
	}

	class ChallengeAdapter extends CursorAdapter implements CompoundButton.OnCheckedChangeListener {

        LayoutInflater mInflater;

        public ChallengeAdapter(MainActivity context, Cursor c) {
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

            holder.tvTitle.setText(id+" - "+Html.fromHtml(titulo));
            holder.tvDesc.setText(Html.fromHtml(desc));

            holder.check.setOnCheckedChangeListener(null);
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

            changeCursor(Prova.getProvas(app, divisao, etapa));
            notifyDataSetChanged();
        }

        class ViewHolder {
            TextView tvTitle;
            TextView tvDesc;
            CheckBox check;
            RelativeLayout rlTop;
            RelativeLayout rlBot;
            View strip;
        }

        class ProvaHelper {
            int id;
            int assinada;

            public ProvaHelper(int id,int assinada){
                this.id = id;
                this.assinada = assinada;
            }

            public boolean isAssinada(){
                return assinada == Desafio.ASSINADO;
            }

            public void assinar(){
                ContentValues cv = new ContentValues();
                cv.put("is_concluded",1);
                act.app.getDB().getWritableDatabase().update("provas", cv, "_id=" + id, null);
            }

            public void desassinar(){
                ContentValues cv = new ContentValues();
                cv.put("is_concluded",0);
                act.app.getDB().getWritableDatabase().update("provas", cv, "_id=" + id, null);
            }
        }
    }
}
