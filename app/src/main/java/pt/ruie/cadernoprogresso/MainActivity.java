package pt.ruie.cadernoprogresso;

import pt.ruie.cadernoprogresso.fragments.DivisaoFragment;
import pt.ruie.cadernoprogresso.fragments.EspecialidadeFragment;
import pt.ruie.cadernoprogresso.fragments.HomeFragment;
import pt.ruie.cadernoprogresso.fragments.NavigationDrawerFragment;
import pt.ruie.cadernoprogresso.models.Divisao;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	public static final int HOME = 1;
	public static final int ALCATEIA = 2;
	public static final int TES = 3;
	public static final int TEX = 4;
	public static final int CLA = 5;

	Fragment mCurrentFragment = null;

	public App app;

	public Toolbar mToolbar;
	boolean isBackShown = false;
	
	public String currentTag = "home";

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		app = (App)getApplication();
		mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mToolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		// Set up the drawer.

		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
	}

	public void setActionBarTitle(String title){
		mToolbar.setTitle(title);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {

		if(mCurrentFragment instanceof EspecialidadeFragment){
			onBackPressed();
		}
		swapFragment(position);
	}
	
	public void swapFragment(int position){
		FragmentManager fragmentManager = getSupportFragmentManager();
		mCurrentFragment = null;
		
		Bundle b = new Bundle();
		
		String tag = "";
		
		switch(position){
		case HOME:
			mCurrentFragment = new HomeFragment();
			tag = "home";
			break;
		case ALCATEIA:
			mCurrentFragment = new DivisaoFragment();
			b.putInt("divisao", Divisao.ALCATEIA);
			tag = "alc";
			break;
		case TES:
			mCurrentFragment = new DivisaoFragment();
			b.putInt("divisao", Divisao.TES);
			tag = "tes";
			break;
		case TEX:
			mCurrentFragment = new DivisaoFragment();
			b.putInt("divisao", Divisao.TEX);
			tag = "tex";
			break;
		case CLA:
			mCurrentFragment = new DivisaoFragment();
			b.putInt("divisao", Divisao.CLA);
			tag = "cla";
			break;
		}

		mCurrentFragment.setArguments(b);
		

		fragmentManager.beginTransaction()
			.replace(R.id.container, mCurrentFragment,tag)
			.commit();
		
		currentTag = tag;
	}
	
	public void swapEspFragment(int id){
		FragmentManager fragmentManager = getSupportFragmentManager();
		mCurrentFragment = new EspecialidadeFragment();
		
		Bundle b = new Bundle();
		b.putInt("id", id);

		mCurrentFragment.setArguments(b);

		String tag = "especialidade";
		
		fragmentManager.beginTransaction()
			.replace(R.id.container, mCurrentFragment, tag)
				.addToBackStack(null)
				.commit();
	}
	
	@Override
	public void onBackPressed() {
		
		if(currentTag.equals("home")){
			this.finish();
			return;
		}
		
		super.onBackPressed();
	}


	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		if (!mNavigationDrawerFragment.isDrawerOpen()) {
//			// Only show items in the action bar relevant to this screen
//			// if the drawer is not showing. Otherwise, let the drawer
//			// decide what to show in the action bar.
//			getMenuInflater().inflate(R.menu.main, menu);
//			restoreActionBar();
//			return true;
//		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Log.d("Main Act", "On Options selected");
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void lockNavigationDrawer() {
		if(mNavigationDrawerFragment != null)
			mNavigationDrawerFragment.lockDrawer();
	}

	public void unlockNavigationDrawer() {
		mNavigationDrawerFragment.unlockDrawer();
	}


}
