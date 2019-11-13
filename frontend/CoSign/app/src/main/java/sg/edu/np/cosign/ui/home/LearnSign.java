package sg.edu.np.cosign.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import sg.edu.np.cosign.Camera2Activity;
import sg.edu.np.cosign.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LearnSign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_sign);
    }

    public void sendToCamera(View view) {
        Intent deliverToCamera = new Intent(this, Camera2Activity.class);
        startActivity(deliverToCamera);
    }
}
