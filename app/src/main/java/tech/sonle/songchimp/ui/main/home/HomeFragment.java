package tech.sonle.songchimp.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import tech.sonle.songchimp.R;
import tech.sonle.songchimp.data.model.Song;
import tech.sonle.songchimp.ui.main.home.adapter.DisMusicAdapter;
import tech.sonle.songchimp.ui.main.home.adapter.PlaylistAdapter;
import tech.sonle.songchimp.ui.main.home.adapter.PopularAdapter;
import tech.sonle.songchimp.ui.play.PlayActivity;
import tech.sonle.songchimp.ui.views.AppBarLayout;

public class HomeFragment extends Fragment {

    public static final String SONG = "Song";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        AppBarLayout mAppBar = view.findViewById(R.id.appBar);
        mAppBar.setIconNavLeft(R.drawable.ic_menu, v -> Toast.makeText(requireContext(), "Menu", Toast.LENGTH_SHORT).show());
        mAppBar.setIconNavRight(R.drawable.ic_search, v -> Toast.makeText(requireContext(), "Search", Toast.LENGTH_SHORT).show());

        ViewPager2 layoutDisMusic = view.findViewById(R.id.layoutDisMusic);
        layoutDisMusic.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        List<Integer> listBanner = new ArrayList<>();
        listBanner.add(R.drawable.banner_one);
        listBanner.add(R.drawable.banner_two);
        layoutDisMusic.setAdapter(new DisMusicAdapter(listBanner));
        ((DotsIndicator) view.findViewById(R.id.indicator)).setViewPager2(layoutDisMusic);

        RecyclerView rcvPlaylist = view.findViewById(R.id.rcvPlaylist);
        rcvPlaylist.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        rcvPlaylist.setAdapter(new PlaylistAdapter());

        RecyclerView rcvPopular = view.findViewById(R.id.rcvPopular);
        rcvPopular.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        List<Song> listSong = new ArrayList<>();
        listSong.add(new Song(R.raw.tho, "Thở"));
        listSong.add(new Song(R.raw.chilakhongcungnhau, "Chỉ là không cùng nhau"));
        listSong.add(new Song(R.raw.coaynoi, "Cô ấy nói"));
        listSong.add(new Song(R.raw.cochangtraivietlencay, "Có chàng trai viết lên cây"));
        listSong.add(new Song(R.raw.loiduongmat, "Lời đường mật"));
        listSong.add(new Song(R.raw.motphut, "1 Phút"));
        listSong.add(new Song(R.raw.nhungaydo, "Như ngày đó"));
        listSong.add(new Song(R.raw.rangkhon, "Răng khôn"));
        listSong.add(new Song(R.raw.saigondaulongqua, "Sài gòn đau lòng quá"));
        PopularAdapter popularAdapter = new PopularAdapter(listSong);
        popularAdapter.setItemSongClick(song -> {
            Intent intent = new Intent(requireActivity(), PlayActivity.class);
            intent.putExtra(SONG, song);
            startActivity(intent);
        });
        rcvPopular.setAdapter(popularAdapter);
    }
}
