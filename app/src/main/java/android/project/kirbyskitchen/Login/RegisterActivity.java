/*
 * Austin Kirby
 * CS372
 * 04/15/19
 * Project - Kirby's Kitchen
 * User Login Activity
 */
package android.project.kirbyskitchen.Login;

import android.content.Intent;
import android.project.kirbyskitchen.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    //create variables for login/register buttons and edit texts
    Button registerBtn, loginBtn;
    EditText usernameText, passwordText, cfrmPassText;
    DatabaseLoginActivity userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDatabase = new DatabaseLoginActivity(this);
        //Get text info from page
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        cfrmPassText = findViewById(R.id.confirmPasswordText);
        registerBtn = findViewById(R.id.button);
        loginBtn = findViewById(R.id.button2);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //set listener for register button and take inputs if it is clicked
            public void onClick(View v) {
                //create string variables for each field that the user provided
                String usernameStr = usernameText.getText().toString();
                String passStr = passwordText.getText().toString();
                String cfrmStr = cfrmPassText.getText().toString();
                //if there is an empty field within the login screen tell the user to fill it
                if(usernameStr.equals("") || passStr.equals("") || cfrmStr.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid username and password.", Toast.LENGTH_SHORT).show();
                }
                //if the fields are filled for username, and pass then check if the passwords entered match
                else{
                    if(passStr.equals(cfrmStr)) {
                        boolean validUsername = userDatabase.validUsername(usernameStr);
                        if(validUsername) {
                            boolean insertUser = userDatabase.insert(usernameStr, passStr);
                            if (insertUser) {
                                Toast.makeText(getApplicationContext(), "Your Registration is complete!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "This username is taken.", Toast.LENGTH_SHORT).show();;
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Entered passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
