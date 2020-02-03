package sg.edu.np.cosign.ui.statistics;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.HashMap;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class StatisticsCollectionPagerAdapter extends FragmentStatePagerAdapter {

    private HashMap<Integer, String> pageTitleHashMap = new HashMap<>();
    // Constructor
    public StatisticsCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
        pageTitleHashMap.put(1, "Bar Chart");
        pageTitleHashMap.put(2, "Line Chart");
        pageTitleHashMap.put(3, "Pie Chart");
        pageTitleHashMap.put(4, "ANOTHER PIE CHART");
    }

    @Override
    public Fragment getItem(int i) {
        Bundle args = new Bundle();
        switch(i+1) {
            case 1:
                Fragment barFragment = new StatisticsBarFragment();
                args.putInt(StatisticsBarFragment.ARG_OBJECT, i);
                barFragment.setArguments(args);
                return barFragment;
            case 2:
                Fragment lineFragment = new StatisticsLineFragment();
                args.putInt(StatisticsLineFragment.ARG_OBJECT, i);
                lineFragment.setArguments(args);
                return lineFragment;
            case 3:
                Fragment pieFragment = new StatisticsPieFragment();
                args.putInt(StatisticsPieFragment.ARG_OBJECT, i);
                pieFragment.setArguments(args);
                return pieFragment;
            case 4:
                Fragment pie2Fragment = new StatisticsPieFragment();
                args.putInt(StatisticsPieFragment.ARG_OBJECT, i);
                pie2Fragment.setArguments(args);
                return pie2Fragment;
        }

        Log.d("DEBUG", "It should not be reaching here" + i);
        Fragment fragment = new StatisticsBarFragment();
        // Bundle args = new Bundle();

        // Our object is just an integer :-P
        args.putInt(StatisticsBarFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // return "OBJECT " + (position + 1);
        return pageTitleHashMap.get(position + 1);
    }
}

