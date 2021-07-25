package tech.sonle.songchimp.ui.play;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tech.sonle.songchimp.R;
import tech.sonle.songchimp.data.model.Song;
import tech.sonle.songchimp.ui.main.home.HomeFragment;
import tech.sonle.songchimp.ui.play.playSong.SongPlayFragment;

public class PlayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Song song = (Song) getIntent().getSerializableExtra(HomeFragment.SONG);
        SongPlayFragment songPlayFragment = new SongPlayFragment();
        if (song != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(HomeFragment.SONG, song);
            songPlayFragment.setArguments(bundle);
        }

        getSupportFragmentManager().beginTransaction().add(R.id.contentView, songPlayFragment).commit();
    }
}
