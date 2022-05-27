package com.example.buspark.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.example.buspark.MainActivity;
import com.example.buspark.R;
import com.example.buspark.adapter.DriverSpinnerAdapter;
import com.example.buspark.adapter.RouteSpinnerAdapter;
import com.example.buspark.model.Bus;
import com.example.buspark.model.Driver;
import com.example.buspark.model.Route;
import com.example.buspark.nodb.BusParkDB;
import com.example.buspark.rest.BusParkApiVolley;

public class ChangeBusFragment extends Fragment {

    private EditText etBusName;
    private AppCompatSpinner sp_route;
    private AppCompatSpinner sp_driver;

    private boolean checkEmpty() {
        boolean problem = false;

        if (TextUtils.isEmpty(etBusName.getText().toString())) {
            etBusName.setError("Обязательное поле");
            problem = true;
        }

        return problem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_bus, container, false);

        etBusName = view.findViewById(R.id.et_busName);

        Bus bus = (Bus) (getArguments().getSerializable(MainActivity.MSG_NAME));

        etBusName.setText(bus.getName());

        sp_route = view.findViewById(R.id.sp_route);
        RouteSpinnerAdapter routeSpinnerAdapter =
                new RouteSpinnerAdapter(
                        getActivity(),
                        R.layout.spinner_item,
                        BusParkDB.ROUTE_LIST
                );
        sp_route.setAdapter(routeSpinnerAdapter);

        sp_driver = view.findViewById(R.id.sp_driver);
        DriverSpinnerAdapter driverSpinnerAdapter =
                new DriverSpinnerAdapter(
                        getActivity(),
                        R.layout.spinner_item,
                        BusParkDB.DRIVER_LIST
                );
        sp_driver.setAdapter(driverSpinnerAdapter);

        AppCompatButton btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!checkEmpty()) {


                    new BusParkApiVolley(getActivity())
                            .updateBus(
                                    bus.getId(),
                                    etBusName.getText().toString(),
                                    ((Route) sp_route.getSelectedItem()).getName(),
                                    ((Driver) sp_driver.getSelectedItem()).getName()
                            );


                    getActivity().getSupportFragmentManager().beginTransaction().remove(ChangeBusFragment.this).commit();

                }
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        ((MainActivity) getActivity()).update();
    }
}