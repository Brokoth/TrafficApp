package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText email,inputPassword, confrimPassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private TextView btnSignIn;
    private FirebaseAuth auth;
    public String status;
    public String id;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnSignIn =  findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.register_btn);
        email = (TextInputEditText) findViewById(R.id.reg_email);
        inputPassword = (TextInputEditText) findViewById(R.id.register_password);
        confrimPassword = (TextInputEditText) findViewById(R.id.confirm_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String inputEmail = email.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String cpassword = confrimPassword.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                if (TextUtils.isEmpty(inputEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(inputEmail.matches(emailPattern))) {
                    Toast.makeText(getApplicationContext(), "Please Enter A Valid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(password.matches(passwordPattern))) {
                    Toast.makeText(getApplicationContext(), "Password must contain at least 1 Capital Alphabet Character, 1 Number and 1 Special Character!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!(password.equals(cpassword))) {
                    Toast.makeText(getApplicationContext(), "The entered passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(inputEmail, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, "Registration failed. Please check your internet connection or try again later. ",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    final FirebaseUser newuser = auth.getCurrentUser();
                                    newuser.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("reg_page", "Email sent.");
                                                        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                        status = "user";
                                                        POJO_user user = new POJO_user(status, inputEmail, id);
                                                        db.collection("Users").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                progressBar.setVisibility(View.GONE);

                                                                Toast.makeText(RegisterActivity.this, "Registered Successfully. An email verification link has been sent to your email address",
                                                                        Toast.LENGTH_LONG).show();
                                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                                finish();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                progressBar.setVisibility(View.GONE);
                                                                Toast.makeText(RegisterActivity.this, "Registration failed. Please check your internet connection or try again later. ",
                                                                        Toast.LENGTH_SHORT).show();
                                                                newuser.delete();
                                                            }
                                                        });

                                                    } else {
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(RegisterActivity.this, "Registration failed. Please check your internet connection or try again later. ",
                                                                Toast.LENGTH_SHORT).show();
                                                        newuser.delete();
                                                    }
                                                }
                                            });


                                }
                            }
                        });
            }
        });
    }
}

