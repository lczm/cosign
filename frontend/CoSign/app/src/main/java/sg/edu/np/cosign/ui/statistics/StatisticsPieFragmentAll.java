package sg.edu.np.cosign.ui.statistics;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import sg.edu.np.cosign.Classes.Constants;
import sg.edu.np.cosign.R;

// Instances of this class are fragments representing a single
// object in our collection.
public class StatisticsPieFragmentAll extends Fragment {
    public static final String ARG_OBJECT = "object";
    private Constants constants = new Constants();
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View pieView = inflater.inflate(R.layout.fragment_pie_charts_object, container, false);
        PieChart pieChart = pieView.findViewById(R.id.pieChart);
        PieDataSet set = new PieDataSet(getData(), "Current learnt against total words");
        set.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(set);

        pieChart.setData(data);
        Description d = new Description();
        d.setText("");
        pieChart.setDescription(d);
        pieChart.invalidate();
        return pieView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*
        Bundle args = getArguments();
        ((TextView) view.findViewById(android.R.id.text1))
                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
*/
    }

    private ArrayList getData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        prefs = getContext().getSharedPreferences("userData", 0);
        String email = prefs.getString("email", "No email");
        String password = prefs.getString("password", "No Password");
        ArrayList<Integer> learntIds = constants.getLearntIds(email, password);

        entries.add(new PieEntry(learntIds.size(), "Learnt"));
        entries.add(new PieEntry(constants.signNames.size() - learntIds.size(), "Total"));
        return entries;
    }

}

