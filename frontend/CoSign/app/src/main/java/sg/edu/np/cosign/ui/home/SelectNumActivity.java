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

public class SelectNumActivity extends AppCompatActivity {

    ArrayList<String> data = new ArrayList<>();
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_num);

        //populating ListView
        data=new ArrayList<>();
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");
        data.add("9");
        data.add("10");
        data.add("11");
        data.add("12");
        data.add("13");
        data.add("14");
        data.add("15");
        data.add("16");
        data.add("17");
        data.add("18");
        data.add("19");
        data.add("20");

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        data);

        ListView lv = findViewById(R.id.NumListView);
        lv.setAdapter(itemsAdapter);
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent in = new Intent(SelectNumActivity.this,
                                LearnSign.class);
                        in.putExtra("rowid", "" + position);
                        startActivity(in);
                    }
                }
        );
    }
}
