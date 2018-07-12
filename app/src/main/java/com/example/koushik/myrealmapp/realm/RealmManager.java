package com.example.koushik.myrealmapp.realm;

import com.example.koushik.myrealmapp.model.Amount;
import com.example.koushik.myrealmapp.model.User;

import java.util.List;

import io.realm.Realm;

/**
 * Created by koushik on 12/6/17.
 */

public class RealmManager {
    private static RealmManager realmManager;
    private Realm realm;

    public static RealmManager getInstance() {
        if (realmManager == null) {
            realmManager = new RealmManager();
        }
        return realmManager;
    }

    public RealmManager() {
        this.realm = getRealm();
    }

    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    public void clearRealmData() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void saveUserData(User user) {
        realm.beginTransaction();
//        realm.delete(User.class);
        realm.insert(user);
        realm.commitTransaction();
    }

    public List<User> getUserList() {
        return realm.copyFromRealm(realm.where(User.class).findAll());
    }

    public User getUserById(String userId) {
        return realm.where(User.class).equalTo("userId", userId).findFirst();
    }

    public void updateUserData(User user) {
        realm.beginTransaction();
        realm.insertOrUpdate(user);
        realm.commitTransaction();
    }


    public void deleteUserById(String userId) {
        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("userId", userId).findFirst();
        user.deleteFromRealm();
        realm.commitTransaction();
    }

    public void SaveDueData(Amount amount) {
        realm.beginTransaction();
        realm.insert(amount);
        realm.commitTransaction();
    }

    public List<Amount> getDueData(String userId, int type) {
        return realm.where(Amount.class).equalTo("userId", userId).equalTo("type", type).findAll();
    }

    public List<Amount> getAllDueData() {
        return realm.where(Amount.class).equalTo("type", 1).findAll();
    }
    public List<Amount> getAllpaymentDue() {
        return realm.where(Amount.class).equalTo("type", 2).findAll();
    }

}
