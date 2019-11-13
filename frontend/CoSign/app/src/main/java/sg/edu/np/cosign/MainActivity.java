package sg.edu.np.cosign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sendToCamera(View view) {
        Intent deliverToCamera = new Intent(this, CameraActivity.class);
        startActivity(deliverToCamera);
    }

    public void sendToRegister(View view) {
        Intent goToRegister = new Intent(this, RegisterActivity.class);
        startActivity(goToRegister);

    }

}