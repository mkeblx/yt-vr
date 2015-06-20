package com.navigatevr.ytvr;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeFailureRecoveryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.playerview);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
        boolean wasRestored) {
      if (!wasRestored) {
        player.cueVideo("wKJ9KzGQq0w");
      }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
      return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}
