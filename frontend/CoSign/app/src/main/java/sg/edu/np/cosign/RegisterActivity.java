package sg.edu.np.cosign;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity.java";

    Context context = this;

    MyDBHandler dbHandler= new MyDBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onCreateClick(View v)
    {
        final EditText etPassword = findViewById(R.id.getPasswordText);
        final EditText etUsername = findViewById(R.id.getUsernameText);
        final EditText etEmail = findViewById(R.id.getEmailText);

        if(isValidUsername(etUsername.getText().toString()) && isValidPassword(etPassword.getText().toString()))
        {
            UserData dbData=dbHandler.findUser(etUsername.getText().toString());
            if (dbData==null){
                String dbUserName= etUsername.getText().toString();
                String dbPassword= etPassword.getText().toString();
                UserData dbUserData= new UserData();
                dbUserData.setMyUsername(dbUserName);
                dbUserData.setMyPassword((dbPassword));
                dbHandler.addUser(dbUserData);

                Toast.makeText(RegisterActivity.this,"User Created Successfully", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(RegisterActivity.this,"User already exist.\n Please try again.",Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(Main2Activity.this,"Valid User Created",Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            //Toast.makeText(Main2Activity.this,"Invalid User Created.\nPlease Try Again.",Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(context)
                    .setTitle("Invalid Username/Password")
                    .setMessage("Username should contain at least one capital letter, one number and 6-12 characters. \n \n" +
                            "Password should contain at least one capital letter, one number, 6-12 characters and one of these (!@#$%) characters.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })
                    .show();
        }

    }
    public boolean isValidPassword(String password)
    {
        Pattern passwordPattern;
        Matcher passwordMatcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%]).{6,12}$";
        passwordPattern=Pattern.compile(PASSWORD_PATTERN);
        passwordMatcher=passwordPattern.matcher(password);

        Log.v(TAG,"Create Password:" + passwordMatcher.matches());

        return passwordMatcher.matches();
    }

    public boolean isValidUsername(String userName)
    {
        Pattern userNamePattern;
        Matcher userNameMatcher;

        final String USERNAME_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,12}$";

        userNamePattern=Pattern.compile(USERNAME_PATTERN);
        userNameMatcher=userNamePattern.matcher(userName);

        Log.v(TAG,"Create UserName" + userNameMatcher.matches());

        return userNameMatcher.matches();

    }

    public void sendToMain(View view) {
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);

    }

}
