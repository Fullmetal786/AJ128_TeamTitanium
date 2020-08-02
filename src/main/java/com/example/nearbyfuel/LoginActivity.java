package com.example.nearbyfuel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    int AUTHUI_REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            startActivity(new Intent(this,MainActivity.class));
            this.finish();
        }
    }

    public void handleLogin(View view) {

        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        Intent intent= AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("http://example.com","http://example.com")
                .setLogo(R.drawable.icon)
                .setAlwaysShowSignInMethodScreen(true)
                .build();

        startActivityForResult(intent,AUTHUI_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AUTHUI_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                //User has signed in successfully
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                //User is new
                if(user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp())
                {
                    Toast.makeText(this, "Welcome new User", Toast.LENGTH_SHORT).show();
                }
                //User already has an account
                else
                {
                    Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();      //Not to comeback on pressing back button
            }
            //Sign Failed
            else
            {
                IdpResponse response=IdpResponse.fromResultIntent(data);
                if(response!=null)
                {
                    Toast.makeText(this, "Error : "+response.getError(), Toast.LENGTH_SHORT).show();
                }
                //else user has cancelled the sign in request
            }
        }
    }
}