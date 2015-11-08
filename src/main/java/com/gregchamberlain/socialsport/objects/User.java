package com.gregchamberlain.socialsport.objects;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by greg on 11/6/15.
 */
@ParseClassName("_User")
public class User extends ParseUser {

    public User() {
    }

    public String getFirstName() {
        return getString("first_name");
    }

    public void setFirstName(String firstName) {
        put("first_name", firstName);
    }

    public String getLastName() {
        return getString("last_name");
    }

    public void setLastName(String lastName) {
        put("last_name", lastName);
    }

    public String getFullName() {
        return getString("first_name") + " " + getString("last_name");
    }

    public String getImageUrl() {
        return getString("imageUrl");
    }

    public void setImageUrl(String imgUrl) {
        put("imageUrl", imgUrl);
    }

    public String getCoverUrl() {
        return getString("coverUrl");
    }

    public void setCoverUrl(String url) {
        put("coverUrl", url);
    }

    public static User getCurrentUser() {
        return (User) ParseUser.getCurrentUser();
    }

    public boolean isSetup() {
        return getBoolean("setup");
    }

    public void finishSetup() {
        put("setup", true);
        saveEventually();
    }

    public void addInterests(Activity o) {
        getCurrentUser().getRelation("interests").add(o);
        saveEventually();
    }

    public void getInterests(FindCallback<Activity> cb) {
        ParseRelation<Activity> relation = getRelation("interests");
        relation.getQuery().findInBackground(cb);
    }

}
