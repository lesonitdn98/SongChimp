package tech.sonle.songchimp.ui.main.home.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.sonle.songchimp.R;
import tech.sonle.songchimp.data.model.Song;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private List<Song> mData;
    private ItemSongClick mItemSongClick;

    public PopularAdapter(List<Song> mData) {
        this.mData = mData;
    }

    public void setItemSongClick(ItemSongClick mItemSongClick) {
        this.mItemSongClick = mItemSongClick;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Song data = mData.get(position);
        holder.initData(data, mItemSongClick);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void initData(Song data, ItemSongClick itemSongClick) {
            if (data != null) {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                Uri uri = Uri.parse("android.resource://" + itemView.getContext().getPackageName() + "/" + data.getId());
                mmr.setDataSource(itemView.getContext(), uri);
                byte[] dataSong = mmr.getEmbeddedPicture();
                Bitmap bitmap = null;
                if (dataSong != null) {
                    bitmap = BitmapFactory.decodeByteArray(dataSong, 0, dataSong.length);
                }
                Glide.with(itemView.getContext()).load(bitmap).placeholder(R.drawable.music_default).into((CircleImageView) itemView.findViewById(R.id.songThumbnail));

                ((TextView) itemView.findViewById(R.id.songName)).setText(data.getName());
                itemView.setOnClickListener(v->itemSongClick.onClick(data));
            }
        }
    }

    public interface ItemSongClick {
        void onClick(Song song);
    }
}
