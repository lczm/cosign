package sg.edu.np.cosign.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sg.edu.np.cosign.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImageViewHolder> {

    private int[] images;

    public CategoryAdapter(int[] images)
    {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Log.d("AAAAAAAA", images.toString());
        int image_id = images[position];
        Log.d("AAAAAAAA", Integer.toString(image_id));
        holder.CategoryImage.setImageResource(image_id);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView CategoryImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
