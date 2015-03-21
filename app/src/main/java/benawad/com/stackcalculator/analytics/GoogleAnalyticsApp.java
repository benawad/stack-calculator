package benawad.com.stackcalculator.analytics;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import io.fabric.sdk.android.Fabric;


public class GoogleAnalyticsApp extends Application {

    private static final String PROPERTY_ID = "UA-56096342-3";
    public static int GENERAL_TRACKER = 0;
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public GoogleAnalyticsApp() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        //set up tracker
        Tracker t = this.getTracker(TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);
        t.setScreenName("Home");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    public synchronized Tracker getTracker(TrackerName appTracker) {
        if (!mTrackers.containsKey(appTracker)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(PROPERTY_ID);//(appTracker == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
            //: (appTracker == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
            //: analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(appTracker, t);
        }
        return mTrackers.get(appTracker);
    }

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }
}
