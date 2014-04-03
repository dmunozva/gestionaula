package es.isolutio.gestionaula;

import java.io.Serializable;

import com.mobandme.ada.Entity;

import es.isolutio.gestionaula.modelo.Curso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * An activity representing a list of Cursos. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link CursoDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CursoListFragment} and the item details (if present) is a
 * {@link CursoDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link CursoListFragment.Callbacks} interface to listen for item selections.
 */
public class CursoListActivity extends FragmentActivity implements
		CursoListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curso_list);

		if (findViewById(R.id.curso_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((CursoListFragment) getSupportFragmentManager().findFragmentById(
					R.id.curso_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link CursoListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Curso entity) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putSerializable(CursoDetailFragment.ARG_ITEM_ID, entity);
			CursoDetailFragment fragment = new CursoDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.curso_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CursoDetailActivity.class);
			detailIntent.putExtra(CursoDetailFragment.ARG_ITEM_ID, entity);
			startActivity(detailIntent);
		}
	}
}
