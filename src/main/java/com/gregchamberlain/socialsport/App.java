package com.gregchamberlain.socialsport;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.gregchamberlain.socialsport.objects.Activity;
import com.gregchamberlain.socialsport.objects.User;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by greg on 10/31/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Parse
        Parse.enableLocalDatastore(this);
        ParseUser.registerSubclass(User.class);
        ParseObject.registerSubclass(Activity.class);
        Parse.initialize(this, "Aqc5H7XkSWwU4UEH5NLaHl63tB8s2Okx0vg6RnMf", "DmqHCEB4F1obEODv1f6lplvogf7zS3lDcoFXeqAA");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseFacebookUtils.initialize(this);
        // Initialize FacebookSDK
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
