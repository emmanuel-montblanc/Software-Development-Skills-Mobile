package emmanuelmontblanc.insalyon.fr.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private int[] img;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    GameAdapter(Context context, int[] img) {
        this.mInflater = LayoutInflater.from(context);
        this.img = img;
    }

    // inflates the cell layout from xml
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.caseImageView.setImageResource(img[position]);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return img.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView caseImageView;

        ViewHolder(View itemView) {
            super(itemView);
            caseImageView = itemView.findViewById(R.id.caseImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Allows us to define the onClick method in the parent activity
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}