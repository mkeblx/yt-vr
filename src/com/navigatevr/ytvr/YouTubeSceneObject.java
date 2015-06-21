package com.navigatevr.ytvr;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRDrawFrameListener;
import org.gearvrf.GVRExternalTexture;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMaterial.GVRShaderType;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;

import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.webkit.WebView;

import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeSceneObject extends GVRSceneObject
    implements GVRDrawFrameListener{

    private static final String TAG = "YouTubeSceneObject";

    private int REFRESH_INTERVAL = 30; // frames
    private int mCount = 0;
    private boolean paused = false;

    private final Surface mSurface;
    private final SurfaceTexture mSurfaceTexture;
    private final YouTubePlayerView playerView;

    public YouTubeSceneObject(GVRContext gvrContext, YouTubePlayerView playerView) {
        super(gvrContext);

        this.playerView = playerView;

        GVRTexture texture = new GVRExternalTexture(gvrContext);
        GVRMaterial material = new GVRMaterial(gvrContext, GVRShaderType.OES.ID);
        material.setMainTexture(texture);
        getRenderData().setMaterial(material);

        mSurfaceTexture = new SurfaceTexture(texture.getId());
        mSurface = new Surface(mSurfaceTexture);
        mSurfaceTexture.setDefaultBufferSize(
              playerView.getWidth(),
              playerView.getHeight());
    }

    @Override
    public void onDrawFrame(float frameTime) {
        if (paused)
            return;

        if (++mCount > REFRESH_INTERVAL) {
            refresh();
            mCount = 0;
        }
    }

    /** Draws the {@link WebView} onto {@link #mSurfaceTexture} */
    public void refresh() {
        try {
            Canvas canvas = mSurface.lockCanvas(null);
            playerView.draw(canvas);
            mSurface.unlockCanvasAndPost(canvas);
        } catch (Surface.OutOfResourcesException t) {
            Log.e(TAG, "lockCanvas failed");
        }
        mSurfaceTexture.updateTexImage();
    }

}
