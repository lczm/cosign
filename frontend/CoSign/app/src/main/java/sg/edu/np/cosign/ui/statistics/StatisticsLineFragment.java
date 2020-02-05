package sg.edu.np.cosign.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.np.cosign.R;

// Instances of this class are fragments representing a single
// object in our collection.
public class StatisticsLineFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View lineView = inflater.inflate(R.layout.fragment_line_charts_object, container, false);
        // Create lineChart by casting
        LineChart lineChart = (LineChart)lineView.findViewById(R.id.lineChart);
        // Create  lineDataset
        LineDataSet lineDataset1 = new LineDataSet(getLineData1(), "Insert Line Chart Label 1");
        lineDataset1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataset1.setColors(ColorTemplate.COLORFUL_COLORS);

        LineDataSet lineDataset2 = new LineDataSet(getLineData2(), "Insert Line Chart Label 2");
        lineDataset2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataset2.setColors(ColorTemplate.COLORFUL_COLORS);


        // Create a barData from the barDataset
        LineData lineData = new LineData();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // x-axis labels
        final String[] xAxisLabels = new String[]{"Jan", "Feb"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(xAxisLabels);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataset1);
        dataSets.add(lineDataset2);

        LineData data = new LineData(dataSets);

        // Setting the data of the barChart
        // Configuring the barChart
        lineChart.setData(data);
        // lineChart.setFitBars(true);
        lineChart.animateXY(800, 800);
        lineChart.invalidate();
        lineChart.setDoubleTapToZoomEnabled(false);
        return lineView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*
        Bundle args = getArguments();
        ((TextView) view.findViewById(android.R.id.text1))
                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
*/
    }

    private ArrayList getLineData1(){
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 50));
        entries.add(new Entry(2, 100));
        entries.add(new Entry(3, 150));
        entries.add(new Entry(4, 200));
        return entries;
    }

    private ArrayList getLineData2(){
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 30));
        entries.add(new Entry(2, 60));
        entries.add(new Entry(3, 90));
        entries.add(new Entry(4,120 ));
        return entries;
    }
}

