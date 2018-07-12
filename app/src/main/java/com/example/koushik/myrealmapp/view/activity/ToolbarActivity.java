package com.example.koushik.myrealmapp.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.ActivityToolbarBinding;
import com.example.koushik.myrealmapp.presenter.Presenter;

public abstract class ToolbarActivity<T extends Presenter> extends PresentedActivity<T> {

    private ActivityToolbarBinding toolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarBinding = DataBindingUtil.setContentView(this, R.layout.activity_toolbar);
        getLayoutBinding(toolbarBinding);
    }

    @Override
    public void setContentView(View view) {
        toolbarBinding.activityToolbar.addView(view);
    }

    public abstract void getLayoutBinding (ActivityToolbarBinding binding);

}
