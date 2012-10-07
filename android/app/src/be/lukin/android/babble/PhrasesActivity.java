package be.lukin.android.babble;

import be.lukin.android.babble.provider.Phrase;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class PhrasesActivity extends SubActivity {

	private static final String SORT_ORDER_TEXT = Phrase.Columns.TEXT + " ASC";
	private static final String SORT_ORDER_LANG = Phrase.Columns.LANG + " ASC";
	private static final String SORT_ORDER_DIST = Phrase.Columns.DIST + " DESC";
	private static final String SORT_ORDER_TIMESTAMP = Phrase.Columns.TIMESTAMP + " DESC";

	private SharedPreferences mPrefs;
	private static String mCurrentSortOrder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phrases);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	}


	@Override
	public void onStop() {
		super.onStop();
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(getString(R.string.prefCurrentSortOrder), mCurrentSortOrder);
		editor.commit();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.phrases, menu);

		// Indicate the current sort order by checking the corresponding radio button
		int id = mPrefs.getInt(getString(R.string.prefCurrentSortOrderMenu), R.id.menuMainSortByTimestamp);
		MenuItem menuItem = menu.findItem(id);
		// TODO: check why null can happen
		if (menuItem != null) {
			menuItem.setChecked(true);
		}
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuMainSortByTimestamp:
			sort(item, SORT_ORDER_TIMESTAMP);
			return true;
		case R.id.menuMainSortByText:
			sort(item, SORT_ORDER_TEXT);
			return true;
		case R.id.menuMainSortByLang:
			sort(item, SORT_ORDER_LANG);
			return true;
		case R.id.menuMainSortByDist:
			sort(item, SORT_ORDER_DIST);
			return true;
		case R.id.menuLanguagesPlot:
			LanguagesBarChart lbc = new LanguagesBarChart();
			Intent intent = lbc.execute(this);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void sort(MenuItem item, String sortOrder) {
		mCurrentSortOrder = sortOrder;
		CursorLoaderListFragment fragment = (CursorLoaderListFragment) getFragmentManager().findFragmentById(R.id.list);
		fragment.changeSortOrder(sortOrder);
		item.setChecked(true);
		// Save the ID of the selected item.
		// TODO: ideally this should be done in onDestory
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putInt(getString(R.string.prefCurrentSortOrderMenu), item.getItemId());
		editor.commit();
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cm_main, menu);
	}

}