package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * This fragment displays the single-match statistics of each player in Team Radiant, where the
 * match is specified in <code>MatchDetailsActivity</code>.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 28, 2017
 */
public class RadiantMatchDetailsFragment extends Fragment
{
    // TODO: implement onCreateView once fragment layout is finished
    // TODO: Might just have one fragment layout for both Radiant and Dire since layouts are similar

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        View view = inflater.inflate(R.layout.activityfragment_home, container, false);
        mTestTextView = view.findViewById(R.id.testTextView);

        return view;

    }
    */
}
