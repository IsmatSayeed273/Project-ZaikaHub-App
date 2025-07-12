package com.hello.online_cooking_recipe_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private EditText nameET, phoneET, emailET, passwordET;
    private CheckBox termsCB;
    private Button signupBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        termsCB = findViewById(R.id.termsCB);
        signupBT = findViewById(R.id.signupBT);
        TextView textTV = findViewById(R.id.textTV);

        signupBT.setOnClickListener(v -> registerUser());

        textTV.setOnClickListener(v -> startActivity(new Intent(SignUp.this, SignIn.class)));
    }

    private void registerUser() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Enter a valid email");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordET.setError("Password must be at least 6 characters");
            return;
        }
        if (!termsCB.isChecked()) {
            Toast.makeText(this, "You must accept the terms & conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) user.sendEmailVerification();
                        Toast.makeText(SignUp.this, "Registration successful! Verify your email.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUp.this, SignIn.class));
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
