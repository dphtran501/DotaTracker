package vhoang52.cs273.orangecoastcollege.edu.dotatracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Fragment {

    EditText mSteamIdEditText;
    Button mRegisterButton;
    HTTPRequestService mService;
    DBHelper mDBHelper;
    Button mRefreshButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        mService = HTTPRequestService.getInstance();
        mDBHelper = DBHelper.getInstance(getActivity());

        mSteamIdEditText = (EditText) view.findViewById(R.id.steamIDEditText);
        mRegisterButton = (Button) view.findViewById(R.id.submitButton);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton();
            }
        });

        mRefreshButton = (Button) view.findViewById(R.id.refreshButton);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButton();
            }
        });

        return view;
    }

    private void submitButton() {

        final long steamId = Long.parseLong(mSteamIdEditText.getText().toString());
        Log.i(TAG, "steamId->" + steamId);

        HTTPRequestService.postUserID(steamId, new HTTPRequestService.UserRegistrationCallback() {
            @Override
            public void onSuccess() {
                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("CurrentUserId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String currentUserId = String.valueOf(steamId);
                editor.putString("CurrentUserId", currentUserId);
                editor.commit();
                Toast.makeText(getActivity(), "User successfully registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getActivity(), "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void refreshButton() {
        HTTPRequestService.getMatchDetails(mService.getmCurrentUserId(), new HTTPRequestService.MatchListCallback() {
            @Override
            public void onSuccess() {
                List<Match> matchList = mService.getmMatchesList();
                Log.i(TAG, "Successfully retrieved match list from server; matchList size->" + matchList.size());


                for (Match m : matchList) {
                    Log.i(TAG, m.toString());
                }

                for (Match m : matchList) {
                    mDBHelper.addMatch(m);

                    for (MatchPlayer mp : m.getMatchPlayerList()) {
                        mDBHelper.addPlayer(mp);
                    }
                }

                List<MatchPlayer> matchPlayerList = mDBHelper.getMatchPlayers(2586393706L);

                for (MatchPlayer m : matchPlayerList) {
                    Log.i(TAG, "matchPlayer in list->" + m.toString());
                }

                Toast.makeText(getActivity(), "Refresh successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
