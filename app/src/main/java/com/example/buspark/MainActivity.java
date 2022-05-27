package com.example.buspark;

import static com.example.buspark.nodb.BusParkDB.BUS_LIST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.buspark.adapter.BusAdapter;
import com.example.buspark.fragment.AddBusFragment;
import com.example.buspark.model.Bus;
import com.example.buspark.rest.BusParkApi;
import com.example.buspark.rest.BusParkApiVolley;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_NAME = "busFromListByPos";

    private AppCompatButton btnAdd;

    private FragmentTransaction transaction;

    private BusAdapter busAdapter;

    private RecyclerView rvBuss;

    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;

    private final BusParkApi busParkApi = new BusParkApiVolley(this);

    @Override
    protected void onResume() {
        super.onResume();

        busParkApi.fillBus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busParkApi.fillRoute();
        busParkApi.fillDriver();

        rvBuss = findViewById(R.id.rv_buses);
        busAdapter = new BusAdapter(this, BUS_LIST);
        rvBuss.setAdapter(busAdapter);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(view -> {

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_main, new AddBusFragment());
            transaction.commit();
        });

        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                Bus bus = BUS_LIST.get(position);

                if (swipeDir == ItemTouchHelper.LEFT) {
                    Toast.makeText(MainActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                    busParkApi.deleteBus(bus.getId());

                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvBuss);
    }

    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        int size = fragments.size();
        if (size > 0)
            getSupportFragmentManager().beginTransaction().remove(fragments.get(size-1)).commit();
        else
            finish();
    }

    public void update() {

        busAdapter.notifyDataSetChanged();
    }

}