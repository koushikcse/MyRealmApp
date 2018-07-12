package com.example.koushik.myrealmapp.dependencyinjection;

import com.example.koushik.myrealmapp.presenter.AddUserPresenter;
import com.example.koushik.myrealmapp.presenter.DetailsPresenter;
import com.example.koushik.myrealmapp.presenter.DueDetailsPresenter;
import com.example.koushik.myrealmapp.presenter.MainPresenter;
import com.example.koushik.myrealmapp.presenter.SplashPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by koushik on 12/6/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface PresenterComponent {
    void inject(SplashPresenter presenter);
    void inject(MainPresenter presenter);
    void inject(DetailsPresenter presenter);
    void inject(AddUserPresenter presenter);
    void inject(DueDetailsPresenter presenter);

}
