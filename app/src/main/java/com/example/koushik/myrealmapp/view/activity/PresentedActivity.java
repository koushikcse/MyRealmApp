package com.example.koushik.myrealmapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.koushik.myrealmapp.dependencyinjection.ApplicationModule;
import com.example.koushik.myrealmapp.dependencyinjection.DaggerPresenterComponent;
import com.example.koushik.myrealmapp.dependencyinjection.PresenterComponent;
import com.example.koushik.myrealmapp.presenter.Presenter;

/**
 * Created by innofied on 6/4/17.
 */

public abstract class PresentedActivity<T extends Presenter> extends DaggerActivity {

    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        presenter.onCreate();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void inject() {
        presenter = onCreatePresenter();

        PresenterComponent presenterComponent = DaggerPresenterComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        injectPresenter(presenterComponent, presenter);
    }

    protected abstract T onCreatePresenter();

    protected abstract void injectPresenter(PresenterComponent component, T presenter);

}
