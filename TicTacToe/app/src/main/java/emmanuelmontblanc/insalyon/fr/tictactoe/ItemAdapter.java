package emmanuelmontblanc.insalyon.fr.tictactoe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
//    private List<String> data;
    private int[] img;

    public ItemAdapter(int[] img){
        this.img = img;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.caseImageView.setImageResource(this.img[position]);
    }

    @Override
    public int getItemCount() {
        return this.img.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView caseImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.caseImageView = view.findViewById(R.id.caseImageView);
        }

        @Override
        public void onClick(View view) {
            this.caseImageView.setImageResource(R.drawable.cross);
        }
    }
}