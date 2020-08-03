package com.example.nearbyfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG ="ProfileActivity" ;
    TextView name;
    EditText editText;

    TextView name_value,vehicle_value,phone_value;

    LinearLayout name_layout,vehicle_layout,phone_layout;

    CircleImageView profile_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.field_name);
        editText=findViewById(R.id.value_name);
        name_layout=findViewById(R.id.name_layout);
        vehicle_layout=findViewById(R.id.vehicle_layout);
        phone_layout=findViewById(R.id.phone_layout);
        profile_pic=findViewById(R.id.profile_pic);

        name_value=findViewById(R.id.name_value);
        vehicle_value=findViewById(R.id.vehicle_value);
        phone_value=findViewById(R.id.phone_value);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(user.getDisplayName()!=null)
                name_value.setText(user.getDisplayName());

                phone_value.setText(user.getPhoneNumber());
            }
            if(user.getPhotoUrl()!=null)
            {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(profile_pic);
            }
        }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }

    public void handleImage(View view)
    {
        startActivityForResult(new Intent(this,ProfilePhotoActivity.class),0);
    }


    public void handleNameLayout(View view) {
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_dialog,(LinearLayout)findViewById(R.id.bottomSheetContainer));

        final TextView field_name=bottomSheetView.findViewById(R.id.field_name);
        final EditText value_name=bottomSheetView.findViewById(R.id.value_name);
        name_value=findViewById(R.id.name_value);
        field_name.setText("Enter your name");
        value_name.setText(name_value.getText());
        value_name.setSelection(name_value.getText().length());
        bottomSheetView.findViewById(R.id.btnsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_value.setText(value_name.getText().toString());
                updateProfile();
              //  updateName();
                Toast.makeText(ProfileActivity.this, "Save pressed", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetView.findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }


    public void handlePhoneLayout(View view) {
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_dialog,(LinearLayout)findViewById(R.id.bottomSheetContainer));

        final TextView field_name=bottomSheetView.findViewById(R.id.field_name);
        final EditText value_name=bottomSheetView.findViewById(R.id.value_name);
        name_value=findViewById(R.id.phone_value);
        field_name.setText("Enter your phone number");
        value_name.setText(name_value.getText());
        value_name.setSelection(name_value.getText().length());
        bottomSheetView.findViewById(R.id.btnsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_value.setText(value_name.getText().toString());
                Toast.makeText(ProfileActivity.this, "Save pressed", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetView.findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void handleVehicleLayout(View view) {

        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_dialog,(LinearLayout)findViewById(R.id.bottomSheetContainer));

        final TextView field_name=bottomSheetView.findViewById(R.id.field_name);
        final EditText value_name=bottomSheetView.findViewById(R.id.value_name);
        name_value=findViewById(R.id.vehicle_value);
        field_name.setText("Enter your vehicle Name");
        value_name.setText(name_value.getText());
        value_name.setSelection(name_value.getText().length());
        bottomSheetView.findViewById(R.id.btnsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                name_value.setText(value_name.getText().toString());
                }
        });
        bottomSheetView.findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void updateProfile()
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                .setDisplayName(name_value.getText().toString())
                .build();
        firebaseUser.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, "Name updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}