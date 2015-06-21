package com.navigatevr.ytvr;

import org.gearvrf.GVRActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends GVRActivity
    implements YouTubePlayer.OnInitializedListener {

    private static final String TAG = "MainActivity";

    private MainScript mScript;

    private YouTubePlayerFragment mYouTubePlayerFragment;

    private View layoutView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        layoutView = inflater.inflate(R.layout.playerview, null, false);

        //setContentView(layoutView);

        mYouTubePlayerFragment = getYouTubePlayerProvider();

        mYouTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);


        mScript = new MainScript(this);

        String deviceCfg = "gvr_note4.xml";

        setScript(mScript, deviceCfg);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("Q8TXgCzxEnw");
        }
    }

    protected YouTubePlayerFragment getYouTubePlayerProvider() {
        //return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);

        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_youtube_player, mYoutubePlayerFragment);
        fragmentTransaction.commit();*/

        YouTubePlayerFragment frag = new YouTubePlayerFragment();

        return frag;
    }

    public YouTubePlayerView getYouTubePlayerView() {
        YouTubePlayerView view = (YouTubePlayerView) mYouTubePlayerFragment.getView();
        return view;
    }

    //
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == RECOVERY_DIALOG_REQUEST) {
          // Retry initialization if user performed a recovery action
          getYouTubePlayerProvider().initialize(DeveloperKey.DEVELOPER_KEY, this);
      }
    }


}
