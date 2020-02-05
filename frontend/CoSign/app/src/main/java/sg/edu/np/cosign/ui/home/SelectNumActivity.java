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

public class SelectNumActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {

    ArrayList<String> data = new ArrayList<>();
    TextView txt;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_num);

        //populating ListView
        data=new ArrayList<>();
        data.add("Zero");
        data.add("One");
        data.add("Two");
        data.add("Three");
        data.add("Four");
        data.add("Five");
        data.add("Six");
        data.add("Seven");
        data.add("Eight");
        data.add("Nine");
        data.add("Ten");
        data.add("Eleven");
        data.add("Twelve");
        data.add("Thirteen");
        data.add("Fourteen");
        data.add("Fifteen");
        data.add("Sixteen");
        data.add("Seventeen");
        data.add("Eighteen");
        data.add("Nineteen");
        data.add("Twenty");

        RecyclerView rv = findViewById(R.id.numRV);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(this, data, "Number");
        adapter.setClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position)
    {
        Intent in = new Intent(SelectNumActivity.this,
                LearnSign.class);
        in.putExtra("pos", "" + position);
        in.putExtra("wordOrNum", "num");
        in.putExtra("data", data);
        startActivity(in);
    }
}
