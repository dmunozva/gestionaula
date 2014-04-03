package es.isolutio.gestionaula;

import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.isolutio.gestionaula.modelo.ContextoAplicacion;
import es.isolutio.gestionaula.modelo.Curso;

/**
 * A fragment representing a single Curso detail screen. This fragment is either
 * contained in a {@link CursoListActivity} in two-pane mode (on tablets) or a
 * {@link CursoDetailActivity} on handsets.
 */
public class CursoDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "obj_curso";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Curso mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CursoDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = (Curso)getArguments().getSerializable(ARG_ITEM_ID);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_curso_detail,
				container, false);

//		// Show the dummy content as text in a TextView.
//		if (mItem != null) {
//			((TextView) rootView.findViewById(R.id.curso_detail))
//					.setText(mItem.content);
//		}
		try {
			mItem.bind(rootView);
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rootView;
	}
}
