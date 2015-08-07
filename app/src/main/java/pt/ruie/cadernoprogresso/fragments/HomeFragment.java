package pt.ruie.cadernoprogresso.fragments;

import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeFragment extends Fragment implements OnClickListener {
	
	ImageView ivAlc,ivTes,ivTex,ivCla;
	ImageView ivEspAlc,ivEspTes,ivEspTex,ivEspCla;
	MainActivity act;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		act = (MainActivity)getActivity();
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		
		initVariables(v);
		
		getActivity().getActionBar().setTitle("Cadernos de Progresso - AEP");
		
		return v;
	}
	
	public void initVariables(View v){
		v.findViewById(R.id.ivAlc).setOnClickListener(this);
		v.findViewById(R.id.ivTes).setOnClickListener(this);
		v.findViewById(R.id.ivTex).setOnClickListener(this);
		v.findViewById(R.id.ivCla).setOnClickListener(this);	
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ivAlc:
			act.swapFragment(MainActivity.ALCATEIA);
			break;
		case R.id.ivTes:
			act.swapFragment(MainActivity.TES);
			break;
		case R.id.ivTex:
			act.swapFragment(MainActivity.TEX);
			break;
		case R.id.ivCla:
			act.swapFragment(MainActivity.CLA);
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		act.currentTag = "home";
	}

}
