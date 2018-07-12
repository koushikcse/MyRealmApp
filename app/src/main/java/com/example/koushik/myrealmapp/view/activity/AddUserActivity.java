package com.example.koushik.myrealmapp.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.ActivityAddUserBinding;
import com.example.koushik.myrealmapp.dependencyinjection.PresenterComponent;
import com.example.koushik.myrealmapp.presenter.AddUserPresenter;

/**
 * Created by koushik on 12/7/17.
 */

public class AddUserActivity extends PresentedActivity<AddUserPresenter> implements AddUserPresenter.IAddUserActivity {

    private AddUserPresenter presenter;
    private ActivityAddUserBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_user);

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            presenter.addUser(binding.firstnameTxt.getText().toString().trim(),
                    binding.lastnameTxt.getText().toString().trim(),
                    binding.addressTxt.getText().toString().trim(),
                    binding.mobileTxt.getText().toString().trim()
                    );
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddUserActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected AddUserPresenter onCreatePresenter() {
        presenter = new AddUserPresenter(this);
        return presenter;
    }

    @Override
    protected void injectPresenter(PresenterComponent component, AddUserPresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void firstNameEmpty() {
        binding.firstnameLayout.setError("Please enter first name");
    }

    @Override
    public void lastNameEmpty() {
        binding.lastnameLayout.setError("Please enter last name");
    }

    @Override
    public void addressEmpty() {
        binding.addressLayout.setError("Please enter address");
    }

    @Override
    public void mobileEmpty() {
        binding.mobileLayout.setError("Please enter mobile number");
    }

    @Override
    public void mobileInvalid() {
        binding.mobileLayout.setError("Please enter valid mobile number");
    }

//    @Override
//    public void totalAmountEmpty() {
//        binding.farmLayout.setError("Please enter farm amount");
//    }
//
//    @Override
//    public void dueEmpty() {
//        binding.dueLayout.setError("Please enter due amount");
//    }

    @Override
    public void userAdded() {
        Toast.makeText(this,"New User Added",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
