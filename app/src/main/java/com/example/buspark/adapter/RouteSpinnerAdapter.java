package com.example.buspark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.buspark.R;
import com.example.buspark.model.Route;
import com.example.buspark.nodb.BusParkDB;

import java.util.List;

public class RouteSpinnerAdapter extends ArrayAdapter<Route> {

    public RouteSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Route> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_item, null);
        }

        ((TextView) convertView.findViewById(R.id.tv_spinner_item))
                .setText(BusParkDB.ROUTE_LIST.get(position).getName());

        return convertView;
    }


}