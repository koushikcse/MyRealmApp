package com.example.koushik.myrealmapp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.ActivityMainBinding;
import com.example.koushik.myrealmapp.dependencyinjection.PresenterComponent;
import com.example.koushik.myrealmapp.model.User;
import com.example.koushik.myrealmapp.presenter.MainPresenter;
import com.example.koushik.myrealmapp.view.adapter.SearchAdapter;

import java.util.List;

public class MainActivity extends PresentedActivity<MainPresenter> implements MainPresenter.IMainActivity {

    private MainPresenter presenter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        double due = presenter.getAlldue();
        double payment = presenter.getAllpayment();
        double totalDue = due - payment;
        binding.totalDueTxt.setText("Total Due: Rs. " + String.format("%.2f", totalDue));
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                finish();
            }
        });
        binding.addNewUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddUserActivity.class));
                finish();
            }
        });

        binding.clearAllDataTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }
                builder.setTitle("Delete user data")
                        .setMessage("Are you sure you want to delete all data?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                presenter.clearDataBase();
                                binding.totalDueTxt.setText("Total Due: Rs. 0.0");
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        presenter.getuserList();
    }

    @Override
    public MainPresenter onCreatePresenter() {
        presenter = new MainPresenter(this);
        return presenter;
    }

    @Override
    protected void injectPresenter(PresenterComponent component, MainPresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void setUserList(List<User> userList) {
        final SearchAdapter searchAdapter = new SearchAdapter(this, userList);
        binding.userListview.setAdapter(searchAdapter);

        binding.userListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getItemAtPosition(position);
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("SelectedUser", user);
                    startActivity(intent);
                    finish();
                }
            }
        });


        binding.userSearchView.setActivated(true);
        binding.userSearchView.setQueryHint("Search user here...");
        binding.userSearchView.onActionViewExpanded();
        binding.userSearchView.setIconified(false);
        binding.userSearchView.clearFocus();
        binding.userSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void noUserAdded() {
        binding.noUserTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearedAllData() {
        binding.userListview.setVisibility(View.GONE);
        binding.noUserTxt.setVisibility(View.VISIBLE);
    }
}
