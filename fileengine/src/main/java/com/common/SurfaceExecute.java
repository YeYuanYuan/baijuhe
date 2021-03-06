package com.common;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.view.Surface;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by linhui on 2018/4/11.
 */
public class SurfaceExecute extends Thread {


    private MediaCodec videocode;
    private MediaCodec audiaCode;
    private Surface surface;
    private AtomicBoolean isStop = new AtomicBoolean(false);
    private MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

    private String path;

    private MediaMuxer mediaMuxer;
    private AtomicBoolean isStartMediaMuxer = new AtomicBoolean(false);


    public SurfaceExecute(String path, Surface surface) {
        this.path = path;
        this.surface = surface;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void running() throws IOException {
        if (videocode == null) {
            mediaMuxer = new MediaMuxer(path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, 720, 1080);
            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 20 * 100 * 1000);
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2);
//            mediaFormat.setInteger(MediaFormat.KEY_BITRATE_MODE, MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR);
            videocode = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            videocode.configure(mediaFormat, surface, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            videocode.start();
        }

        int i = videocode.dequeueOutputBuffer(bufferInfo, 10 * 1000);
        if (i > 0) {

            ByteBuffer buffer = videocode.getOutputBuffer(i);
            if (buffer != null) {
                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    bufferInfo.size = 0;
                }
                if (bufferInfo.size != 0) {
                    buffer.position(bufferInfo.offset);
                    buffer.limit(bufferInfo.offset + bufferInfo.size);
//                bufferInfo.presentationTimeUs = (nanoTime - mediaStartTime - pts) / 1000;
                    mediaMuxer.writeSampleData(0, buffer, bufferInfo);
//                    Log.i(TAG, "video outputBuffer=" + outputBuffer);
                }
                videocode.releaseOutputBuffer(i, false);
            }

        } else if (i == -2 && !isStartMediaMuxer.get()) {
            mediaMuxer.addTrack(videocode.getOutputFormat());
            mediaMuxer.start();
            isStartMediaMuxer.set(true);
        }

    }


    public void initAudio() {
        while (!isStop.get()) {
            try {
                running();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (mediaMuxer != null) {
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaMuxer = null;
        }

        if (videocode != null) {
            videocode.stop();
            videocode.release();
            videocode = null;
        }

    }

    public void stopCodec() {
        this.isStop.set(true);
    }


    @Override
    public void run() {
        initAudio();
    }


    public static final void main(String args[]) {


        DynamicProxy proxy = new DynamicProxy(new Name());
        //加上这句将会产生一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        //获取代理类实例sell
        Person sell = (Person)(Proxy.newProxyInstance(Person.class.getClassLoader(),
                new Class[] {Person.class}, proxy));
        //通过代理类对象调用代理类方法，实际上会转到invoke方法调用
        sell.println();
        sell.haha();
    }

    public static final class DynamicProxy implements InvocationHandler {

        Object object;

        public DynamicProxy(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            if(method.getName().equals("println")) {
                System.out.println("before");
                result = method.invoke(object, args);
                System.out.println("after");
            }else{
                result = method.invoke(object, args);
            }
            return result;
        }
    }

    public interface Person {

        void println();
        void haha();
    }

    public static class Name implements Person {
        @Override
        public void println() {
            System.out.println("lin");
        }

        @Override
        public void haha() {
            System.out.println("haha");
        }
    }


}
