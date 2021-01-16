package com.example.marmoushadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class loginActivity extends AppCompatActivity {

    EditText etname,etpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etname=findViewById(R.id.ET_name);
        etpass=findViewById(R.id.ET_Pass);


        Backendless.initApp(this,"A2B83EC3-2382-E066-FFDA-43CCF14A8B00","CE6F3A5C-20CF-4277-AD8F-3A53F1BEC34E");


        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if (response){
                    Intent in=new Intent (loginActivity.this,MainActivity.class);
                    startActivity(in);}
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }


    public void login(View view) {

        Backendless.UserService.login(etname.getText().toString(), etpass.getText().toString(), new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(loginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                Intent in=new Intent (loginActivity.this,MainActivity.class);
                startActivity(in);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                if (fault.getCode().equals("3003"))
                    Toast.makeText(loginActivity.this, "error in pass or name", Toast.LENGTH_SHORT).show();
                else if (fault.getCode().equals("3087"))
                    Toast.makeText(loginActivity.this, "please conferm the email", Toast.LENGTH_SHORT).show();
                else if (fault.getCode().equals("3002"))
                    Toast.makeText(loginActivity.this, "logged for many times", Toast.LENGTH_SHORT).show();
            }
        },true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}