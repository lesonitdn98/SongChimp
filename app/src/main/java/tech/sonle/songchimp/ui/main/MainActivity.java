package tech.sonle.songchimp.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import tech.sonle.songchimp.R;
import tech.sonle.songchimp.ui.main.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.contentView, new HomeFragment()).commit();
    }
}