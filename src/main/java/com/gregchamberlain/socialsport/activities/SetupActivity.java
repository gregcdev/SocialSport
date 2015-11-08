package com.gregchamberlain.socialsport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gregchamberlain.socialsport.R;
import com.gregchamberlain.socialsport.fragments.InterestsFragment;
import com.gregchamberlain.socialsport.fragments.WelcomeFragment;
import com.gregchamberlain.socialsport.objects.User;
import com.parse.ParseUser;

/**
 * Created by greg on 10/31/15.
 */
public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button nextBtn, backBtn;
    private InterestsFragment interestsFragment;

    private FragmentManager manager;

    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);


        interestsFragment = new InterestsFragment();
        manager = getSupportFragmentManager();
        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (manager.getBackStackEntryCount() == 0) {
                    nextBtn.setText("Next");
                    progress = 0;
                    backBtn.setVisibility(View.GONE);
                } else {
                    nextBtn.setText("Finish");
                    progress = 1;
                    backBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        manager.beginTransaction()
        .add(R.id.fragment_container, WelcomeFragment.newInstance(), "welcomeFragment")
        .commit();

        nextBtn = (Button) findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this);

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ParseUser.getCurrentUser().saveInBackground();
    }

    public void nextScreen(View view) {
        ParseUser.getCurrentUser().saveInBackground();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                if (progress == 0) {
                    manager.beginTransaction()
                            .addToBackStack("welcomeFragment")
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .replace(R.id.fragment_container, interestsFragment)
                            .commit();
                } else {
                    User.getCurrentUser().finishSetup();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
            case R.id.back_btn:
                manager.popBackStack();
                break;
        }

    }
}
