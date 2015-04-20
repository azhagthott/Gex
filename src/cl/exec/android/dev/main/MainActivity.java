package cl.exec.android.dev.main;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

import cl.exec.android.dev.fragment.FragmentMain;
import cl.exec.android.dev.fragment.FragmentStatistic;
import cl.exec.android.dev.gex.R;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;

import cl.exec.android.dev.classes.Users;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,
		ConnectionCallbacks, OnConnectionFailedListener {

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
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPlusClient = new PlusClient.Builder(this, this, this).setActions(
				"http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity").build();

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments

		Fragment newFragment;
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		switch (position) {
		case 0:
			newFragment = new FragmentMain();
			transaction.replace(R.id.container, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			break;

		case 1:
			newFragment = new FragmentStatistic();
			transaction.replace(R.id.container, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			break;

		case 2:
			break;

		default:
			break;
		}

	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section_main);
			break;
		case 2:
			mTitle = getString(R.string.title_section_statistic);
			break;
		case 3:
			mTitle = getString(R.string.title_section_others);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.action_settings:
			break;

		case R.id.action_disconnect:
			signOutFromGplus();
			finish();
			break;

		case R.id.action_quit:
			finish();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void signOutFromGplus() {
		if (mPlusClient.isConnected()) {
			mPlusClient.disconnect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	private void getProfileInformation() {
		try {

			if (mPlusClient.isConnected()) {

				Users user = new Users();
				
				user.setName(mPlusClient.getAccountName());
				
			
			}

		} catch (Exception e) {
			// TODO: handle exception

		}
	}
}
