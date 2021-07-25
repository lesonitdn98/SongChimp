package tech.sonle.songchimp.ui.main.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import tech.sonle.songchimp.R;

public class DisMusicAdapter extends RecyclerView.Adapter<DisMusicAdapter.ViewHolder> {

    private List<Integer> mData;

    public DisMusicAdapter(List<Integer> mData) {
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dis_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.initData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void initData(int data) {
            Glide.with(itemView.getContext()).load(data).into((ImageView) itemView.findViewById(R.id.imvContent));
        }
    }
}
