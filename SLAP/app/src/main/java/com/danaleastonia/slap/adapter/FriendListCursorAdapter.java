package com.danaleastonia.slap.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danaleastonia.slap.DBContract;
import com.danaleastonia.slap.R;


public class FriendListCursorAdapter extends CursorAdapter {
    public FriendListCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.friend_list_element, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvFriendName = (TextView) view.findViewById(R.id.friendUserNameTV);
        String name = cursor.getString(cursor.getColumnIndex(DBContract.FriendEntry.COLUMN_NAME));
        tvFriendName.setText(name);
    }

}
