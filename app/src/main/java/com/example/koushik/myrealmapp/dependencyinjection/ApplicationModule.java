package com.example.koushik.myrealmapp.dependencyinjection;

import android.app.Activity;
import android.content.Context;

import com.example.koushik.myrealmapp.realm.RealmManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by koushik on 12/6/17.
 */
@Module
public class ApplicationModule {
    private Activity activity;

    public ApplicationModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Context getApplicationContext(){return activity.getApplicationContext();}

    @Provides
    RealmManager getRealmManager() {
        return RealmManager.getInstance();
    }
}
