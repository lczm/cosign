package sg.edu.np.cosign.ui.statistics;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.HashMap;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

    private HashMap<Integer, String> pageTitleHashMap = new HashMap<>();
    // Constructor
    public DemoCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
        pageTitleHashMap.put(1, "Object 1");
        pageTitleHashMap.put(2, "Object 2");
        pageTitleHashMap.put(3, "Object 3");
        pageTitleHashMap.put(4, "Object 4");
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new DemoObjectFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
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

