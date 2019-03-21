package omari.hamza.mobiletracker.views;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String lang = "ar";
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        config.setLayoutDirection(new Locale(lang));
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
