/*
 * Austin Kirby
 * CS372
 * 04/15/19
 * Project - Kirby's Kitchen
 * User Login Activity
 */

package android.project.kirbyskitchen.Login;

import android.content.Intent;
import android.os.Bundle;
import android.project.kirbyskitchen.MainActivity;
import android.project.kirbyskitchen.R;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    EditText loginUsername, loginPassword;
    DatabaseLoginActivity userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsername = findViewById(R.id.usernameLoginText);
        loginPassword = findViewById(R.id.passwordLoginText);
        userDatabase = new DatabaseLoginActivity(this);
        loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                boolean validLogin = userDatabase.validLoginCredentials(username, password);
                //if the user enters information that is valid
                if(validLogin) {
                    Toast validLoginMsg = Toast.makeText(getApplicationContext(), "You have successfully logged in!", Toast.LENGTH_SHORT);
                    TextView loginView = (TextView) validLoginMsg.getView().findViewById(android.R.id.message);
                    if(loginView != null){
                        validLoginMsg.setGravity(Gravity.CENTER, 0, 250);
                        loginView.setGravity(Gravity.CENTER);
                        validLoginMsg.show();
                    }

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
                //if the user enters information that is invalid regarding a login
                else {
                    Toast invalidLogin = Toast.makeText(getApplicationContext(), "Please enter a valid Username and Password", Toast.LENGTH_SHORT);
                    TextView loginView = (TextView) invalidLogin.getView().findViewById(android.R.id.message);
                    if(loginView != null){
                        invalidLogin.setGravity(Gravity.CENTER, 0, 250);
                        loginView.setGravity(Gravity.CENTER);
                        invalidLogin.show();
                    }

                }
            }
        });
    }
}
