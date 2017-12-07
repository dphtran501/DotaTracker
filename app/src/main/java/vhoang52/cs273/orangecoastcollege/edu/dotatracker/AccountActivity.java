package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends Fragment {
    private static final String TAG = "AccountActivityFragment";
    HTTPRequestService mService;

    private User mUser;

    private CircleImageView profilePicture;
    private TextView userName;
    private RingProgressBar winRingProgressBar;
    private RingProgressBar lossRingProgressBar;
    private ListView mostPlayedHeroesListView;
    private MostPlayedHeroesListAdapter listAdapter;
    private List<Hero> mMMostPlayedHerbs = new ArrayList<>();

    private ViewGroup mContainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TODO: populate with actual heros
        //mMMostPlayedHerbs.add(new Hero("Alchemist", 73));
        View view = inflater.inflate(R.layout.activity_seve_account, container, false);

        mService = HTTPRequestService.getInstance();
        mUser = mService.getmCurrentUser();

        profilePicture = view.findViewById(R.id.profilePicture);
        userName = view.findViewById(R.id.userName);
        winRingProgressBar = view.findViewById(R.id.winRingProgressBar);
        lossRingProgressBar = view.findViewById(R.id.lossRingProgressBar);
        mostPlayedHeroesListView = view.findViewById(R.id.mostPlayedHeroesListView);

        userName.setText(mUser.getPersonaName());

        listAdapter = new MostPlayedHeroesListAdapter(view.getContext(), R.layout.hero_list_item, mMMostPlayedHerbs);
        mostPlayedHeroesListView.setAdapter(listAdapter);

        profilePicture.setTag(R.id.key1, this);
        profilePicture.setTag(R.id.key2, FluffActivity.class);

        return view;
    }
}
