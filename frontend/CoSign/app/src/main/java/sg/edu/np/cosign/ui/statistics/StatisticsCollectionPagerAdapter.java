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
        // Amount of words learnt compared to goal
        pageTitleHashMap.put(1, "Learnt against Goal");
        // Amount of words learnt compared to the total amount of words
        pageTitleHashMap.put(2, "Learnt against Total");
        // Words learnt by date
        pageTitleHashMap.put(3, "Words learnt by date");
        // pageTitleHashMap.put(4, "USELESS CHART");
    }

    @Override
    public Fragment getItem(int i) {
        Bundle args = new Bundle();
        switch(i+1) {
            case 1:
                Fragment pieFragmentGoals = new StatisticsPieFragmentGoals();
                args.putInt(StatisticsPieFragmentGoals.ARG_OBJECT, i);
                pieFragmentGoals.setArguments(args);
                return pieFragmentGoals;
            case 2:
                Fragment pieFragmentAll = new StatisticsPieFragmentAll();
                args.putInt(StatisticsPieFragmentAll.ARG_OBJECT, i);
                pieFragmentAll.setArguments(args);
                return pieFragmentAll;
            case 3:
                Fragment barFragment = new StatisticsBarFragment();
                args.putInt(StatisticsBarFragment.ARG_OBJECT, i);
                barFragment.setArguments(args);
                return barFragment;
        }

        Log.d("DEBUG", "-- BAD -- It should not be reaching here : " + i);
        Fragment fragment = new StatisticsBarFragment();
        // Bundle args = new Bundle();

        // Our object is just an integer :-P
        args.putInt(StatisticsBarFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // return "OBJECT " + (position + 1);
        return pageTitleHashMap.get(position + 1);
    }
}

