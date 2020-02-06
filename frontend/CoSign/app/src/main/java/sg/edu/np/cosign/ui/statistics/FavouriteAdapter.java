package sg.edu.np.cosign.ui.statistics;

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

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.cosign.Classes.Constants;
import sg.edu.np.cosign.R;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private FavItemClickListener mClickListener;
    private SharedPreferences prefs;
    private Constants constants = new Constants();
    private ArrayList<Integer> favourites = new ArrayList<>();
    private ArrayList<Integer> sign_ids = new ArrayList<>();
    private Context context;
    private boolean numbersOrNot;

    // data is passed into the constructor
    public FavouriteAdapter(Context context, List<String> data, boolean numbers) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.numbersOrNot = numbers;
        prefs = context.getSharedPreferences("userData", 0);
        String email = prefs.getString("email", "No email");
        String password = prefs.getString("password", "No Password");
        favourites = constants.getFavourite(email, password);
        for (int i = 0; i < mData.size(); i++)
        {
            Integer convertedId = constants.signMapping.get(mData.get(i));
            sign_ids.add(convertedId);
        }

    }

    // inflates the row layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.itemTV.setText(animal);
        if (!numbersOrNot) {
            holder.positionTV.setText(Integer.toString(position + 1));
            for (int i = 0; i < favourites.size(); i++) {
                if (sign_ids.contains(favourites.get(i))) {
                    holder.favImgBtn.setImageResource(R.drawable.red_heart);
                    break;
                }
                else
                {
                    holder.favImgBtn.setImageResource(R.drawable.black_heart);
                }
            }
        }
        else {
            holder.positionTV.setText(Integer.toString(position + 1 + constants.jumpNumber));
            for (int i = 0; i < favourites.size(); i++) {
                if (sign_ids.contains(favourites.get(i))) {
                    holder.favImgBtn.setImageResource(R.drawable.red_heart);
                    break;
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
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTV;
        TextView positionTV;
        ImageButton favImgBtn;

        MyViewHolder(View itemView) {
            super(itemView);
            positionTV = itemView.findViewById(R.id.rowNum);
            itemTV = itemView.findViewById(R.id.learnItem);
            favImgBtn = itemView.findViewById(R.id.favImageBtn);
            favImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!numbersOrNot) {
                        if (favourites.contains(getAdapterPosition() + 1)) {
                            // Logging
                            Log.d("DEBUG", "Removing from Favourites");
                            favImgBtn.setImageResource(R.drawable.black_heart);
                            String email = prefs.getString("email", "No email");
                            String password = prefs.getString("password", "No Password");
                            boolean response = constants.postFavouriteToggle(constants.signMapping.get(itemTV.getText().toString()), email, password);
                            if (response == true) {
                                Toast.makeText(context, "Removing from favourites : " + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                            } else {
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
                            } else {
                                Toast.makeText(context, "Something has gone wrong! Please contact the administrator." + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                            favourites.add(getAdapterPosition() + 1);
                        }
                    } else {
                        if (favourites.contains(getAdapterPosition() + 1 + constants.jumpNumber)) {
                            // Logging
                            Log.d("DEBUG", "Removing from Favourites");
                            favImgBtn.setImageResource(R.drawable.black_heart);
                            String email = prefs.getString("email", "No email");
                            String password = prefs.getString("password", "No Password");
                            boolean response = constants.postFavouriteToggle(constants.signMapping.get(itemTV.getText().toString()), email, password);
                            if (response == true) {
                                Toast.makeText(context, "Removing from favourites : " + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Something has gone wrong! Please contact the administrator." + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                            favourites.remove(Integer.valueOf(getAdapterPosition() + 1 + constants.jumpNumber));
                        } else {
                            // Logging
                            Log.d("DEBUG", "Adding to Favourites ");
                            favImgBtn.setImageResource(R.drawable.red_heart);
                            String email = prefs.getString("email", "No email");
                            String password = prefs.getString("password", "No Password");
                            boolean response = constants.postFavouriteToggle(constants.signMapping.get(itemTV.getText().toString()), email, password);
                            if (response == true) {
                                Toast.makeText(context, "Adding to favourites : " + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Something has gone wrong! Please contact the administrator." + itemTV.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                            favourites.add(getAdapterPosition() + 1 + constants.jumpNumber);
                        }
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
    void setClickListener(FavItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface FavItemClickListener {
        void onItemClick(View view, int position);
    }
}
