package tech.sonle.songchimp.ui.play.playSong;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import tech.sonle.songchimp.R;
import tech.sonle.songchimp.data.model.Song;
import tech.sonle.songchimp.service.PLaySongService;
import tech.sonle.songchimp.ui.main.home.HomeFragment;
import tech.sonle.songchimp.ui.play.PlayActivity;
import tech.sonle.songchimp.ui.views.AppBarLayout;
import tech.sonle.songchimp.ui.views.MusicDisc;

public class SongPlayFragment extends Fragment {

    private Song mSong;
    private boolean isPlay = false;
    private MusicDisc mMusicDisc;
    private PLaySongService mPlaySongService;
    private boolean mIsBind = false;
    private ImageView icPlay;
    private SeekBar seekBarSong;

    private TextView mCurrentTime, mTotalTime;

    private final ServiceConnection mPlaySongServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PLaySongService.PLaySongBinder binder = (PLaySongService.PLaySongBinder) service;
            mPlaySongService = binder.getService();
            mIsBind = true;
            mPlaySongService.setSongController(new PLaySongService.SongController() {
                @Override
                public void onCurrentTime(String time) {
                    mCurrentTime.setText(time);
                }

                @Override
                public void onTotalTime(String time) {
                    mTotalTime.setText(time);
                }

                @Override
                public void updateSeekBar(int process) {
                    seekBarSong.setProgress(process);
                }

                @Override
                public void onStopSong() {
                    isPlay = false;
                    handlePlaySong();
                }
            });
            mPlaySongService.totalTime();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBind = false;
        }
    };

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

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(requireActivity(), PLaySongService.class);
        requireActivity().bindService(intent, mPlaySongServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mIsBind) {
            requireActivity().unbindService(mPlaySongServiceConnection);
            mIsBind = false;
        }
    }

    private void initView(View view) {
        AppBarLayout appBarLayout = view.findViewById(R.id.appBar);
        mCurrentTime = view.findViewById(R.id.currentTime);
        mTotalTime = view.findViewById(R.id.totalTime);
        icPlay = view.findViewById(R.id.iconPlay);
        seekBarSong = view.findViewById(R.id.seekBarSong);
        seekBarSong.setProgress(0);
        appBarLayout.setIconNavLeft(R.drawable.ic_back, v -> requireActivity().finish());
        appBarLayout.setIconNavRight(R.drawable.ic_menu_line, v -> {
        });
        mMusicDisc = view.findViewById(R.id.musicDisc);
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
            mMusicDisc.setSongThumbnail(bitmap);

            mCurrentTime.setText(R.string.time_defaut);

            Intent intent = new Intent(requireActivity(), PLaySongService.class);
            intent.putExtra(HomeFragment.SONG, mSong);
            requireActivity().startService(intent);

            isPlay = true;
            handlePlaySong();
        }
        (view.findViewById(R.id.layoutPlay)).setOnClickListener(v -> handlePlaySong());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void handlePlaySong() {
        if (isPlay) {
            if (mPlaySongService != null)
                mPlaySongService.onContinuesSong();
            icPlay.setImageDrawable(requireContext().getDrawable(R.drawable.ic_pause));
            isPlay = false;
            mMusicDisc.startRotateAnimation();
        } else {
            if (mPlaySongService != null)
                mPlaySongService.onPauseSong();
            icPlay.setImageDrawable(requireContext().getDrawable(R.drawable.ic_play));
            isPlay = true;
            mMusicDisc.stopRotateAnimation();
        }
    }
}
