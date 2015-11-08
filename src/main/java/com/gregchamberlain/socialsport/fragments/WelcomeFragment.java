package com.gregchamberlain.socialsport.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.gregchamberlain.socialsport.R;
import com.gregchamberlain.socialsport.objects.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by greg on 11/6/15.
 */
public class WelcomeFragment extends Fragment {

    private ImageView userImage, coverImage;
    private TextView welcomeText;

    private boolean loaded = false, viewLoaded =false;

    private GraphRequest request;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        userImage = (ImageView) view.findViewById(R.id.welcome_image);
        coverImage = (ImageView) view.findViewById(R.id.welcome_cover);
        coverImage.setColorFilter(getResources().getColor(R.color.colorAccentTrans));
        welcomeText = (TextView) view.findViewById(R.id.welcome_text);
        final int imgSize = getContext().getResources().getDimensionPixelSize(R.dimen.welcome_image_size);
        Picasso.with(getContext())
                .load(User.getCurrentUser().getImageUrl())
                .resize(imgSize, imgSize)
                .centerCrop()
                .into(userImage);
        Picasso.with(getContext())
                .load(User.getCurrentUser().getCoverUrl())
                .resize(getActivity().getWindowManager().getDefaultDisplay().getWidth(), imgSize * 2)
                .centerCrop()
                .into(coverImage);
        welcomeText.setText("Welcome " + User.getCurrentUser().getFirstName() + "!");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static WelcomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
