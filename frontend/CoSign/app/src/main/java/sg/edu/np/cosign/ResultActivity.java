package sg.edu.np.cosign;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ResultActivity extends AppCompatActivity {

    ImageView iv;
    TextView answerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        iv = findViewById(R.id.resultIV);
        answerTV = findViewById(R.id.answerTV);

        File img = (File)getIntent().getExtras().get("capturedImg");
        String answer = (String)getIntent().getExtras().get("answer");


        if(img.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());

            iv.setImageBitmap(myBitmap);
            answerTV.setText(answer);

        }
    }
}
