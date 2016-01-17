package pt.ruie.cadernoprogresso.fragments;

import pt.ruie.cadernoprogresso.MainActivity;
import pt.ruie.cadernoprogresso.R;
import pt.ruie.cadernoprogresso.models.Divisao;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private NavigationDrawerCallbacks mCallbacks;

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 1;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;
	
	NavigationAdapter mNavigationAdapter;

	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Read in the flag indicating whether or not the user has demonstrated
		// awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;
		}

		// Select either the default item (0) or the last selected item.
		selectItem(mCurrentSelectedPosition);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		
		mDrawerListView.addHeaderView(inflater.inflate(R.layout.header_listview,container, false));
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0){
					//do nothing, clicking on the header
					return;
				}
				selectItem(position);
			}
		});
		
		mNavigationAdapter = new NavigationAdapter(getActivity(),new String[] {
			getString(R.string.title_section1),
			getString(R.string.title_section2),
			getString(R.string.title_section3),
			getString(R.string.title_section4),
			getString(R.string.title_section5)
		});
		
		mDrawerListView.setAdapter(mNavigationAdapter);
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return mDrawerListView;
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 *
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener

		android.support.v7.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,
				((MainActivity)getActivity()).mToolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close) {
					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						if (!isAdded()) {
							return;
						}

						getActivity().invalidateOptionsMenu();
					}

					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
						if (!isAdded()) {
							return;
						}

						if (!mUserLearnedDrawer) {

							mUserLearnedDrawer = true;
							SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
							sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
						}

						getActivity().invalidateOptionsMenu();
					}
				};


		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	public void setUpBurgerMenu(int fragmentId, DrawerLayout drawerLayout, android.support.v7.app.ActionBarDrawerToggle drawerToggle) {
		if (getActivity() != null) {
			mFragmentContainerView = getActivity().findViewById(fragmentId);
			mDrawerLayout = drawerLayout;
		}
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// If the drawer is open, show the global app actions in the action bar.
		// See also
		// showGlobalContextActionBar, which controls the top-left area of the
		// action bar.
//		if (mDrawerLayout != null && isDrawerOpen()) {
//			inflater.inflate(R.menu.global, menu);
//			showGlobalContextActionBar();
//		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("NavigationDrawer","OnOptionsItemSelected");

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		if (item.getItemId() == R.id.action_example) {
			Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	public class NavigationAdapter extends ArrayAdapter<String> {

		LayoutInflater mInflater;
		String[] mTitles;
		
		
		public NavigationAdapter(Context context, String[] objects) {
			super(context, R.layout.row_navigation, objects);
			mTitles = objects;
			mInflater = getActivity().getLayoutInflater();
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder; // to reference the child views for later actions

			if (v == null) {
				v = mInflater.inflate(R.layout.row_navigation, null);
				// cache view fields into the holder

				holder = new ViewHolder();
				holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				holder.strip = v.findViewById(R.id.div_color);
				

				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}

			holder.tvTitle.setText(mTitles[position]);
			
			switch (position) {
			case Divisao.ALCATEIA:
				holder.strip.setBackgroundResource(R.color.alc);
				break;
			case Divisao.TES:
				holder.strip.setBackgroundResource(R.color.tes);
				break;
			case Divisao.TEX:
				holder.strip.setBackgroundResource(R.color.tex);
				break;
			case Divisao.CLA:
				holder.strip.setBackgroundResource(R.color.cla);
				break;

			default:
				break;
			}

			return v;
		}

		private class ViewHolder {
			TextView tvTitle;
			View strip;
		}
		
	}

	private android.support.v7.app.ActionBar getActionBar() {
		return ((AppCompatActivity) getActivity()).getSupportActionBar();
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}

	public void lockDrawer() {
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
	}

	public void unlockDrawer() {
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
	}

	public boolean isDrawerLocked() {
		return DrawerLayout.LOCK_MODE_LOCKED_CLOSED == mDrawerLayout.getDrawerLockMode(GravityCompat.START);
	}
}
