package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapts a listview for the user to quickly switch between saved users
 */

public class LoginListAdapter extends ArrayAdapter<User> {
    private Context mContext;
    private int mResourceId;
    private List<User> mUserList = new ArrayList<>();

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public LoginListAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourceId = resource;
        mUserList = objects;
    }

    /**
     * Viewholder static class used to smooth out scrolling instead of recreating objects when they re-enter view
     */
    static class ViewHolder {
        LinearLayout listItemLinearLayout;
        TextView userNameTextView;
        TextView steamIdTextView;
    }

    /**
     * Returns an adapted view to the user
     *
     * @param position    position of the item in the list
     * @param convertView the view to adapt and return with converted values
     * @param parent      container view
     * @return converted view
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(mResourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.listItemLinearLayout = (LinearLayout) convertView.findViewById(R.id.login_list_item_layout);
            viewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.list_item_username);
            viewHolder.steamIdTextView = (TextView) convertView.findViewById(R.id.list_item_steam_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User selectedUser = mUserList.get(position);

        viewHolder.userNameTextView.setText(selectedUser.getPersonaName());
        viewHolder.steamIdTextView.setText(String.valueOf(selectedUser.getSteamId32()));
        viewHolder.listItemLinearLayout.setTag(R.id.login_user_tag, selectedUser);


        return convertView;
    }
}
