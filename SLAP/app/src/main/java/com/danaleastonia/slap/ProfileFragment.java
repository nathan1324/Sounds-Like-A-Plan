package com.danaleastonia.slap;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private static final String MyPreferences = "MyPrefs";
    private static final String LOGGEDIN_USER_EMAIL = "LOGGEDIN_USER_EMAIL";

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView profileIV = (ImageView) view.findViewById(R.id.profileImage);
        TextView emailTV = (TextView) view.findViewById(R.id.profileEmail);
        TextView nameTV = (TextView) view.findViewById(R.id.profileName);

        sharedPreferences = getActivity().getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString(LOGGEDIN_USER_EMAIL, null);
        DBHandler dbHandler = new DBHandler(getContext());

        User loggedUser = dbHandler.getUserByEmail(loggedInEmail);

        if (loggedUser!=null)
        {
            emailTV.setText(loggedUser.getEmail());
            nameTV.setText(loggedUser.getName());
            SlapUtil.assignRandomImage(profileIV, loggedUser.getId());
        }

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
