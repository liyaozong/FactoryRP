package tech.yozo.factoryrp;

import android.app.Application;
import android.content.Context;

public class FactoryrpApplication extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        FactoryrpApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return FactoryrpApplication.context;
    }
}
