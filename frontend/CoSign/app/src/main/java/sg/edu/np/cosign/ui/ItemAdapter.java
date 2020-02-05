package sg.edu.np.cosign.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    // data for recyclerview e.g. A B C D E F G or liek 1 2 3 4 5 6
    private List<String> mData;
    // dunnid to care
    private LayoutInflater mInflater;
    // when u click smth
    private ItemClickListener mClickListener;
    // this is like a mini database we using it to store like email n password in it so we cn access from other activity
    private SharedPreferences prefs;
    // zeming stuff
    private Constants constants = new Constants();
    // what u favourited
    private ArrayList<Integer> favourites = new ArrayList<>();
    private String activityName;
    private Context context;

    // data is passed into the constructor
    public ItemAdapter(Context context, List<String> data, String activityName) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.activityName = activityName;
        this.context = context;
        prefs = context.getSharedPreferences("userData", 0);
        String email = prefs.getString("email", "No email");
        String password = prefs.getString("password", "No Password");
        favourites = constants.getFavourite(email, password);
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
        if (activityName == "Word") {
            holder.positionTV.setText(Integer.toString(position + 1));
            for (int i = 0; i < favourites.size(); i++) {
                if (favourites.get(i) == position + 1) {
                    holder.favImgBtn.setImageResource(R.drawable.red_heart);
                }
                else
                {
                    holder.favImgBtn.setImageResource(R.drawable.black_heart);
                }
            }
        }
        else if (activityName == "Number") {
            holder.positionTV.setText(Integer.toString(position + 1 + constants.jumpNumber));
            for (int i = 0; i < favourites.size(); i++) {
                if (favourites.get(i) == position + 1 + constants.jumpNumber) {
                    holder.favImgBtn.setImageResource(R.drawable.red_heart);
                }
                else
                {
                    holder.favImgBtn.setImageResource(R.drawable.black_heart);
                }
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
                    if (favourites.contains(getAdapterPosition() + 1))
                    {
                        // Logging
                        Log.d("DEBUG", "Removing from Favourites");
                        favImgBtn.setImageResource(R.drawable.black_heart);
                        String email = prefs.getString("email", "No email");
                        String password = prefs.getString("password", "No Password");
                        boolean response = constants.postFavouriteToggle(constants.signMapping.get(itemTV.getText().toString()), email, password);
                        if (response == true) {
                            Toast.makeText(context, "Removing from favourites : " + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(context, "Something has gone wrong! Please contact the administrator." + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                        favourites.remove(Integer.valueOf(getAdapterPosition() + 1));
                    } else {
                        // Logging
                        Log.d("DEBUG", "Adding to Favourites");
                        favImgBtn.setImageResource(R.drawable.red_heart);
                        String email = prefs.getString("email", "No email");
                        String password = prefs.getString("password", "No Password");
                        boolean response = constants.postFavouriteToggle(constants.signMapping.get(itemTV.getText().toString()), email, password);
                        if (response == true) {
                            Toast.makeText(context, "Adding to favourites : " + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(context, "Something has gone wrong! Please contact the administrator." + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                        favourites.add(getAdapterPosition() + 1);
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
}
