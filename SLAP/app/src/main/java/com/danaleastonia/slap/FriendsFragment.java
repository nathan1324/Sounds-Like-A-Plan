package com.danaleastonia.slap;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.danaleastonia.slap.adapter.FriendListCursorAdapter;


public class FriendsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FriendsFragment() {
        // Required empty public constructor
    }


    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        ListView friendListView = (ListView) view.findViewById(R.id.friends_list_view);
        final DBHandler dbHandler = new DBHandler(getContext());
        Cursor friendsCursor = dbHandler.getFriendCursor();
        final FriendListCursorAdapter friendListCursorAdapter = new FriendListCursorAdapter(getContext(), friendsCursor, 0);
        friendListView.setAdapter(friendListCursorAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.friendSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Cursor newFriendsCursor = dbHandler.getFriendCursor();
                        friendListCursorAdapter.swapCursor(newFriendsCursor);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 1000);
                    }
                }

        );


        return view;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
