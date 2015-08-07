package pt.ruie.cadernoprogresso.fragments;

import java.util.ArrayList;

import pt.ruie.cadernoprogresso.App;
import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.PagerSlidingTabStrip;
import pt.ruie.cadernoprogresso.R;
import pt.ruie.cadernoprogresso.models.Divisao;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DivisaoFragment extends Fragment {
	
	int divisao;
	MainActivity act;
	App app;
	View fragment_view;
	
	ViewPager mPager;
	MyPagerAdapter mAdapter;
	
	CadernoFragment etapa1;
	CadernoFragment etapa2;
	CadernoFragment etapa3;
	ListFragment esp;
	ArrayList<Fragment> mFragments;
	
	public DivisaoFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		act = (MainActivity)getActivity();
		app = (App)act.getApplication();
		fragment_view = inflater.inflate(R.layout.fragment_divisao, container, false);
		
		divisao = getArguments().getInt("divisao");
		
		switch(divisao){
		case Divisao.ALCATEIA:
			getActivity().getActionBar().setTitle("Alcateia");
			break;
		case Divisao.TES:
			getActivity().getActionBar().setTitle("Tribo de Escoteiros");
			break;
		case Divisao.TEX:
			getActivity().getActionBar().setTitle("Tribo de Exploradores");
			break;
		case Divisao.CLA:
			getActivity().getActionBar().setTitle("Clã");
			break;
		}
		
		initVariables(fragment_view);
		
		return fragment_view;
	}
	
	public void initVariables(View v){
		mPager = (ViewPager)v.findViewById(R.id.pager);
		mPager.setOffscreenPageLimit(3);
		
		mFragments = new ArrayList<Fragment>();
		
		etapa1 = new CadernoFragment();
		etapa1.setup(divisao, 1);
		
		etapa2 = new CadernoFragment();
		etapa2.setup(divisao, 2);
		
		etapa3 = new CadernoFragment();
		etapa3.setup(divisao, 3);
		
		esp = new ListFragment();
		esp.setup(divisao);
		
		mFragments.add(etapa1);
		mFragments.add(etapa2);
		mFragments.add(etapa3);
		mFragments.add(esp);
		
		mAdapter = new MyPagerAdapter(getChildFragmentManager(),mFragments);
		mPager.setAdapter(mAdapter);
		
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)v.findViewById(R.id.tabs);
		
		tabs.setViewPager(mPager);
		
		tabs.setTextColor(getResources().getColor(R.color.black));
		
		switch(divisao){
		case Divisao.ALCATEIA:
			tabs.setIndicatorColor(getResources().getColor(R.color.alc));
			break;
		case Divisao.TES:
			tabs.setIndicatorColor(getResources().getColor(R.color.tes));
			break;
		case Divisao.TEX:
			tabs.setIndicatorColor(getResources().getColor(R.color.tex));
			break;
		case Divisao.CLA:
			tabs.setIndicatorColor(getResources().getColor(R.color.cla));
			break;
		}
		
		
	}
	
	class MyPagerAdapter extends FragmentPagerAdapter {
		
		ArrayList<Fragment> fragments;

	    public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}
	    
	    @Override
	    public Fragment getItem(int position) {
	    	return fragments.get(position);
	    }

	    @Override
	    public int getCount() {
	        return fragments.size();
	    }
	    
	    @Override
	    public CharSequence getPageTitle(int position) {
	    	switch(position){
	    	case 0:
	    		return "1ª Etapa";
	    	case 1:
	    		return "2ª Etapa";
	    	case 2:
	    		return "3ª Etapa";
	    	case 3:
	    		return "Especialidades";
    		}
	    	return "";
	    }
	   
	}

}
