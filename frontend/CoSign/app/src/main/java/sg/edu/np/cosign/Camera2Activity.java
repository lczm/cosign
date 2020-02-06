package sg.edu.np.cosign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Camera2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);


        Intent in = getIntent();
        String charOrNum = in.getStringExtra("charOrNum");

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2Fragment.newInstance(), "camera2Fragment")
                    .commit();

        }

        getSupportFragmentManager().executePendingTransactions();

        Camera2Fragment camera2Fragment = (Camera2Fragment)getSupportFragmentManager().findFragmentByTag("camera2Fragment");
        camera2Fragment.setSignText(charOrNum);
    }
}
