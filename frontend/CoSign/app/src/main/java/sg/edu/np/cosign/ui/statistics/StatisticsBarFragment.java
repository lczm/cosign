package sg.edu.np.cosign.ui.statistics;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import sg.edu.np.cosign.Classes.Constants;
import sg.edu.np.cosign.R;

// Instances of this class are fragments representing a single
// object in our collection.
public class StatisticsBarFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public Constants constants = new Constants();
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View barView = inflater.inflate(R.layout.fragment_bar_charts_object, container, false);
        // Create barChart by casting
        BarChart barChart = (BarChart)barView.findViewById(R.id.barChart);
        // Create barDataset
        BarDataSet barDataset = new BarDataSet(getData(), "Insert Bar Chart Label");
        // Set the aesthetics of the barChart
        barDataset.setBarBorderWidth(0.9f);
        barDataset.setColors(ColorTemplate.COLORFUL_COLORS);

        // Create a barData from the barDataset
        BarData barData = new BarData(barDataset);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // x-axis labels
        final String[] xAxisLabels = new String[]{"Bookmarks", "Learnt"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(xAxisLabels);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        // Setting the data of the barChart
        // Configuring the barChart
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(800, 800);
        barChart.setVisibleYRange(0, 20, YAxis.AxisDependency.LEFT);
        barChart.invalidate();
        barChart.setDoubleTapToZoomEnabled(false);
        return barView;
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
        ArrayList<BarEntry> entries = new ArrayList<>();
        prefs = getContext().getSharedPreferences("userData", 0);
        // String username = prefs.getString("username", "No Username");
        String email = prefs.getString("email", "No email");
        String password = prefs.getString("password", "No Password");
        ArrayList<Integer> favourites = constants.getFavourite(email, password);
        ArrayList<Integer> learntIds = constants.getLearntIds(email, password);

        entries.add(new BarEntry(0f, favourites.size()));
        entries.add(new BarEntry(1f, learntIds.size()));
        return entries;
    }
}

