package com.hihonor.adsdk.demo.external.widget;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.hihonor.adsdk.base.api.CustomVideo;
import com.hihonor.adsdk.common.video.OnVideoPlayListener;
import com.hihonor.adsdk.demo.external.utils.LogUtil;

/**
 * 功能描述
 *
 * @since 2023-08-02
 */
public class CustomVideoView extends VideoView {
    private static final String TAG = "CustomVideoView";
    private CustomVideo mCustomVideo;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private OnVideoPlayListener mOnVideoPlayListener;

    private Uri mUri;

    private MediaPlayer mMediaPlayer;

    private boolean isPrepared;

    public CustomVideoView(Context context) {
        this(context, null);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnPreparedListener(mp -> {
            mMediaPlayer = mp;
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                }
            } catch (Exception e) {

            }
            mp.start();
            isPrepared = true;
            updateProgress();
            if (mCustomVideo != null) {
                mCustomVideo.reportVideoStart();
            }
            if (mOnVideoPlayListener != null) {
                mOnVideoPlayListener.onVideoStart();
            }
        });

        setOnCompletionListener(mp -> {
            isPrepared = false;
            if (mCustomVideo != null) {
                mCustomVideo.reportVideoEnd();
            }
            if (mOnVideoPlayListener != null) {
                mOnVideoPlayListener.onVideoEnd();
            }
        });

        setOnErrorListener((mp, what, extra) -> {
            isPrepared = false;
            if (mCustomVideo != null) {
                mCustomVideo.reportVideoError(what, "custom player error.");
            }
            if (mOnVideoPlayListener != null) {
                mOnVideoPlayListener.onVideoError(what, "custom error.");
            }
            return false;
        });
        setZOrderOnTop(true);
        setZOrderMediaOverlay(true);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (isPrepared) {
                resume();
            } else{
                setVideoURI(mUri);
            }
        } else {
            pause();
        }
    }

    private void updateProgress() {
        if (getVisibility() != View.VISIBLE) {
            LogUtil.warn(TAG, "updateProgress#player view is invisible");
            return;
        }
        if (isPlaying()) {
            long currentPosition = getCurrentPosition();
            long duration = getDuration();
            if (duration <= 0) {
                duration = getMediaDuration();
            }
            mHandler.postDelayed(this::updateProgress, 1000);
            if (mOnVideoPlayListener != null) {
                mOnVideoPlayListener.onProgressUpdate(currentPosition, getBufferPercentage(), duration);
            }
        }
    }

    public void setMute(boolean isMute) {
        if (mMediaPlayer != null) {
            if (isMute) {
                mMediaPlayer.setVolume(1,1);
            } else {
                mMediaPlayer.setVolume(0,0);
            }
        }
    }

    private long getMediaDuration() {
        try {
            //1，创建MediaMetadataRetriever对象
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            //2.设置音视频资源路径
            retriever.setDataSource(mUri.toString());
            //3.获取音视频资源总时长
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            retriever.release();
            if (TextUtils.isEmpty(time)) {
                return 0;
            }
            return Long.parseLong(time);
        } catch (Exception e) {
            Log.w(TAG, "parse duration ex. error = " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void setVideoURI(Uri uri) {
        if (uri == null) {
            return;
        }
        super.setVideoURI(uri);
        mUri = uri;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void pause() {
        super.pause();
        if (mCustomVideo != null) {
            mCustomVideo.reportVideoPause();
        }
        if (mOnVideoPlayListener != null) {
            mOnVideoPlayListener.onVideoPause();
        }
    }

    @Override
    public void resume() {
        start();
        if (mCustomVideo != null) {
            mCustomVideo.reportVideoResume();
        }
        if (mOnVideoPlayListener != null) {
            mOnVideoPlayListener.onVideoResume();
        }
    }

    public void setOnVideoPlayListener(OnVideoPlayListener onVideoPlayListener) {
        mOnVideoPlayListener = onVideoPlayListener;
    }

    public void setCustomVideo(CustomVideo customVideo) {
        mCustomVideo = customVideo;
    }
}
