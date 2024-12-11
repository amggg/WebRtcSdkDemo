package org.webrtc;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.projection.MediaProjection;
import android.util.Log;

import androidx.annotation.IntRange;

/**
 * @author admin
 */
public class ScreenCaptureAndroidOpt extends ScreenCapturerAndroid implements Runnable, SurfaceTexture.OnFrameAvailableListener {

    private int delayTime = 0;

    private volatile boolean isAvailable = false;

    /**
     * Constructs a new Screen Capturer.
     *
     * @param mediaProjectionPermissionResultData the result data of MediaProjection permission
     *                                            activity; the calling app must validate that result code is Activity.RESULT_OK before
     *                                            calling this method.
     * @param mediaProjectionCallback             MediaProjection callback to implement application specific
     *                                            logic in events such as when the user revokes a previously granted capture permission.
     **/
    public ScreenCaptureAndroidOpt(Intent mediaProjectionPermissionResultData, MediaProjection.Callback mediaProjectionCallback) {
        super(mediaProjectionPermissionResultData, mediaProjectionCallback);
    }


    public void setFrameSendListener(FrameSendEvents frameSendEvents) {
        if (this.surfaceTextureHelper != null) {
            this.surfaceTextureHelper.setFrameSendListener(frameSendEvents);
        }
    }

    @Override
    public synchronized void changeCaptureFormat(int width, int height, @IntRange(from = 1, to = Integer.MAX_VALUE) int frameRate) {
        super.changeCaptureFormat(width, height, frameRate);
        Log.i("changeCaptureFormat", "width:" + width + " height:" + height + " frameRate:" + frameRate);
        delayTime = 1000 / frameRate;
    }

    @Override
    public void run() {
        if (this.surfaceTextureHelper != null) {
            this.surfaceTextureHelper.hasPendingTexture = true;
            this.surfaceTextureHelper.tryDeliverTextureFrame();
            this.surfaceTextureHelper.handler.postDelayed(this, this.delayTime);
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (!isAvailable && this.surfaceTextureHelper != null) {
            Log.i("ArmScreenCaptureAndroid", "onFrameAvailable");
            this.surfaceTextureHelper.hasPendingTexture = true;
            this.surfaceTextureHelper.tryDeliverTextureFrame();
            this.surfaceTextureHelper.handler.postDelayed(this, this.delayTime);
            isAvailable = true;
        }
    }

    @Override
    public synchronized void startCapture(int width, int height, int ignoredFrameRate) {
        super.startCapture(width, height, ignoredFrameRate);
        if (surfaceTextureHelper == null) {
            return;
        }
        SurfaceTexture surfaceTexture = surfaceTextureHelper.getSurfaceTexture();
        surfaceTexture.setOnFrameAvailableListener(this, this.surfaceTextureHelper.handler);
        this.changeCaptureFormat(width, height, ignoredFrameRate);
        this.surfaceTextureHelper.handler.postDelayed(this, delayTime);
    }

    @Override
    public synchronized void stopCapture() {
        super.stopCapture();
        if (this.surfaceTextureHelper == null) {
            return;
        }
        this.surfaceTextureHelper.handler.removeCallbacks(this);
    }
}

