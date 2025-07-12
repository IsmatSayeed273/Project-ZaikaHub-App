package com.hello.online_cooking_recipe_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private EditText emailET, passwordET;
    private Button signInBTN;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.pasET);
        signInBTN = findViewById(R.id.siginBTN);
        TextView createAccountTV = findViewById(R.id.createAccountTV);
        TextView forgotPasswordTV = findViewById(R.id.forgotPasswordTV);
        TextView skipTV = findViewById(R.id.skipTV);

        signInBTN.setOnClickListener(v -> loginUser());
        createAccountTV.setOnClickListener(v -> startActivity(new Intent(SignIn.this, SignUp.class)));
        forgotPasswordTV.setOnClickListener(v -> resetPassword());
        skipTV.setOnClickListener(v -> startActivity(new Intent(SignIn.this, HomeActivity.class)));
    }

    private void loginUser() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Enter a valid email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordET.setError("Password is required");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            Toast.makeText(SignIn.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignIn.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignIn.this, "Verify your email first!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SignIn.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void resetPassword() {
        String email = emailET.getText().toString().trim();
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Enter a valid email to reset password");
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignIn.this, "Reset link sent to your email!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignIn.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
