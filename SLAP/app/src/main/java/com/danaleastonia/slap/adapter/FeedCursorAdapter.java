package com.danaleastonia.slap.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danaleastonia.slap.DBContract;
import com.danaleastonia.slap.R;
import com.danaleastonia.slap.SlapUtil;


public class FeedCursorAdapter extends CursorAdapter {
    public FeedCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.feed_list_element, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvFeedTitle = (TextView) view.findViewById(R.id.feedListItemTV);
        TextView tvFeedName = (TextView) view.findViewById(R.id.feedUserNameTV);
        ImageView imageView = (ImageView) view.findViewById(R.id.feedProfileImage);
        String title = cursor.getString(cursor.getColumnIndex(DBContract.FeedEntry.COLUMN_TITLE));
        String name = cursor.getString(cursor.getColumnIndex(DBContract.FeedEntry.COLUMN_NAME));
        int id = cursor.getInt(cursor.getColumnIndex(DBContract.FeedEntry._ID));
        tvFeedTitle.setText(title);
        tvFeedName.setText(name);
        SlapUtil.assignRandomImage(imageView, id);
    }
}
