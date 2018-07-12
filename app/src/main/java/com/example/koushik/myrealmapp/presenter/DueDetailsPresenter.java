package com.example.koushik.myrealmapp.presenter;

import com.example.koushik.myrealmapp.model.Amount;
import com.example.koushik.myrealmapp.realm.RealmManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by koushik on 13/7/17.
 */

public class DueDetailsPresenter extends PresenterStub {
    private IDueDetailsACtivity aCtivity;

    @Inject
    RealmManager realmManager;


    public DueDetailsPresenter(IDueDetailsACtivity aCtivity) {
        this.aCtivity = aCtivity;
    }

    public void getDueHistory(String userId){
        if(userId!=null){
            aCtivity.setDueList(realmManager.getDueData(userId,1));
        }
    }
    public void getPaymentHistory(String userId){
        if(userId!=null){
             aCtivity.setPaymentList(realmManager.getDueData(userId,2));
        }
    }

    public interface IDueDetailsACtivity{
        void setDueList(List<Amount> amountList);
        void setPaymentList(List<Amount> amountList);
    }
}
