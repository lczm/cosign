package sg.edu.np.cosign.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sg.edu.np.cosign.R;

public class HomeFragment extends Fragment {

    private sg.edu.np.cosign.ui.home.HomeViewModel homeViewModel;
/*
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private int[] images = { R.drawable.alphabet_finaledit, R.drawable.number_finaledit };

 */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(sg.edu.np.cosign.ui.home.HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*recyclerView = root.findViewById(R.id.CategoryRecyclerView);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("aaa", images.toString());
        //CategoryAdapter adapter = new CategoryAdapter(images);
        //  recyclerView.setAdapter(adapter);
         */

        return root;
    }
}