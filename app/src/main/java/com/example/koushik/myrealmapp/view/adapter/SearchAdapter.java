package com.example.koushik.myrealmapp.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.example.koushik.myrealmapp.R;
import com.example.koushik.myrealmapp.databinding.SearchListItemBinding;
import com.example.koushik.myrealmapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by koushik on 16/5/17.
 */

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private List<User> mData = null;

    //    ValueFilter valueFilter;
    public SearchAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        mData = new ArrayList<>();
        mData.addAll(userList);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchListItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_list_item, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (SearchListItemBinding) convertView.getTag();
        }
        User user = mData.get(position);

        binding.searchTxt.setText(user.getFirstName() + " " + user.getLastName());
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mData.clear();
        if (charText.length() == 0) {
            mData.addAll(userList);
        } else {
            for (User cc : userList) {
                if (cc.getFirstName().toLowerCase(Locale.getDefault()).contains(charText) || cc.getLastName().toLowerCase(Locale.getDefault()).contains(charText) || (cc.getFirstName().toLowerCase(Locale.getDefault()) + " " + cc.getLastName().toLowerCase(Locale.getDefault())).contains(charText)) {
                    mData.add(cc);
                }
            }
        }
        notifyDataSetChanged();
    }

}
