package tech.sonle.songchimp.ui.play.playSong;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import tech.sonle.songchimp.R;
import tech.sonle.songchimp.data.model.Song;
import tech.sonle.songchimp.ui.main.home.HomeFragment;
import tech.sonle.songchimp.ui.views.AppBarLayout;
import tech.sonle.songchimp.ui.views.MusicDisc;

public class SongPlayFragment extends Fragment {

    private Song mSong;
    private boolean isPlay = false;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert this.getArguments() != null;
        mSong = (Song) this.getArguments().getSerializable(HomeFragment.SONG);
        initView(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initView(View view) {
        AppBarLayout appBarLayout = view.findViewById(R.id.appBar);
        appBarLayout.setIconNavLeft(R.drawable.ic_back, v -> requireActivity().finish());
        appBarLayout.setIconNavRight(R.drawable.ic_menu_line, v -> {
        });
        if (mSong != null) {
            appBarLayout.setTitle(mSong.getName());
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + mSong.getId());
            mmr.setDataSource(requireContext(), uri);
            byte[] dataSong = mmr.getEmbeddedPicture();
            Bitmap bitmap = null;
            if (dataSong != null) {
                bitmap = BitmapFactory.decodeByteArray(dataSong, 0, dataSong.length);
            }
            ((MusicDisc) view.findViewById(R.id.musicDisc)).setSongThumbnail(bitmap);
        }

        (view.findViewById(R.id.layoutPlay)).setOnClickListener(v -> {
            if (isPlay) {
                ((ImageView) view.findViewById(R.id.iconPlay)).setImageDrawable(requireContext().getDrawable(R.drawable.ic_pause));
                isPlay = false;
            }
            else {
                ((ImageView) view.findViewById(R.id.iconPlay)).setImageDrawable(requireContext().getDrawable(R.drawable.ic_play));
                isPlay = true;
            }
        });
    }
}
