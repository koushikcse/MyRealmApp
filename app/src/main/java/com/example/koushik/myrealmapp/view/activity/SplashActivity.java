package com.example.koushik.myrealmapp.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.ActivitySplashBinding;
import com.example.koushik.myrealmapp.dependencyinjection.PresenterComponent;
import com.example.koushik.myrealmapp.presenter.SplashPresenter;


public class SplashActivity extends PresentedActivity<SplashPresenter>
        implements SplashPresenter.ISplashActivity {

    private SplashPresenter presenter;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeError();
                presenter.submitBtnPressed(binding.usernaemTxt.getText().toString().trim(), binding.passwordTxt.getText().toString().trim());
            }
        });
    }

    @Override
    protected SplashPresenter onCreatePresenter() {
        presenter = new SplashPresenter(this);
        return presenter;
    }

    @Override
    protected void injectPresenter(PresenterComponent component, SplashPresenter presenter) {
        component.inject(presenter);
    }

    private void removeError(){
        binding.passwordLayout.setErrorEnabled(false);
        binding.usernameLayout.setErrorEnabled(false);
    }

    @Override
    public void usernameEmpty() {
        binding.usernameLayout.setErrorEnabled(true);
        binding.usernameLayout.setError("Please enter username");
    }

    @Override
    public void passwordEmpty() {
        binding.usernameLayout.setErrorEnabled(true);
        binding.passwordLayout.setError("Please enter password");
    }

    @Override
    public void gotoHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void wrongCredentials() {
        Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_LONG).show();
    }
}
