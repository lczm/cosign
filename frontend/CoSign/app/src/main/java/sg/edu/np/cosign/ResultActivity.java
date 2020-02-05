package sg.edu.np.cosign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import sg.edu.np.cosign.ui.home.SelectNumActivity;
import sg.edu.np.cosign.ui.home.SelectWordActivity;

public class ResultActivity extends AppCompatActivity {

    ImageView iv;
    TextView answerTV;
    Button directToAlphaBtn;
    Button directToNumBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        iv = findViewById(R.id.resultIV);
        answerTV = findViewById(R.id.answerTV);
        directToAlphaBtn = findViewById(R.id.directToAlphaBtn);
        directToNumBtn = findViewById(R.id.directToNumBtn);

        File img = (File)getIntent().getExtras().get("capturedImg");
        //String answer = (String)getIntent().getExtras().get("answer");
        String result = (String)getIntent().getExtras().get("result");


        if(img.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());

            iv.setImageBitmap(myBitmap);
            answerTV.setText(result);

        }
    }

    public void toAlphaSelect(View v)
    {
        Intent toAlphaSelect = new Intent(ResultActivity.this, SelectWordActivity.class);
        ResultActivity.this.startActivity(toAlphaSelect);
    }


    public void toNumSelect(View v)
    {
        Intent toNumSelect = new Intent(ResultActivity.this, SelectNumActivity.class);
        ResultActivity.this.startActivity(toNumSelect);
    }
}
