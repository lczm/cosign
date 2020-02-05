package sg.edu.np.cosign.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import sg.edu.np.cosign.Camera2Activity;
import sg.edu.np.cosign.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;


public class LearnSign extends AppCompatActivity {

    String charOrNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_sign);

        ImageView signIV = findViewById(R.id.signIV);


        Intent in = getIntent();
        String pos = in.getStringExtra("pos");
        String signType = in.getStringExtra("wordOrNum");
        ArrayList<String> data = in.getStringArrayListExtra("data");
        charOrNum = data.get(Integer.parseInt(pos));

        if (Objects.equals(signType, "word"))
        {
            String imgName = "alpha" + charOrNum.toLowerCase();
            signIV.setImageResource(getResources().getIdentifier(imgName, "drawable", this.getPackageName()));
        }
        else
        {
            String imgName = "num" + (Integer.parseInt(pos) + 1);
            signIV.setImageResource(getResources().getIdentifier(imgName, "drawable", this.getPackageName()));
        }
        ((TextView) findViewById(R.id.word_num_text)).setText("This is how you sign " + charOrNum + "!");
    }

    public void sendToCamera(View view) {
        Intent deliverToCamera = new Intent(this, Camera2Activity.class);
        deliverToCamera.putExtra("charOrNum", charOrNum);
        startActivity(deliverToCamera);
    }
}
