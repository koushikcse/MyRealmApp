package com.example.koushik.myrealmapp.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.ActivityDetailsBinding;
import com.example.koushik.myrealmapp.dependencyinjection.PresenterComponent;
import com.example.koushik.myrealmapp.model.Amount;
import com.example.koushik.myrealmapp.model.User;
import com.example.koushik.myrealmapp.presenter.DetailsPresenter;

import java.util.List;

/**
 * Created by koushik on 12/7/17.
 */

public class DetailsActivity extends PresentedActivity<DetailsPresenter> implements DetailsPresenter.IDetailsActivity {

    private DetailsPresenter presenter;
    private ActivityDetailsBinding binding;
    private User user;
    private double totalDue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        user = (User) getIntent().getSerializableExtra("SelectedUser");
        if (user != null) {
            presenter.getUserById(user.getUserId());
        }
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                finish();
            }
        });
        binding.addDueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeError();
                presenter.addNewDue(binding.descriptionTxt.getText().toString().trim(),binding.rateTxt.getText().toString().trim(), binding.priceTxt.getText().toString().trim());
            }
        });
        binding.addPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeError();
                presenter.addNewpayment(binding.paymentDescriptionTxt.getText().toString().trim(),binding.paidAmountTxt.getText().toString().trim());
            }
        });
        binding.checkTotalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rate = binding.rateTxt.getText().toString().trim();
                String price = binding.priceTxt.getText().toString().trim();
                if (rate != null && price != null && !rate.equals("") && !price.equals("")) {
                    double ratevalue = Double.parseDouble(rate);
                    double amountvalue = Double.parseDouble(price);
                    double total = ratevalue * amountvalue;
                    binding.dueTxt.setText("Rs. " + String.format("%.2f", total));
                }
            }
        });
        binding.dueHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, DueDetailsActivity.class);
                intent.putExtra("userId", user.getUserId());
                intent.putExtra("type", "due");
                startActivity(intent);
            }
        });
        binding.paymentHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, DueDetailsActivity.class);
                intent.putExtra("userId", user.getUserId());
                intent.putExtra("type", "payment");
                startActivity(intent);
            }
        });
    }

    @Override
    protected DetailsPresenter onCreatePresenter() {
        presenter = new DetailsPresenter(this);
        return presenter;
    }

    @Override
    protected void injectPresenter(PresenterComponent component, DetailsPresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void setUser(User user) {
        binding.usernameTxt.setText(user.getFirstName() + " " + user.getLastName());
        binding.mobileTxt.setText(user.getMobile());
        binding.addressTxt.setText(user.getAddress());
        binding.totalDueTxt.setText("Rs. " + String.format("%.2f", user.getTotalDue()));
        totalDue = user.getTotalDue();
    }

    public void removeError(){
        binding.rateLayout.setErrorEnabled(false);
        binding.priceLayout.setErrorEnabled(false);
        binding.paidAmountLayout.setErrorEnabled(false);
        binding.descriptionLayout.setErrorEnabled(false);
        binding.paymentDescriptionLayout.setErrorEnabled(false);

    }

    @Override
    public void userNotFound() {
        Toast.makeText(this, "User Not Found", Toast.LENGTH_LONG).show();
    }

    @Override
    public void rateEmpty() {
        binding.rateLayout.setErrorEnabled(true);
        binding.rateLayout.setError("please enter rate");
    }

    @Override
    public void descriptionEmpty() {
        binding.descriptionLayout.setErrorEnabled(true);
        binding.descriptionLayout.setError("please enter description");
    }

    @Override
    public void paymentdescriptionEmpty() {
        binding.paymentDescriptionLayout.setErrorEnabled(true);
        binding.paymentDescriptionLayout.setError("please enter description");
    }

    @Override
    public void amountEmpty() {
        binding.priceLayout.setErrorEnabled(true);
        binding.priceLayout.setError("please enter amount");
    }

    @Override
    public void priceEmpty() {
        binding.paidAmountLayout.setErrorEnabled(true);
        binding.paidAmountLayout.setError("please enter paid amount");
    }

    @Override
    public void newPaymentAdded(double price) {
        binding.paymentDescriptionTxt.setText("");
        binding.paidAmountTxt.setText("");
        Toast.makeText(this, "New payment added", Toast.LENGTH_LONG).show();
        totalDue = totalDue - price;
        User edituser = new User();
        edituser.setUserId(user.getUserId());
        edituser.setFirstName(user.getFirstName());
        edituser.setLastName(user.getLastName());
        edituser.setMobile(user.getMobile());
        edituser.setAddress(user.getAddress());
        edituser.setTotalAmount(user.getTotalAmount());
        edituser.setTotalDue(totalDue);
        presenter.updateUserData(edituser);
        binding.totalDueTxt.setText("Rs. " + String.format("%.2f", totalDue));
    }

    @Override
    public void newDueAdded(double price) {
        binding.rateTxt.setText("");
        binding.priceTxt.setText("");
        binding.descriptionTxt.setText("");
        binding.dueTxt.setText("Rs. 0.00");
        Toast.makeText(this, "New due added", Toast.LENGTH_LONG).show();
        totalDue = totalDue + price;
        User edituser = new User();
        edituser.setUserId(user.getUserId());
        edituser.setFirstName(user.getFirstName());
        edituser.setLastName(user.getLastName());
        edituser.setMobile(user.getMobile());
        edituser.setAddress(user.getAddress());
        edituser.setTotalAmount(user.getTotalAmount());
        edituser.setTotalDue(totalDue);

        presenter.updateUserData(edituser);
        binding.totalDueTxt.setText("Rs. " + String.format("%.2f", totalDue));
    }
}
