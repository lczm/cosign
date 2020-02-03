package sg.edu.np.cosign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sg.edu.np.cosign.Classes.Constants;

public class MainActivity extends AppCompatActivity{
// implements View.OnTouchListener
    private static final String TAG = "MainActivity.java";
    private TextView tv_NewUser;
    //Context context = this;
    //private Session session;
    private UserData userData;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_NewUser = findViewById(R.id.registerText);
        //tv_NewUser.setOnTouchListener(this);
        if((dbHandler.findUser("CoSign") == null) || (dbHandler.findUser("cosign@gmail.com") == null)){
            Log.d(TAG, "onCreate: test account created");
            dbHandler.createTestAccount();
        }

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //session = new Session(context);
    }


/*
    //For new users onTouch listener to launch a new intent page of registration
    public boolean onTouch(View v, MotionEvent event)
    {
        Log.v(TAG, "Touch Start");
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        return true;
    }
*/

    //For Login action nad checking of username and password
    public void onClick(View v)
    {
        final EditText etPassword = findViewById(R.id.passwordText);
        final EditText etUsername = findViewById(R.id.usernameText);
        //final EditText etEmail = findViewById(R.id.usernameText);

        Log.v(TAG, "Login with: " + etUsername.getText().toString() + ", " + etPassword.getText().toString());

        // if(isValidUser(etUsername.getText().toString(),etPassword.getText().toString()))
        if (isValidUserPost(etUsername.getText().toString(), etPassword.getText().toString()))
        {
            Intent intent = new Intent(MainActivity.this, BottomNavigation.class);
            Toast.makeText(MainActivity.this, "Welcome, " + etUsername.getText().toString(), Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
        }
    }

    // A function to check with the database if what they user enter exist, returns true/false, passed in onClick
    public boolean isValidUser(String username, String password)
    {
        UserData dbData = dbHandler.findUser(username);
        Boolean valid = false;
        // added the or statement so that they can log in through both email and username
        try {
            if ((dbData.getMyUsername().equals(username) || (dbData.getMyEmail().equals(username)))) {
                if (dbData.getMyPassword().equals(password)){
                    valid = true;
                }
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            valid = false;
        }
        //Log.d
        return valid;
    }

    public boolean isValidUserPost(String email, String password)
    {
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // .url("http://35.229.247.145:5000/login")
                    .url(Constants.serverIP + Constants.databasePort + "/login")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                // Store the user data here
/*
                SharedPreferences.Editor editor = getSharedPreferences(
                        "userData", MODE_PRIVATE).edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();
*/

                SharedPreferences pref = getApplicationContext().
                        getSharedPreferences("userData", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.commit();

                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



    public void sendToRegister(View view) {
        Intent goToRegister = new Intent(this, RegisterActivity.class);
        startActivity(goToRegister);

    }


}