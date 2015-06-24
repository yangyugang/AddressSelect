package io.github.hammer.address;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hammer on 6/24/15.
 */
public class App extends Application {

    private static Context sAppContext;

    @Override public void onCreate() {
        super.onCreate();

        sAppContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sAppContext;
    }

}
