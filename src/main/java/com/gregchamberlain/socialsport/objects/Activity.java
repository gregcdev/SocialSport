package com.gregchamberlain.socialsport.objects;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by greg on 11/7/15.
 */
@ParseClassName("Activity")
public class Activity extends ParseObject {

    public Activity() {

    }

    public static void getAll(FindCallback<Activity> cb) {
        ParseQuery<Activity> query = ParseQuery.getQuery("Activity");
        query.findInBackground(cb);
    }
}
