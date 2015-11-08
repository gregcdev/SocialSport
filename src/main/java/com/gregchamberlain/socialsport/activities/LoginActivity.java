package com.gregchamberlain.socialsport.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.gregchamberlain.socialsport.R;
import com.gregchamberlain.socialsport.objects.User;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    final List<String> permissions = Arrays.asList("public_profile", "email", "user_friends");

    private Button fbLogin;
    private LinearLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        fbLogin = (Button) findViewById(R.id.fblogin_btn);
        loadingLayout = (LinearLayout) findViewById(R.id.loading_layout);

        if (ParseUser.getCurrentUser() != null) {
            fbLogin.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            ParseUser.getCurrentUser().fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    afterLogin();
                }
            });
        }

        fbLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithFacebook();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithFacebook() {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Cancelled Login", Toast.LENGTH_SHORT).show();
                } else if (user.isNew()) {
                    Toast.makeText(getApplicationContext(), "Signup and Login", Toast.LENGTH_SHORT).show();
                    afterLogin();
                } else {
                    Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                    afterLogin();
                }
            }
        });
    }

    private void afterLogin() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String firstName = "", lastName = "", url = "", coverUrl = "";
                        try {
                            url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            coverUrl = object.getJSONObject("cover").getString("source");
                            firstName = object.getString("first_name");
                            lastName = object.getString("last_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        User user = User.getCurrentUser();
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setImageUrl(url);
                        user.setCoverUrl(coverUrl);
                        user.saveEventually();
                        Intent i;
                        if (User.getCurrentUser().isSetup()) {
                            i = new Intent(getApplicationContext(), MainActivity.class);
                        } else {
                            i = new Intent(getApplicationContext(), SetupActivity.class);
                        }
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,picture.type(large),cover");
        request.setParameters(parameters);
        request.executeAsync();
    }
}

