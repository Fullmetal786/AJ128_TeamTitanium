package com.example.nearbyfuel;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DisFragment extends Fragment {

    private static final String TAG = "DisFragment";
    TextView showDis;
    Button calculate;
    EditText mileage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dis, container, false);
        showDis = v.findViewById(R.id.showDis);
        calculate = v.findViewById(R.id.calculate);
        mileage = v.findViewById(R.id.mileage);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        mileage.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

            double fuel=HomeFragment.fuel;
            String val=mileage.getText().toString();

            showDis.setText(fuel*Float.parseFloat(val)+" m");
            }
        });
        return v;
    }

}