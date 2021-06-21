package com.example.demo.Registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.demo.API.ApiClient;
import com.example.demo.Login.Login;
import com.example.demo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    EditText firstName, lastName, email, password, repeatPassword;
    TextView goToSignIn;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) ||
                        TextUtils.isEmpty(password.getText().toString()) ||
                        TextUtils.isEmpty(firstName.getText().toString()) ||
                        TextUtils.isEmpty(lastName.getText().toString())){
                    ShowDialogWindow("Пустые поля");
                }
                else{
                    if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        if(password.getText().toString().equals(repeatPassword.getText().toString())){
                            RegisterRequest registerRequest = new RegisterRequest();
                            registerRequest.setEmail(email.getText().toString());
                            registerRequest.setPassword(password.getText().toString());
                            registerRequest.setFirstName(firstName.getText().toString());
                            registerRequest.setLastName(lastName.getText().toString());
                            RegisterUser(registerRequest);
                        }
                        else{
                            ShowDialogWindow("Повторите пароль!");
                        }
                    }
                    else{
                        ShowDialogWindow("Email некорректный");
                    }
                }
            }
        });
    }

    public void RegisterUser(RegisterRequest registerRequest)
    {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(Registration.this, Login.class));
                }
                else{
                    ShowDialogWindow("Что-то пошло не так, повторите попытку позже");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ShowDialogWindow(t.getLocalizedMessage());
            }
        });
    }

    public void ShowDialogWindow(String text){
        final AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).setMessage(text).setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }

    public void init(){
        firstName = findViewById(R.id.NameRegisrat);
        lastName = findViewById(R.id.SurnameRegistar);
        email = findViewById(R.id.EmailRegistar);
        password = findViewById(R.id.PassRegistr1);
        repeatPassword = findViewById(R.id.PassRegistr2);
        goToSignIn = findViewById(R.id.LoginRegis);
        signUp = findViewById(R.id.Registration);
    }
}