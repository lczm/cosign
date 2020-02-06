package sg.edu.np.cosign.ui.favourite;

import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sg.edu.np.cosign.Classes.Constants;
import sg.edu.np.cosign.R;
import sg.edu.np.cosign.ui.ItemAdapter;
import sg.edu.np.cosign.ui.home.LearnSign;
import sg.edu.np.cosign.ui.home.SelectWordActivity;
import sg.edu.np.cosign.ui.statistics.FavouriteAdapter;

public class FavouriteFragment extends Fragment implements ItemAdapter.ItemClickListener{

    private FavouriteViewModel favouriteViewModel;
    private Constants constants = new Constants();
    SharedPreferences prefs;
    FavouriteAdapter adapter;

    public ArrayList<String> allAlpha = new ArrayList<>();
    public ArrayList<String> allNumber = new ArrayList<>();
    ArrayList<Integer> allFavId = new ArrayList<>();
    ArrayList<String> allFav = new ArrayList<>();
    public ArrayList<Integer> disabledAlphaPos = new ArrayList<>();
    //public ArrayList<String> getAlpha = new ArrayList<>();
    //public ArrayList<String> getnumber = new ArrayList<>();

    RecyclerView favRV;
    RecyclerView favNumRV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favouriteViewModel =
                ViewModelProviders.of(this).get(FavouriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        final TextView textView = root.findViewById(R.id.text_favourite);
        favouriteViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        prefs = getContext().getSharedPreferences("userData", 0);
        String email = prefs.getString("email", "No email");
        String password = prefs.getString("password", "No Password");

        // populate allAlpha list with all alpha
        allAlpha.add("A");
        allAlpha.add("B");
        allAlpha.add("C");
        allAlpha.add("D");
        allAlpha.add("E");
        allAlpha.add("F");
        allAlpha.add("G");
        allAlpha.add("H");
        allAlpha.add("I");
        allAlpha.add("J");
        allAlpha.add("K");
        allAlpha.add("L");
        allAlpha.add("M");
        allAlpha.add("N");
        allAlpha.add("O");
        allAlpha.add("P");
        allAlpha.add("Q");
        allAlpha.add("R");
        allAlpha.add("S");
        allAlpha.add("T");
        allAlpha.add("U");
        allAlpha.add("V");
        allAlpha.add("W");
        allAlpha.add("X");
        allAlpha.add("Y");
        allAlpha.add("Z");

        //populating allNumbers with numbers
        allNumber.add("One");
        allNumber.add("Two");
        allNumber.add("Three");
        allNumber.add("Four");
        allNumber.add("Five");
        allNumber.add("Six");
        allNumber.add("Seven");
        allNumber.add("Eight");
        allNumber.add("Nine");


        disabledAlphaPos.add(9);
        disabledAlphaPos.add(25);

        allFavId = getFavourite(email, password);
        for (int i = 0; i < allFavId.size(); i++)
        {
            String value = constants.reverseSignMapping.get(allFavId.get(i));
            allFav.add(value);
        }
        allNumber.retainAll(allFav);
        allAlpha.retainAll(allFav);

        //Alphabet recyclerView
        favRV = (RecyclerView) root.findViewById(R.id.favAlphaRV);
        favRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        favRV.setLayoutManager(layoutManager);
        adapter = new FavouriteAdapter(this.getContext(), allAlpha, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(favRV.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        favRV.addItemDecoration(dividerItemDecoration);
        favRV.setAdapter(adapter);

        //number recyclerView
        favNumRV = (RecyclerView) root.findViewById(R.id.favNumberRV);
        favNumRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerNumber = new LinearLayoutManager(this.getContext());
        favNumRV.setLayoutManager(layoutManagerNumber);
        adapter = new FavouriteAdapter(this.getContext(), allNumber, true);
        DividerItemDecoration dividerItemDecorationNumber = new DividerItemDecoration(favNumRV.getContext(), ((LinearLayoutManager) layoutManagerNumber).getOrientation());
        favNumRV.addItemDecoration(dividerItemDecoration);
        favNumRV.setAdapter(adapter);




        return root;
    }

    // Note : Use this function to get the ids of all the favourites
    // You can use reverseSignMapping in Constants to get the names of the ids
    private ArrayList<Integer> getFavourite(String email, String password) {
        ArrayList<Integer> returnFavouriteList = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.serverIP + Constants.databasePort + "/profile")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                Log.d("DEBUG", "Successful");
                String responseBody = response.body().string();
                try {
                    JSONObject responseJson = new JSONObject(responseBody);
                    JSONObject bookmarkJson = (JSONObject)responseJson.get("bookmarks");
                    Iterator<String> keys = bookmarkJson.keys();
                    while(keys.hasNext()) {
                        Integer tempInteger = Integer.parseInt(keys.next());
                        returnFavouriteList.add(tempInteger);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return returnFavouriteList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Un-Successful");
        return null;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        if (!disabledAlphaPos.contains(position)){
            Intent in = new Intent(this.getActivity(),
                    LearnSign.class);
            in.putExtra("pos", "" + position);
            in.putExtra("wordOrNum", "word");
            in.putExtra("data", allAlpha);
            startActivity(in);
        }
    }
}