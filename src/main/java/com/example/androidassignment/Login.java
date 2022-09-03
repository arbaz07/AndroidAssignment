package com.example.androidassignment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password, loginEmail, loginPassword;
    private Button register, login;
    private TextView loginLink, registerLink;
    private ConstraintLayout loginMainScreen, registrationMainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); // Hide action bar from login screen

        mAuth = FirebaseAuth.getInstance();

        idsInitialisations(); // Ids initialisation
    }

    /**
     * This function is used to initialize ids
     * Function calling of check user data
     */
    private void idsInitialisations() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        login = findViewById(R.id.login);

        registrationMainScreen = findViewById(R.id.registrationMainScreen);
        loginMainScreen = findViewById(R.id.loginMainScreen);

        loginLink = findViewById(R.id.loginLink);
        registerLink = findViewById(R.id.registerLink);

        checkUser();
    }

    /**
     * Handle visibility of layouts (login and register)
     * Set user data in singleton
     * Function calling of click listeners
     */
    private void checkUser() {
        if (mAuth.getCurrentUser() == null) {
            registrationMainScreen.setVisibility(View.VISIBLE);
        } else {
            if (mAuth.getCurrentUser().getEmail() != null) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                FirebaseUser user = mAuth.getCurrentUser();
                SetAndGetData.getInstance().setFirebaseUser(user);
            } else {
                loginMainScreen.setVisibility(View.VISIBLE);
            }
        }
        registrationClickListeners();
    }

    /**
     * Click listeners of registration and login
     */
    private void registrationClickListeners() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText, passwordText;
                emailText = email.getText().toString();
                passwordText = password.getText().toString();

                // Validations for input email and password
                if (TextUtils.isEmpty(emailText)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter email to register", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter password to register", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser(emailText, passwordText);
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationMainScreen.setVisibility(View.GONE);
                loginMainScreen.setVisibility(View.VISIBLE);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationMainScreen.setVisibility(View.VISIBLE);
                loginMainScreen.setVisibility(View.GONE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTextLogin, passwordTextLogin;
                emailTextLogin = loginEmail.getText().toString();
                passwordTextLogin = loginPassword.getText().toString();

                // Validations for input email and password
                if (TextUtils.isEmpty(emailTextLogin)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter email to login", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordTextLogin)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter password to login", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginUser(emailTextLogin, passwordTextLogin);
            }
        });
    }

    /**
     * Login User On Firebase
     * Setting Data On Singleton
     *
     * @param emailTextLogin
     * @param passwordTextLogin
     */
    private void loginUser(String emailTextLogin, String passwordTextLogin) {
        mAuth.signInWithEmailAndPassword(emailTextLogin, passwordTextLogin)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SetAndGetData.getInstance().setFirebaseUser(user);
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Register User on Firebase
     * Setting data in singleton
     *
     * @param emailText
     * @param passwordText
     */
    private void registerUser(String emailText, String passwordText) {
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            SetAndGetData.getInstance().setFirebaseUser(user);

                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}