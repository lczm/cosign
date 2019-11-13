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
        data.add("0         Zero");
        data.add("1         One");
        data.add("2         Two");
        data.add("3         Three");
        data.add("4         Four");
        data.add("5         Five");
        data.add("6         Six");
        data.add("7         Seven");
        data.add("8         Eight");
        data.add("9         Nine");
        data.add("10        Ten");
        data.add("11        Eleven");
        data.add("12        Twelve");
        data.add("13        Thirteen");
        data.add("14        Fourteen");
        data.add("15        Fifteen");
        data.add("16        Sixteen");
        data.add("17        Seventeen");
        data.add("18        Eighteen");
        data.add("19        Nineteen");
        data.add("20        Twenty");

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
