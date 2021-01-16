package com.example.marmoushadminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class orderDetails extends AppCompatActivity {

    TextView UserNameDetails, UserPhoneDetails, UserMailDetails, UserAdressDetails, UserDateDetails, cm_titleDetails, cm_HeightDetails, cm_WidthDetails;
    ListView lv_Details;
    ImageView cm_imageDetails;
    List<Order> collect;
    RadioButton DoneRadio, NotRadio, ProcessRadio;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        UserNameDetails = findViewById(R.id.UserNameDetails);
        UserPhoneDetails = findViewById(R.id.UserPhoneDetails);
        UserMailDetails = findViewById(R.id.UserMailDetails);
        UserAdressDetails = findViewById(R.id.UserAdressDetails);
        UserDateDetails = findViewById(R.id.UserDateDetails);
        lv_Details = findViewById(R.id.lv_orderDetailsss);

        Order order = (Order) getIntent().getSerializableExtra("order");

        UserDateDetails.setText(order.created.substring(0, 10));

        DataQueryBuilder Builderr = DataQueryBuilder.create();
        Builderr.setWhereClause("ownerId='" + order.ownerId + "'");
        Backendless.Data.of(Users.class).find(Builderr, new AsyncCallback<List<Users>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleResponse(List<Users> response) {
                UserNameDetails.setText(response.get(0).name);
                UserPhoneDetails.setText(response.get(0).phone);
                UserMailDetails.setText(response.get(0).email);
                UserAdressDetails.setText(response.get(0).adress);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });

        DataQueryBuilder Builder = DataQueryBuilder.create();
        Builder.setWhereClause("ownerId='" + order.ownerId + "'");//"created='"+orderr.created+"'"
        Log.i("eeee1", order.created + "");
        Backendless.Data.of(Order.class).find(Builder, new AsyncCallback<List<Order>>() {
            @Override
            public void handleResponse(List<Order> response) {

                collect = response.parallelStream().filter(o -> o.created.equals(order.created)).collect(Collectors.toList());

                orderDetailsadapter adapter = new orderDetailsadapter(orderDetails.this, collect);
                lv_Details.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("eeee", fault + "");
            }
        });


    }


    class orderDetailsadapter extends ArrayAdapter<Order> {
        public orderDetailsadapter(@NonNull Context context, List<Order> collect) {
            super(context, 0, collect);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.cm_order_details, parent, false);

            cm_titleDetails = convertView.findViewById(R.id.cm_titleDetails);
            cm_HeightDetails = convertView.findViewById(R.id.cm_HeightDetails);
            cm_WidthDetails = convertView.findViewById(R.id.cm_WidthDetails);
            cm_imageDetails = convertView.findViewById(R.id.cm_imageDetails);
            DoneRadio = convertView.findViewById(R.id.DoneRadio);
            NotRadio = convertView.findViewById(R.id.NotRadio);
            ProcessRadio = convertView.findViewById(R.id.processingRadio);
            radioGroup = convertView.findViewById(R.id.radioGroup);


            cm_titleDetails.setText(getItem(position).title);
            cm_HeightDetails.setText(getItem(position).height+ "");
            cm_WidthDetails.setText(getItem(position).width + "");
            Picasso.get().load(getItem(position).url).resize(150,150).into(cm_imageDetails);

            String not = "Not Available";
            String done = "Done";

            String state = getItem(position).state;
            Log.i("xxx", "current state " + state);

            if (getItem(position).getState().equalsIgnoreCase(not)) NotRadio.setChecked(true);
             else if (getItem(position).getState().equalsIgnoreCase(done)) DoneRadio.setChecked(true);
             else ProcessRadio.setChecked(true);


            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                Order CurrOrder = collect.get(position);

                if (checkedId == R.id.NotRadio) {

                    CurrOrder.setState(not);

                    Log.i("xxx", "current: " + CurrOrder.getObjectId() + " state: " + CurrOrder.getState());

                    Backendless.Data.of(Order.class).save(CurrOrder, new AsyncCallback<Order>() {
                        @Override
                        public void handleResponse(Order response) {
                            Log.i("xxx", "state set to :" + response.getState());

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.i("xxx", "fault : " + fault.getCode());

                        }

                    });
                } else if (checkedId == R.id.DoneRadio) {
                    CurrOrder.setState(done);

                    Log.i("xxx", "current: " + CurrOrder.getObjectId() + " state: " + CurrOrder.getState());

                    Backendless.Data.of(Order.class).save(CurrOrder, new AsyncCallback<Order>() {
                        @Override
                        public void handleResponse(Order response) {

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });

                }
            });

            return convertView;
        }
    }
}