package com.example.koushik.myrealmapp.presenter;

import com.example.koushik.myrealmapp.model.User;
import com.example.koushik.myrealmapp.realm.RealmManager;
import com.example.koushik.myrealmapp.utility.Validation;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by koushik on 12/7/17.
 */

public class AddUserPresenter extends PresenterStub {
    private IAddUserActivity activity;

    @Inject
    RealmManager realmManager;

    public AddUserPresenter(IAddUserActivity activity) {
        this.activity = activity;
    }

    public void addUser(String firstname, String lastname, String address, String mobile) {
        boolean flag = true;
        if (Validation.isFieldEmpty(firstname)) {
            flag = false;
            activity.firstNameEmpty();
            return;
        }
        if (Validation.isFieldEmpty(lastname)) {
            activity.lastNameEmpty();
            return;
        }
        if (Validation.isFieldEmpty(address)) {
            activity.addressEmpty();
            return;
        }
        if (Validation.isFieldEmpty(mobile)) {
            activity.mobileEmpty();
            return;
        }
        if (Validation.isMobileValid(mobile)) {
            activity.mobileInvalid();
            return;
        }
//        if (Validation.isFieldEmpty(totalAmount)) {
//            activity.totalAmountEmpty();
//            return;
//        }
//        if (Validation.isFieldEmpty(due)) {
//            activity.dueEmpty();
//            return;
//        }
        if (flag) {
            User user = new User();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setAddress(address);
            user.setMobile(mobile);
            user.setTotalDue(0.0);
//            user.setTotalAmount(Double.parseDouble(totalAmount));
            user.setUserId(firstname, mobile);
            realmManager.saveUserData(user);
            List<User> userList = realmManager.getUserList();
            activity.userAdded();
        }
    }

    public interface IAddUserActivity {
        void firstNameEmpty();

        void lastNameEmpty();

        void addressEmpty();

        void mobileEmpty();

        void mobileInvalid();

//        void totalAmountEmpty();
//
//        void dueEmpty();

        void userAdded();
    }
}

