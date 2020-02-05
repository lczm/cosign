package sg.edu.np.cosign.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import sg.edu.np.cosign.Classes.Constants;
import sg.edu.np.cosign.R;

// Instances of this class are fragments representing a single
// object in our collection.
public class StatisticsPieFragmentGoals extends Fragment {
    public static final String ARG_OBJECT = "object";
    private Constants constants = new Constants();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View pieView = inflater.inflate(R.layout.fragment_pie_charts_object, container, false);
        PieChart pieChart = pieView.findViewById(R.id.pieChart);
        PieDataSet set = new PieDataSet(getData(), "Results");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(set);

        pieChart.setData(data);
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
        // Note, "Green", "Yellow"... are not the colours of the individual pies
        // They are just labels, misleading but to be changed in the future
        entries.add(new PieEntry(12.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));
        return entries;
    }

}

