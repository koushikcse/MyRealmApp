package com.example.koushik.myrealmapp.presenter;

import com.example.koushik.myrealmapp.model.Amount;
import com.example.koushik.myrealmapp.model.User;
import com.example.koushik.myrealmapp.realm.RealmManager;
import com.example.koushik.myrealmapp.utility.Validation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by koushik on 12/7/17.
 */

public class DetailsPresenter extends PresenterStub {
    private IDetailsActivity activity;
    private User user;

    @Inject
    RealmManager realmManager;

    public DetailsPresenter(IDetailsActivity activity) {
        this.activity = activity;
    }

    public void getUserById(String userId) {
        user = realmManager.getUserById(userId);
        if (user != null) {
            activity.setUser(user);
        } else {
            activity.userNotFound();
        }
    }

    public void addNewDue(String description, String rate, String price) {
        if (Validation.isFieldEmpty(description)) {
            activity.descriptionEmpty();
            return;
        }
        if (Validation.isFieldEmpty(rate)) {
            activity.rateEmpty();
            return;
        }
        if (Validation.isFieldEmpty(price)) {
            activity.amountEmpty();
            return;
        }
        if (user != null) {
            double ratevalue = Double.parseDouble(rate);
            double amountvalue = Double.parseDouble(price);
            double total = ratevalue * amountvalue;
            Amount amount = new Amount();
            Date date = new Date();
            amount.setDate(new SimpleDateFormat("MMM dd,yyyy", Locale.getDefault()).format(date));
            amount.setDescription(description);
            amount.setPrice(total);
            amount.setType(1);
            amount.setUserId(user.getUserId());
            realmManager.SaveDueData(amount);
            activity.newDueAdded(total);
        }

    }

    public void addNewpayment(String description, String price) {
        if (Validation.isFieldEmpty(description)) {
            activity.paymentdescriptionEmpty();
            return;
        }
        if (Validation.isFieldEmpty(price)) {
            activity.priceEmpty();
            return;
        }
        if (user != null) {
            double amountvalue = Double.parseDouble(price);
            Amount amount = new Amount();
            Date date = new Date();
            amount.setDate(new SimpleDateFormat("MMM dd,yyyy", Locale.getDefault()).format(date));
            amount.setPrice(amountvalue);
            amount.setDescription(description);
            amount.setType(2);
            amount.setUserId(user.getUserId());
            realmManager.SaveDueData(amount);
            activity.newPaymentAdded(amountvalue);
        }
    }


    public void updateUserData(User user) {
        realmManager.updateUserData(user);
    }

    public interface IDetailsActivity {
        void setUser(User user);

        void userNotFound();

        void rateEmpty();

        void descriptionEmpty();
        void paymentdescriptionEmpty();

        void amountEmpty();

        void priceEmpty();

        void newPaymentAdded(double price);

        void newDueAdded(double price);
    }
}
