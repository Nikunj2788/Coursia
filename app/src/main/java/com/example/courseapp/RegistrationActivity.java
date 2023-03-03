package com.example.courseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private TextInputEditText userNameEdt,pwdedit,cofpwdedt;
    private Button registerBtn;
    private ProgressBar lodingPB;
    private FirebaseAuth mAuth;
    private TextView logingTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userNameEdt = findViewById(R.id.idEditUserName);
        pwdedit = findViewById(R.id.idEditpassword);
        cofpwdedt = findViewById(R.id.idconfrimpassword);
        registerBtn = findViewById(R.id.idBtnRegister);
        lodingPB = findViewById(R.id.idPBloding);
        logingTv= findViewById(R.id.idTvLogin);

        mAuth = FirebaseAuth.getInstance();
        logingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lodingPB.setVisibility(View.VISIBLE);
                String UserName = userNameEdt.getText().toString();
                String pwd = pwdedit.getText().toString();
                String cnpwd = cofpwdedt.getText().toString();
                if(!pwd.equals(cnpwd)){
                    Toast.makeText(RegistrationActivity.this,"Please Chekc Both Password",Toast.LENGTH_SHORT);

                }
                else if(TextUtils.isEmpty(UserName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnpwd)){
                    Toast.makeText(RegistrationActivity.this, "Please add Your Credentials", Toast.LENGTH_SHORT).show();

                }
                else{
                    mAuth.createUserWithEmailAndPassword(UserName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                lodingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                lodingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Fail to Register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}