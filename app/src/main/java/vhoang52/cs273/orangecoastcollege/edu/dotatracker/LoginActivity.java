package vhoang52.cs273.orangecoastcollege.edu.dotatracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends Fragment {
    private static final String TAG = "LoginActivityFragment";

    private EditText mSteamIdEditText;
    private Button mRegisterButton;
    private Button mRefreshButton;
    private ListView mUserListView;

    private HTTPRequestService mService;
    private DBHelper mDBHelper;

    private LoginListAdapter mLoginListAdapter;
    private List<User> mUserList;


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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        mService = HTTPRequestService.getInstance();
        mDBHelper = DBHelper.getInstance(getActivity());

        mRefreshButton = (Button) view.findViewById(R.id.refreshButton);
        mRegisterButton = (Button) view.findViewById(R.id.submitButton);
        mSteamIdEditText = (EditText) view.findViewById(R.id.steamIDEditText);
        mUserListView = (ListView) view.findViewById(R.id.savedUsersListView);
        mUserList = mDBHelper.getAllUsers();
        mLoginListAdapter = new LoginListAdapter(getActivity(), R.layout.login_list_item, mUserList);
        mUserListView.setAdapter(mLoginListAdapter);

        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout selectedItem = (LinearLayout) view;
                User user = (User) selectedItem.getTag(R.id.login_user_tag);
                mService.setmCurrentUserId(user.getSteamId32());
                mService.setmCurrentUser(user);

                modifyUserIdSharedPrefs(user.getSteamId32());
                Toast.makeText(getActivity(), "User profile set to " + user.getPersonaName(), Toast.LENGTH_SHORT).show();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton();
            }
        });


        return view;
    }

    private void submitButton() {

        if (mSteamIdEditText.getText().toString().length() != 0) {
            final long steamId = Long.parseLong(mSteamIdEditText.getText().toString());
            Log.i(TAG, "steamId->" + steamId);

            HTTPRequestService.postUserID(steamId, new HTTPRequestService.UserRegistrationCallback() {
                @Override
                public void onSuccess() {
                    modifyUserIdSharedPrefs(steamId);
                    getUserSummary();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(getActivity(), "Failed to register user", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please enter a Steam ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserSummary() {
        long steamId = mService.getmCurrentUserId();
        HTTPRequestService.getUserSummaries(steamId, new HTTPRequestService.JSONStringCallback() {
            @Override
            public void onSuccess() {
                User currentUser = mService.getmCurrentUser();
                mDBHelper.addUser(currentUser);
                mUserList.add(currentUser);
                mLoginListAdapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "User successfully registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void modifyUserIdSharedPrefs(long steamId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String currentUserId = String.valueOf(steamId);
        editor.putString("CurrentUserId", currentUserId);
        editor.apply();
    }



}
