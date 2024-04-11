package com.ruoyi.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class PullStmpThread extends Thread{

    @Override
    public void run() {

        /*// 视频720分辨率最好，20-25帧最佳 , 画面清晰度自己调参ffmpeg很多参数
        FrameRecorder recorder = new FFmpegFrameRecorder("rtmp://127.0.0.1/live/livestream", videoWidth, heightWidth, 0);

        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("flv");
        recorder.setFrameRate(frameRate);
        recorder.setVideoBitrate(2000000); //画面清晰的和模糊和马赛克 受码率影响，码率越大画面越清晰，延迟越高，根据自己服务器带宽调整
        recorder.setVideoOption("tune", "zerolatency");
        recorder.setVideoOption("preset", "ultrafast");
        recorder.setGopSize(50);
        try {
            recorder.start();
        } catch (FrameRecorder.Exception e) {
            throw new RuntimeException(e);
        }
        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
        while (true) {
            Frame frame = converterToMat.convert(img.clone());
            try {
                Thread.sleep((long) (20));
                recorder.record(frame);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
*/

    }
}
