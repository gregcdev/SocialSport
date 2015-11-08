package com.gregchamberlain.socialsport.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gregchamberlain.socialsport.R;
import com.gregchamberlain.socialsport.adapters.InterestsAdapter;
import com.gregchamberlain.socialsport.objects.Activity;
import com.gregchamberlain.socialsport.objects.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 11/6/15.
 */
public class InterestsFragment extends Fragment {

    private ProgressBar progressBar;

    private ArrayList<Activity> activities;
    private ArrayList<Activity> interests;

    private boolean interestsLoaded = false, activitiesLoaded = false;

    private InterestsAdapter interestsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflates the view from xml
        View view = inflater.inflate(R.layout.activities_fragment, container, false);

        //Initializes recyclerView from xml, sets layoutManager, and adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.activites_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(interestsAdapter);
        recyclerView.setHasFixedSize(true);

        //Initializes progressbar from xml layout
        progressBar = (ProgressBar) view.findViewById(R.id.activities_progress);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializes ArrayLists and adapter
        interests = new ArrayList<>();
        activities = new ArrayList<>();
        interestsAdapter = new InterestsAdapter(getContext());

        //Queries for all available activities
        Activity.getAll(new FindCallback<Activity>() {
            @Override
            public void done(List<Activity> objects, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                //Sets activities ArrayList to retrieved data
                activities = (ArrayList<Activity>) objects;
                activitiesLoaded = true;
                checkDataSets();
            }
        });

        User.getCurrentUser().getInterests(new FindCallback<Activity>() {
            @Override
            public void done(List<Activity> objects, ParseException e) {
                if (e != null) {
                    Log.d("asdasd", e.getMessage());
                    e.printStackTrace();
                    return;
                }
                //Sets interests ArrayList to retrieved data;
                interests = (ArrayList<Activity>) objects;
                interestsLoaded = true;
                checkDataSets();
            }
        });

    }

    /* Checks that interests and activities have both been loaded
     *before setting adapter data and hiding loading progressbar
     */
    private void checkDataSets() {
        if (interestsLoaded && activitiesLoaded) {
            interestsAdapter.setData(activities, interests);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        ParseUser.getCurrentUser().saveInBackground();
        super.onStop();
    }
}
