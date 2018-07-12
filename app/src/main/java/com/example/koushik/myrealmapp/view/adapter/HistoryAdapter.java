package com.example.koushik.myrealmapp.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.HistoryListItemBinding;
import com.example.koushik.myrealmapp.model.Amount;

import java.util.List;

/**
 * Created by koushik on 13/7/17.
 */

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private List<Amount> amountList;

    public HistoryAdapter(Context context, List<Amount> amountList) {
        this.context = context;
        this.amountList = amountList;
    }

    @Override
    public int getCount() {
        return amountList.size();
    }

    @Override
    public Object getItem(int position) {
        return amountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryListItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.history_list_item, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (HistoryListItemBinding) convertView.getTag();
        }
        Amount amount = amountList.get(position);
        binding.dueTxt.setText("Rs. " + String.format("%.2f", amount.getPrice()));
        binding.dateTxt.setText(amount.getDate());
        binding.descriptionTxt.setText(amount.getDescription());
        return convertView;
    }
}
