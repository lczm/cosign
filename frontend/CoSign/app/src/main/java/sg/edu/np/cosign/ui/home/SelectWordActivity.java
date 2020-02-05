package sg.edu.np.cosign.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sg.edu.np.cosign.R;
import sg.edu.np.cosign.ui.ItemAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectWordActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {

    public ArrayList<String> data = new ArrayList<>();
    TextView txt;
    ItemAdapter adapter;

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

        RecyclerView rv = findViewById(R.id.wordRV);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(this, data, "Word");
        adapter.setClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position)
    {
        Intent in = new Intent(SelectWordActivity.this,
                LearnSign.class);
        in.putExtra("pos", "" + position);
        in.putExtra("wordOrNum", "word");
        in.putExtra("data", data);
        startActivity(in);
    }
}
