package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CircleImageView profilePicture = findViewById(R.id.profilePicture);
        TextView userName = findViewById(R.id.userName);
        RingProgressBar winRingProgressBar = findViewById(R.id.winRingProgressBar);
        RingProgressBar lossRingProgressBar = findViewById(R.id.lossRingProgressBar);
        ListView mostPlayedHeroesListView = findViewById(R.id.mostPlayedHeroesListView);


    }
}
