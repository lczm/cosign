package sg.edu.np.cosign.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import sg.edu.np.cosign.R;

public class HomeFragment extends Fragment {

    private sg.edu.np.cosign.ui.home.HomeViewModel homeViewModel;

    private RecyclerView recyclerView;

    private int[] images = {R.drawable.alphabet, R.drawable.number};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(sg.edu.np.cosign.ui.home.HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.CategoryRecyclerView);

        return root;
    }
}