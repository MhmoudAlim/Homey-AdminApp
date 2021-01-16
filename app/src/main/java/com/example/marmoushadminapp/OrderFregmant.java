package com.example.marmoushadminapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;


public class OrderFregmant extends Fragment {
    ListView lv_orders;
    TextView cm_ordername, cm_orderDate;
    private List<Users> names;
    List<Order> collect;
    boolean get=false;

    public OrderFregmant() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_order_fregmant, container, false);
        lv_orders = view.findViewById(R.id.lv_orders);

        Backendless.Data.of(Users.class).find(new AsyncCallback<List<Users>>() {
            @Override
            public void handleResponse(List<Users> responseee) {
                Log.i("ccc" ,responseee+"");
                names = responseee;
                Backendless.Data.of(Order.class).find(new AsyncCallback<List<Order>>() {
                    @Override
                    public void handleResponse(List<Order> response) {
                        Log.i("aaa", response.toString());


                        collect = response.stream().sorted(Comparator.comparing(order -> order.created)).collect(Collectors.toList());

                        for (int i = 0; i < collect.size(); i++) {
                            for (int j = i + 1; j < collect.size(); j++) {
                                if (collect.get(i).ownerId.equals(collect.get(j).ownerId) && collect.get(i).created.equals(collect.get(j).created)) {
                                    collect.remove(i);
                                }
                            }

                        }


                        orderadapter adapter = new orderadapter(getActivity(), collect);
                        lv_orders.setAdapter(adapter);


                        lv_orders.setOnItemClickListener((parent, view1, position, id) -> {
                            Order orderbyname = collect.get(position);
                            Intent in = new Intent(getActivity(), orderDetails.class);
                            in.putExtra("order", orderbyname);
                            startActivity(in);
                        });
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
                get=true;
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(getActivity(), "failsd", Toast.LENGTH_SHORT).show();
            }
        });

        if (get=true) {


        }

        return view;
    }

    class orderadapter extends ArrayAdapter<Order> {
        public orderadapter(@NonNull Context context, List<Order> collect) {
            super(context, 0, collect);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.cm_order, parent, false);
            cm_ordername = convertView.findViewById(R.id.cm_orderName);
            cm_orderDate = convertView.findViewById(R.id.cm_orderDate);

            if (names != null) {
                List<String> collect = names.parallelStream().filter(users -> users.ownerId.equals(getItem(position).ownerId)).map(users -> users.name).collect(Collectors.toList());
                cm_ordername.setText(collect.get(0));
            } else {

                lv_orders.deferNotifyDataSetChanged();
            }

            cm_orderDate.setText(getItem(position).created);

            return convertView;
        }
    }

}