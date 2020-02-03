package sg.edu.np.cosign.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sg.edu.np.cosign.Classes.Constants;
import sg.edu.np.cosign.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private boolean something = false;
    private SharedPreferences prefs;
    private Constants constants = new Constants();
    private ArrayList<Integer> favourites = new ArrayList<Integer>();

    // data is passed into the constructor
    public ItemAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        prefs = context.getSharedPreferences("userData", 0);
        String email = prefs.getString("email", "No email");
        String password = prefs.getString("password", "No Password");
        favourites = getFavourite(email, password);
        Log.d("DEBUG", favourites.toString());
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.itemTV.setText(animal);
        holder.positionTV.setText(Integer.toString(position + 1));
        for (int i = 0; i < favourites.size(); i++) {
            if (favourites.get(i) == position + 1) {
                holder.favImgBtn.setBackgroundResource(R.drawable.red_heart);
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTV;
        TextView positionTV;
        ImageButton favImgBtn;

        ViewHolder(View itemView) {
            super(itemView);
            positionTV = itemView.findViewById(R.id.rowNum);
            itemTV = itemView.findViewById(R.id.learnItem);
            favImgBtn = itemView.findViewById(R.id.favImageBtn);
            favImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (something)
                    {
                        v.setBackgroundResource(R.drawable.black_heart);
                        Log.d("DEBUG", "Removing from Favourites");
                        String email = prefs.getString("email", "No email");
                        String password = prefs.getString("password", "No Password");
                        // Log.d("DEBUG", "Email is : " + email);
                        // Log.d("DEBUG", "Password is : " + password);
                        // Log.d("DEBUG", "Item Name is : " + itemTV.getText().toString());
                        // Log.d("DEBUG", "From constants : " + constants.signMapping.get(itemTV.getText().toString()));
                        postFavouriteToggle(constants.signMapping.get(itemTV.getText().toString()), email, password);
                        something = false;
                    } else {
                        v.setBackgroundResource(R.drawable.red_heart);
                        Log.d("DEBUG", "Adding to Favourites");
                        String email = prefs.getString("email", "No email");
                        String password = prefs.getString("password", "No Password");
                        postFavouriteToggle(constants.signMapping.get(itemTV.getText().toString()), email, password);
                        something = true;
                    }
                }
            });
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // POST method
    private boolean postFavouriteToggle(Integer sign_id, String email, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("sign_id", sign_id);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.serverIP + Constants.databasePort + "/bookmark")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                Log.d("DEBUG", "Successful");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

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
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Un-Successful");
        return null;
    }
}
