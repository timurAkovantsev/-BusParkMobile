package com.example.buspark.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspark.MainActivity;
import com.example.buspark.R;
import com.example.buspark.fragment.ChangeBusFragment;
import com.example.buspark.model.Bus;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<Bus> busList;
    private Context context;

    public BusAdapter(Context context, List<Bus> busList) {

        this.inflater = LayoutInflater.from(context);
        this.busList = busList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName,
                tvRoute,
                tvDriver;
        private final LinearLayout ll_item;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            ll_item = itemView.findViewById(R.id.ll_item);
            tvName = itemView.findViewById(R.id.tv_name);
            tvRoute = itemView.findViewById(R.id.tv_route);
            tvDriver = itemView.findViewById(R.id.tv_driver);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bus_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @SuppressLint("RecyclerView") int position
    ) {

        Bus bus = busList.get(position);

        ((MyViewHolder) holder).tvName.setText("Номер автобуса: " + bus.getName());
        ((MyViewHolder) holder).tvRoute.setText("Маршрут: " + bus.getRoute().getName());
        ((MyViewHolder) holder).tvDriver.setText(bus.getDriver().getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeBusFragment changeClientFragment = new ChangeBusFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable(MainActivity.MSG_NAME, busList.get(position));
                changeClientFragment.setArguments(bundle);

                ((AppCompatActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_main, changeClientFragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {

        return busList.size();
    }
}