package sg.edu.np.cosign.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import sg.edu.np.cosign.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectWordActivity extends AppCompatActivity {

    ArrayList<String> data = new ArrayList<>();
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_word);

        //set TextView text
        txt = findViewById(R.id.AlphaTitle);
        txt.setText("Alphabets");

        //populating ListView
        data=new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        data.add("E");
        data.add("F");
        data.add("G");
        data.add("H");
        data.add("I");
        data.add("J");
        data.add("K");
        data.add("L");
        data.add("M");
        data.add("N");
        data.add("O");
        data.add("P");
        data.add("Q");
        data.add("R");
        data.add("S");
        data.add("T");
        data.add("U");
        data.add("V");
        data.add("W");
        data.add("X");
        data.add("Y");
        data.add("Z");

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        data);

        ListView lv = findViewById(R.id.AlphaListView);
        lv.setAdapter(itemsAdapter);
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent in = new Intent(SelectWordActivity.this,
                                LearnSign.class);
                        in.putExtra("rowid", "" + position);
                        startActivity(in);
                    }
                }
        );
    }
}
