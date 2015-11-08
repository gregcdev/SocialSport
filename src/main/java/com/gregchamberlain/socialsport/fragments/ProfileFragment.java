package com.gregchamberlain.socialsport.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gregchamberlain.socialsport.R;
import com.gregchamberlain.socialsport.objects.User;
import com.squareup.picasso.Picasso;

/**
 * Created by greg on 11/6/15.
 */
public class ProfileFragment extends Fragment{

    private ImageView profileImg, coverImg;
    private TextView userName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImg = (ImageView) view.findViewById(R.id.profile_image);
        coverImg = (ImageView) view.findViewById(R.id.cover_image);
        coverImg.setColorFilter(getResources().getColor(R.color.colorAccentTrans));
        userName = (TextView) view.findViewById(R.id.profile_name);
        final int imgSize = getContext().getResources().getDimensionPixelSize(R.dimen.welcome_image_size);
        Picasso.with(getContext())
                .load(User.getCurrentUser().getImageUrl())
                .resize(imgSize, imgSize)
                .centerCrop()
                .into(profileImg);
        Picasso.with(getContext())
                .load(User.getCurrentUser().getCoverUrl())
                .resize(getActivity().getWindowManager().getDefaultDisplay().getWidth(), getContext().getResources().getDimensionPixelOffset(R.dimen.profile_cover_height))
                .centerCrop()
                .into(coverImg);
        userName.setText(User.getCurrentUser().getFullName());
        return view;
    }

    public static ProfileFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
