package sg.edu.np.cosign;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sg.edu.np.cosign.Classes.Constants;

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
        final EditText etRePassword = findViewById((R.id.reEnterText));

        if(isFormFilled()) {

        //isValidUsername(etUsername.getText().toString()) &&
        if(isValidPassword(etPassword.getText().toString()) && isValidEmail(etEmail.getText().toString()))
        {
            UserData dbData=dbHandler.findUser(etUsername.getText().toString());
            if (dbData==null){
                String dbUserName= etUsername.getText().toString();
                String dbPassword= etPassword.getText().toString();
                UserData dbUserData= new UserData();
                dbUserData.setMyUsername(dbUserName);
                dbHandler.addUser(dbUserData);

                Toast.makeText(RegisterActivity.this,"User Created Successfully", Toast.LENGTH_LONG).show();
                Intent goToMain = new Intent(this, MainActivity.class);
                startActivity(goToMain);

            }
            else{
                Toast.makeText(RegisterActivity.this,"User already exist.\n Please try again.",Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(Main2Activity.this,"Valid User Created",Toast.LENGTH_LONG).show();
            //finish();

        }
        else
        {
            //Toast.makeText(Main2Activity.this,"Invalid User Created.\nPlease Try Again.",Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(context)
                    .setTitle("Invalid Email/Password")
                    .setMessage("Password should contain at least one capital letter, one number, 6-12 characters and one of these (!@#$%) characters. \n \n" +
                            "Email should be valid")

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

    }
    public boolean isValidPassword(String password)
    {
        Pattern passwordPattern;
        Matcher passwordMatcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%]).{6,30}$";
        passwordPattern=Pattern.compile(PASSWORD_PATTERN);
        passwordMatcher=passwordPattern.matcher(password);

        Log.v(TAG,"Create Password:" + passwordMatcher.matches());

        return passwordMatcher.matches();
    }

    public boolean isValidEmail(String email){
        Pattern emailPattern;
        Matcher emailMatcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        emailPattern = Pattern.compile(EMAIL_PATTERN);
        emailMatcher = emailPattern.matcher(email);

        Log.v(TAG,"Create UserName" + emailMatcher.matches());

        return emailMatcher.matches();
    }

    public boolean isValidUsername(String userName)
    {
        Pattern userNamePattern;
        Matcher userNameMatcher;

        final String USERNAME_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,30}$";

        userNamePattern=Pattern.compile(USERNAME_PATTERN);
        userNameMatcher=userNamePattern.matcher(userName);

        Log.v(TAG,"Create UserName" + userNameMatcher.matches());

        return userNameMatcher.matches();

    }

    public boolean isFormFilled(){

        final EditText etPassword = findViewById(R.id.getPasswordText);
        final EditText etUsername = findViewById(R.id.getUsernameText);
        final EditText etEmail = findViewById(R.id.getEmailText);
        final EditText etRePassword = findViewById((R.id.reEnterText));

        //Checks if Username is entered
        if (etUsername.getText().toString().equals("")){
            new AlertDialog.Builder(context)
                    .setTitle("Invalid Username")
                    .setMessage("Please enter your Username ")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    }).show();

            return false;
        }
        //Checks if email is entered
        // Haven't check if email is repeated
        else if (etEmail.getText().toString().equals("")){
            new AlertDialog.Builder(context)
                    .setTitle("Invalid Email Address")
                    .setMessage("Please enter your Email Address ")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    }).show();

            return false;
        }
        //check if re-enter password is filled
        else if (etRePassword.getText().toString().equals("")){
            new AlertDialog.Builder(context)
                    .setTitle("Invalid Password Re-entered")
                    .setMessage("Please re-enter your password ")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    }).show();

            return false;
        }
        //Checks if re-enter password is filled or if it is the same as the password entered
        else if (!(etRePassword.getText().toString().equals(etPassword.getText().toString()))){
            new AlertDialog.Builder(context)
                    .setTitle("Invalid Password Re-entered")
                    .setMessage("Password re-entered does not match ")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    }).show();

            return false;
        }
        //else it is valid
        else{
            return true;
        }
    }

    public void sendToMain(View view) {
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);

    }

}
