package com.hihonor.adsdk.demo.external.utils;

/**
 * 功能描述
 *
 * @since 2023-08-07
 */
public class VideoAdViewFactory {
    private static final String TAG_LOG = "VideoAdViewFactory";
    private static final String DEFAULT_MINUTE = "00";
    private static final String TIME_SECTION = ":";

    public static String updateTimeText(long position, long duration) {
        String timeText;
        long leftPosition = duration - position;
        if (leftPosition >= (60 * 1000)) {
            long minute = leftPosition / (60 * 1000); // 分
            long second = (leftPosition - minute * 60 * 1000) / 1000; // 秒
            timeText = (minute < 10 ? ("0" + minute) : minute) + TIME_SECTION + (second < 10 ? ("0" + second) : second);
        } else {
            long second = (leftPosition / 1000);
            timeText = DEFAULT_MINUTE + TIME_SECTION + (second < 10 ? ("0" + second) : second);
        }
        return timeText;
    }
}
