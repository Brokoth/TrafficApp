package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AccountSettingsActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ImageView save_email, save_pass, back;
    private Button signout, delete;
    private EditText newemail, repeatpass, newpass;
    private ProgressBar emailpbar, passpbar, deletepbar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseFirestore db;
    private DocumentReference db2;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private String id, newemailstr, repeatpassstr, newpassstr;
    private String emailPattern;
    private String passwordPattern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_activity_layout);
        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        save_email = findViewById(R.id.save_email_btn);
        save_pass = findViewById(R.id.save_pass_btn);
        signout = findViewById(R.id.signout_btn);
        delete = findViewById(R.id.delete_btn);
        newemail = findViewById(R.id.newemail);
        repeatpass = findViewById(R.id.repeat_pass);
        newpass = findViewById(R.id.new_pass);
        emailpbar = findViewById(R.id.email_progress_bar);
        passpbar = findViewById(R.id.pass_progress_bar);
        deletepbar = findViewById(R.id.delete_progress_bar);
        Toolbar toolbar = findViewById(R.id.toolbar_acc_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Account");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null) {
                    Intent intent = new Intent(AccountSettingsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        };

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        newemail.setText(user.getEmail());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailpbar.setVisibility(View.VISIBLE);
                newemailstr = newemail.getText().toString().trim();
                emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(newemailstr) || !(newemailstr.matches(emailPattern))) {
                    Toast.makeText(getApplicationContext(), "Please Enter A Valid email address.", Toast.LENGTH_SHORT).show();
                    emailpbar.setVisibility(View.GONE);
                    return;
                } else {
                    user.updateEmail(newemailstr).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            db.collection("Users").document(id).update("inputEmail", newemailstr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    emailpbar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Please Sign In With Your New Credentials!", Toast.LENGTH_SHORT).show();
                                    signOut();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    emailpbar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });


                        }
                    });
                }
            }
        });

        save_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passpbar.setVisibility(View.VISIBLE);
                passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                newpassstr = newpass.getText().toString().trim();
                repeatpassstr = repeatpass.getText().toString().trim();
                if (!(TextUtils.isEmpty(newpassstr)) || !(TextUtils.isEmpty(repeatpassstr))) {
                    if (newpassstr.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        passpbar.setVisibility(View.GONE);
                        return;
                    }
                    if (!(newpassstr.matches(passwordPattern))) {
                        Toast.makeText(getApplicationContext(), "Password must contain at least 1 Capital Alphabet Character, 1 Number and 1 Special Character!", Toast.LENGTH_LONG).show();
                        passpbar.setVisibility(View.GONE);
                        return;
                    }
                    if (!(newpassstr.equals(repeatpassstr))) {
                        Toast.makeText(getApplicationContext(), "The entered passwords do not match", Toast.LENGTH_LONG).show();
                        passpbar.setVisibility(View.GONE);
                        return;
                    }
                    user.updatePassword(newpassstr).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            passpbar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Please Sign In With Your New Credentials!", Toast.LENGTH_LONG).show();
                            signOut();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            //Log.e("account settings: ","pass: "+e.getMessage());
                            passpbar.setVisibility(View.GONE);
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in both password fields!", Toast.LENGTH_LONG).show();
                    passpbar.setVisibility(View.GONE);
                }
            }

        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletepbar.setVisibility(View.GONE);
                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("Users").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                deletepbar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Please Sign In With Your New Credentials!", Toast.LENGTH_SHORT).show();
                                signOut();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        //Log.e("account settings: ","delete: "+e.getMessage());
                        deletepbar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void signOut() {
        auth.signOut();
        startActivity(new Intent(AccountSettingsActivity.this, LoginActivity.class));
        finish();
    }

}
