package com.example.koushik.myrealmapp.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.ActivityDueDetailsBinding;
import com.example.koushik.myrealmapp.dependencyinjection.PresenterComponent;
import com.example.koushik.myrealmapp.model.Amount;
import com.example.koushik.myrealmapp.presenter.DueDetailsPresenter;
import com.example.koushik.myrealmapp.view.adapter.HistoryAdapter;

import java.util.List;

/**
 * Created by koushik on 13/7/17.
 */

public class DueDetailsActivity extends PresentedActivity<DueDetailsPresenter> implements DueDetailsPresenter.IDueDetailsACtivity {

    private ActivityDueDetailsBinding binding;
    private DueDetailsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_due_details);
        String userId = getIntent().getStringExtra("userId");
        String type = getIntent().getStringExtra("type");
        if (type.equals("payment")) {
            if (userId != null) {
                presenter.getPaymentHistory(userId);
            }
        } else if (type.equals("due")) {
            if (userId != null) {
                presenter.getDueHistory(userId);
            }
        }

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected DueDetailsPresenter onCreatePresenter() {
        presenter = new DueDetailsPresenter(this);
        return presenter;
    }

    @Override
    protected void injectPresenter(PresenterComponent component, DueDetailsPresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void setDueList(List<Amount> amountList) {
        binding.titleTxt.setText("Due Details");
        if (amountList != null && amountList.size() != 0) {
            HistoryAdapter historyAdapter = new HistoryAdapter(this, amountList);
            binding.historyListview.setAdapter(historyAdapter);
        } else {
            binding.noHistoryTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setPaymentList(List<Amount> amountList) {
        binding.titleTxt.setText("Payment Details");
        if (amountList != null && amountList.size() != 0) {
            HistoryAdapter historyAdapter = new HistoryAdapter(this, amountList);
            binding.historyListview.setAdapter(historyAdapter);
        } else {
            binding.noHistoryTxt.setVisibility(View.VISIBLE);
        }
    }
}
