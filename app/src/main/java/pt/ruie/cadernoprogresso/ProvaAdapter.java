package pt.ruie.cadernoprogresso;

import pt.ruie.cadernoprogresso.models.Divisao;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProvaAdapter extends CursorAdapter {

	LayoutInflater mInflater;
	int divisao;
	
	public ProvaAdapter(Context context, Cursor c,int divisao) {
		super(context, c);
		this.mInflater = LayoutInflater.from(context);
		this.divisao = divisao;
	}
	
	@Override
	public void bindView(View v, Context context, Cursor cursor) {
		
		ViewHolder holder = (ViewHolder) v.getTag();
		
		int id = cursor.getInt(cursor.getColumnIndex("id"));
		String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
		String desc = cursor.getString(cursor.getColumnIndex("descricao"));
		
//		titulo = titulo.replaceAll("<br>", "\n");
		
		holder.tvTitle.setText(id+" - "+Html.fromHtml(titulo));
		holder.tvDesc.setText(Html.fromHtml(desc));
		
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
			holder.rlBot.setVisibility(View.GONE);
		} else {
			holder.rlBot.setVisibility(View.VISIBLE);
		}
		
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
		
		v.setTag(holder);
		return v;
	}
	
	class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
		RelativeLayout rlTop;
		RelativeLayout rlBot;
		View strip;
	}
	
}
