package com.navigatevr.ytvr;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRCameraRig;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRScript;
import org.gearvrf.GVRTexture;
import org.gearvrf.animation.GVRAnimationEngine;

import android.graphics.Color;

import com.google.android.youtube.player.YouTubePlayerView;

public class MainScript extends GVRScript {

    private static final String TAG = "MainScript";

    private GVRAnimationEngine mAnimationEngine;

    private MainActivity mActivity;
    private GVRContext mContext;

    private GVRScene mScene;
    private GVRCameraRig mRig;

    private GVRSceneObject mContainer;

    private YouTubeSceneObject ytObject;

    MainScript(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onInit(GVRContext gvrContext) {
        mContext = gvrContext;

        mAnimationEngine = gvrContext.getAnimationEngine();

        mScene = gvrContext.getNextMainScene();
        mRig = mScene.getMainCameraRig();

        mRig.getLeftCamera().setBackgroundColor(Color.DKGRAY);
        mRig.getRightCamera().setBackgroundColor(Color.DKGRAY);

        mContainer = new GVRSceneObject(gvrContext);
        mScene.addSceneObject(mContainer);


        GVRTexture texture = mContext.loadTexture(
                new GVRAndroidResource(mContext, R.raw.youtube_icon ));
        GVRSceneObject testObject = new GVRSceneObject(mContext, 1f, 1f, texture);
        testObject.getTransform().setPosition(0f, 0f, -3f);

        mContainer.addChildObject(testObject);

        //setupScene();
    }

    void setupScene() {
        YouTubePlayerView ytView = mActivity.getYouTubePlayerView();
        ytObject = new YouTubeSceneObject(mContext, ytView);

        GVRSceneObject playerContainer = new GVRSceneObject(mContext);
        playerContainer.addChildObject(ytObject);

        playerContainer.getTransform().setPosition(0f, 0f, -3f);

        mContainer.addChildObject(playerContainer);
    }

    @Override
    public void onStep() {

    }

    // for debug, hide
    //@Override
    /*public SplashMode getSplashMode() {
        return SplashMode.NONE;
    }*/


}
