package sg.edu.np.cosign;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void sendToMain(View view) {
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);

    }

}
