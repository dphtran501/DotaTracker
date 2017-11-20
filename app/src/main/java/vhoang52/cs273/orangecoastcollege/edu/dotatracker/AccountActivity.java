package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import java.util.List;

public class AccountActivity extends AppCompatActivity {

    private CircleImageView profilePicture;
    private TextView userName;
    private RingProgressBar winRingProgressBar;
    private RingProgressBar lossRingProgressBar;
    private ListView mostPlayedHeroesListView;
    private MostPlayedHeroesListAdapter listAdapter;
    private List<Hero> mMostPlayedHeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profilePicture = findViewById(R.id.profilePicture);
        userName = findViewById(R.id.userName);
        winRingProgressBar = findViewById(R.id.winRingProgressBar);
        lossRingProgressBar = findViewById(R.id.lossRingProgressBar);
        mostPlayedHeroesListView = findViewById(R.id.mostPlayedHeroesListView);
        listAdapter = new MostPlayedHeroesListAdapter(this, R.layout.hero_list_item, mMostPlayedHeros);
        mostPlayedHeroesListView.setAdapter(listAdapter);
    }
}
