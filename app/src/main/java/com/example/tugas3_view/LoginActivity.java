package com.example.tugas3_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.tugas3_view.databinding.ActivityLoginBinding;

import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {
    MyDbHelper myDbHelper;
    private ActivityLoginBinding binding;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup view binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HotelFragment()).commit();
        }
        session = new SessionManager(getApplicationContext());
        myDbHelper = new MyDbHelper(this);
        SQLiteDatabase sqLiteDatabase =
                myDbHelper.getWritableDatabase();
        binding.Signinbuttonid.setOnClickListener(this);
        binding.SignUpHerebuttonid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username =
                binding.signinusernameEditText.getText().toString();
        String password =
                binding.signinpasswordEditText.getText().toString();
        if (v.getId() == R.id.Signinbuttonid) {
            Boolean result = myDbHelper.findPassword(username, password);
            if (result == true) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                session.setLogin(true);
                finish();
            } else {
                Toast.makeText(this, "username and password didn`t match", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.SignUpHerebuttonid) {
            Intent intent = new Intent(LoginActivity.this,
                    RegisterActivity.class);
            startActivity(intent);
        }
    }
}