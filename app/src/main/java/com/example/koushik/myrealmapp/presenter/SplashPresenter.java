package com.example.koushik.myrealmapp.presenter;

import com.example.koushik.myrealmapp.utility.Validation;

/**
 * Created by koushik on 12/6/17.
 */

public class SplashPresenter extends PresenterStub {
    private ISplashActivity isplashActivity;

    public SplashPresenter(ISplashActivity isplashActivity) {
        this.isplashActivity = isplashActivity;
    }

    public void submitBtnPressed(String username,String password) {
        boolean flag=true;
        if(Validation.isFieldEmpty(username)){
            flag=false;
            isplashActivity.usernameEmpty();
        }
        if(Validation.isFieldEmpty(password)){
            isplashActivity.passwordEmpty();
            flag=false;
        }
        if (flag){
            if(username.equals("Koushik")&&password.equals("12345")){
                isplashActivity.gotoHome();
            }
            else {
                isplashActivity.wrongCredentials();
            }
        }
    }

    public interface ISplashActivity {
        void usernameEmpty();
        void passwordEmpty();
        void gotoHome();
        void wrongCredentials();
    }
}
