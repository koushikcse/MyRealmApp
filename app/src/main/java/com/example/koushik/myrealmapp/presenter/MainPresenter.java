package com.example.koushik.myrealmapp.presenter;

import com.example.koushik.myrealmapp.model.Amount;
import com.example.koushik.myrealmapp.model.User;
import com.example.koushik.myrealmapp.realm.RealmManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by koushik on 12/6/17.
 */

public class MainPresenter extends PresenterStub {
    private IMainActivity iMainActivity;

    @Inject
    RealmManager realmManager;

    public MainPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
    }

    public void getuserList() {
        List<User> userList = realmManager.getUserList();
        if (userList != null) {
            iMainActivity.setUserList(userList);
        } else {
            iMainActivity.noUserAdded();
        }
    }

    public void clearDataBase() {
        realmManager.clearRealmData();
        iMainActivity.clearedAllData();
    }

    public double getAlldue() {
        double totaldue = 0.0;
        List<Amount> duelist = realmManager.getAllDueData();
        if (duelist != null && duelist.size() != 0) {
            int len = duelist.size();
            for (int i = 0; i < len; i++) {
                totaldue = totaldue + duelist.get(i).getPrice();
            }
        }
        return totaldue;
    }
    public double getAllpayment() {
        double totaldue = 0.0;
        List<Amount> duelist = realmManager.getAllpaymentDue();
        if (duelist != null && duelist.size() != 0) {
            int len = duelist.size();
            for (int i = 0; i < len; i++) {
                totaldue = totaldue + duelist.get(i).getPrice();
            }
        }
        return totaldue;
    }

    public interface IMainActivity {
        void setUserList(List<User> userList);

        void noUserAdded();

        void clearedAllData();
    }
}
