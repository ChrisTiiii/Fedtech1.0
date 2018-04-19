package com.example.juicekaaa.fedtech10.Activity.AR;

/**
 * Created by Juicekaaa on 17/6/26.
 */

import android.content.Context;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cn.easyar.CameraCalibration;
import cn.easyar.CameraDevice;
import cn.easyar.CameraDeviceFocusMode;
import cn.easyar.CameraDeviceType;
import cn.easyar.CameraFrameStreamer;
import cn.easyar.Frame;
import cn.easyar.FunctorOfVoidFromPointerOfTargetAndBool;
import cn.easyar.ImageTarget;
import cn.easyar.ImageTracker;
import cn.easyar.Renderer;
import cn.easyar.StorageType;
import cn.easyar.Target;
import cn.easyar.TargetInstance;
import cn.easyar.TargetStatus;
import cn.easyar.Vec2I;
import cn.easyar.Vec4I;

public class HelloAR {
    private CameraDevice camera;
    private CameraFrameStreamer streamer;
    private ArrayList<ImageTracker> trackers;
    private Renderer videobg_renderer;
    private ArrayList<VideoRenderer> video_renderers;
    private VideoRenderer current_video_renderer;
    private int tracked_target = 0;
    private int active_target = 0;
    private ARVideo video = null;
    private boolean viewport_changed = false;
    private Vec2I view_size = new Vec2I(0, 0);
    private int rotation = 0;
    private Vec4I viewport = new Vec4I(0, 0, 1280, 720);
    private Context context;
    private static int flag = -1;

    public static int num = 0;


    public static void setNum() {
        num = 0;
    }

    ;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                switch (flag) {
                    case 1:
                        Toast.makeText(context, "研发部1送您一张劵", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "收获了1优惠券");
                        num++;
                        break;
                    case 2:
                        Log.e("TAG", "收获了2优惠券");
                        Toast.makeText(context, "研发部2送您一张劵", Toast.LENGTH_SHORT).show();
                        num++;
                        break;
                    case 3:
                        Log.e("TAG", "收获了3优惠券");
                        Toast.makeText(context, "研发部3送您一张劵", Toast.LENGTH_SHORT).show();
                        num++;
                        break;
                    case 4:
                        Log.e("TAG", "收获了3优惠券");
                        Toast.makeText(context, "研发部4送您一张劵", Toast.LENGTH_SHORT).show();
                        num++;
                        break;
                    case 5:
                        Log.e("TAG", "收获了3优惠券");
                        Toast.makeText(context, "研发部5送您一张劵", Toast.LENGTH_SHORT).show();
                        num++;
                        break;

                    case 6:
                        Log.e("TAG", "收获了3优惠券");
                        Toast.makeText(context, "FEDTECH", Toast.LENGTH_SHORT).show();
                        num++;
                        break;
                    case 7:
                        Log.e("TAG", "收获了3优惠券");
                        Toast.makeText(context, "巩前哨", Toast.LENGTH_SHORT).show();
                        num++;
                        break;
                }
            }
        }
    };


    public HelloAR(Context context) {
        trackers = new ArrayList<ImageTracker>();
        this.context = context;
    }

    private void loadFromImage(ImageTracker tracker, String path) {
        ImageTarget target = new ImageTarget();
        String jstr = "{\n"
                + "  \"images\" :\n"
                + "  [\n"
                + "    {\n"
                + "      \"image\" : \"" + path + "\",\n"
                + "      \"name\" : \"" + path.substring(0, path.indexOf(".")) + "\"\n"
                + "    }\n"
                + "  ]\n"
                + "}";
        target.setup(jstr, StorageType.Assets | StorageType.Json, "");
        tracker.loadTarget(target, new FunctorOfVoidFromPointerOfTargetAndBool() {
            @Override
            public void invoke(Target target, boolean status) {
                Log.i("HelloAR", String.format("load target (%b): %s (%d)", status, target.name(), target.runtimeID()));
            }
        });
    }

    private void loadAllFromJsonFile(ImageTracker tracker, String path) {
        for (ImageTarget target : ImageTarget.setupAll(path, StorageType.Assets)) {
            tracker.loadTarget(target, new FunctorOfVoidFromPointerOfTargetAndBool() {
                @Override
                public void invoke(Target target, boolean status) {
                    try {
                        Log.i("HelloAR", String.format("load target (%b): %s (%d)", status, target.name(), target.runtimeID()));
                    } catch (Throwable ex) {
                    }
                }
            });
        }
    }

    public boolean initialize() {
        camera = new CameraDevice();
        streamer = new CameraFrameStreamer();
        streamer.attachCamera(camera);

        boolean status = true;
        status &= camera.open(CameraDeviceType.Default);
        camera.setSize(new Vec2I(1280, 720));

        if (!status) {
            return status;
        }
        ImageTracker tracker = new ImageTracker();
        tracker.attachStreamer(streamer);
        loadAllFromJsonFile(tracker, "targets.json");

        loadFromImage(tracker, "dog.jpg");
        loadFromImage(tracker, "yanfa.JPG");
        loadFromImage(tracker, "aiguo.jpg");
        loadFromImage(tracker, "tuling.jpg");
        loadFromImage(tracker, "oula.jpg");
        loadFromImage(tracker, "fedtech.jpg");
        loadFromImage(tracker, "gong.jpg");



        trackers.add(tracker);

        return status;
    }

    public void dispose() {
        if (video != null) {
            video.dispose();
            video = null;
        }
        tracked_target = 0;
        active_target = 0;

        for (ImageTracker tracker : trackers) {
            tracker.dispose();
        }
        trackers.clear();
        video_renderers.clear();
        current_video_renderer = null;
        if (videobg_renderer != null) {
            videobg_renderer.dispose();
            videobg_renderer = null;
        }
        if (streamer != null) {
            streamer.dispose();
            streamer = null;
        }
        if (camera != null) {
            camera.dispose();
            camera = null;
        }
    }

    public boolean start() {
        boolean status = true;
        status &= (camera != null) && camera.start();
        status &= (streamer != null) && streamer.start();
        camera.setFocusMode(CameraDeviceFocusMode.Continousauto);
        for (ImageTracker tracker : trackers) {
            status &= tracker.start();
        }
        return status;
    }

    public boolean stop() {
        boolean status = true;
        for (ImageTracker tracker : trackers) {
            status &= tracker.stop();
        }
        status &= (streamer != null) && streamer.stop();
        status &= (camera != null) && camera.stop();
        return status;
    }

    public void initGL() {
        if (active_target != 0) {
            video.onLost();
            video.dispose();
            video = null;
            tracked_target = 0;
            active_target = 0;
        }
        if (videobg_renderer != null) {
            videobg_renderer.dispose();
        }
        videobg_renderer = new Renderer();
        video_renderers = new ArrayList<VideoRenderer>();
        for (int k = 0; k < 3; k += 1) {
            VideoRenderer video_renderer = new VideoRenderer();
            video_renderer.init();
            video_renderers.add(video_renderer);
        }
        current_video_renderer = null;
    }

    public void resizeGL(int width, int height) {
        view_size = new Vec2I(width, height);
        viewport_changed = true;
    }

    private void updateViewport() {
        CameraCalibration calib = camera != null ? camera.cameraCalibration() : null;
        int rotation = calib != null ? calib.rotation() : 0;
        if (rotation != this.rotation) {
            this.rotation = rotation;
            viewport_changed = true;
        }
        if (viewport_changed) {
            Vec2I size = new Vec2I(1, 1);
            if ((camera != null) && camera.isOpened()) {
                size = camera.size();
            }
            if (rotation == 90 || rotation == 270) {
                size = new Vec2I(size.data[1], size.data[0]);
            }
            float scaleRatio = Math.max((float) view_size.data[0] / (float) size.data[0], (float) view_size.data[1] / (float) size.data[1]);
            Vec2I viewport_size = new Vec2I(Math.round(size.data[0] * scaleRatio), Math.round(size.data[1] * scaleRatio));
            viewport = new Vec4I((view_size.data[0] - viewport_size.data[0]) / 2, (view_size.data[1] - viewport_size.data[1]) / 2, viewport_size.data[0], viewport_size.data[1]);

            if ((camera != null) && camera.isOpened())
                viewport_changed = false;
        }
    }

    public void render() {
        GLES20.glClearColor(1.f, 1.f, 1.f, 1.f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if (videobg_renderer != null) {
            Vec4I default_viewport = new Vec4I(0, 0, view_size.data[0], view_size.data[1]);
            GLES20.glViewport(default_viewport.data[0], default_viewport.data[1], default_viewport.data[2], default_viewport.data[3]);
            if (videobg_renderer.renderErrorMessage(default_viewport)) {
                return;
            }
        }

        if (streamer == null) {
            return;
        }
        Frame frame = streamer.peek();
        try {
            updateViewport();
            GLES20.glViewport(viewport.data[0], viewport.data[1], viewport.data[2], viewport.data[3]);

            if (videobg_renderer != null) {
                videobg_renderer.render(frame, viewport);
            }

            ArrayList<TargetInstance> targetInstances = frame.targetInstances();
            if (targetInstances.size() > 0) {
                TargetInstance targetInstance = targetInstances.get(0);
                Target target = targetInstance.target();
                int status = targetInstance.status();
                if (status == TargetStatus.Tracked) {
                    int id = target.runtimeID();
                    if (active_target != 0 && active_target != id) {
                        video.onLost();
                        video.dispose();
                        video = null;
                        tracked_target = 0;
                        active_target = 0;
                    }
                    if (tracked_target == 0) {
                        if (video == null && video_renderers.size() > 0) {
                            String target_name = target.name();
                            if (target_name.equals("yanfa") && video_renderers.get(0).texId() != 0) {
                                video = new ARVideo();
                                video.openVideoFile("view2.mp4", video_renderers.get(0).texId());
                                current_video_renderer = video_renderers.get(0);
                                flag = 1;

                            } else if (target_name.equals("dog") && video_renderers.get(0).texId() != 0) {
                                video = new ARVideo();
                                video.openVideoFile("view2.mp4", video_renderers.get(0).texId());
                                current_video_renderer = video_renderers.get(0);
                                flag = 2;

                            } else if (target_name.equals("aiguo") && video_renderers.get(0).texId() != 0) {
                                video = new ARVideo();
                                video.openVideoFile("view2.mp4", video_renderers.get(0).texId());
                                current_video_renderer = video_renderers.get(0);
                                flag = 3;

                            } else if (target_name.equals("oula") && video_renderers.get(0).texId() != 0) {
                                video = new ARVideo();
                                video.openVideoFile("view2.mp4", video_renderers.get(0).texId());
                                current_video_renderer = video_renderers.get(0);
                                flag = 4;
                            } else if (target_name.equals("tuling") && video_renderers.get(0).texId() != 0) {
                                video = new ARVideo();
                                video.openVideoFile("view2.mp4", video_renderers.get(0).texId());
                                current_video_renderer = video_renderers.get(0);
                                flag = 5;
                            }else if (target_name.equals("fedtech") && video_renderers.get(0).texId() != 0) {
                                video = new ARVideo();
                                video.openVideoFile("view2.mp4", video_renderers.get(0).texId());
                                current_video_renderer = video_renderers.get(0);
                                flag = 6;


                            } else if (target_name.equals("gong") && video_renderers.get(0).texId() != 0) {
                                video = new ARVideo();
                                video.openVideoFile("view2.mp4", video_renderers.get(0).texId());
                                current_video_renderer = video_renderers.get(0);
                                flag = 7;
                            } else if (target_name.equals("idback") && video_renderers.get(2).texId() != 0) {
                                video = new ARVideo();
                                video.openStreamingVideo("https://sightpvideo-cdn.sightp.com/sdkvideo/EasyARSDKShow201520.mp4", video_renderers.get(2).texId());
                                current_video_renderer = video_renderers.get(2);
                                num++;
                            }

//                            } else if (target_name.equals("dog") && video_renderers.get(1).texId() != 0) {
//                                video = new ARVideo();
//                                video.openTransparentVideoFile("view2.mp4", video_renderers.get(1).texId());
//                                current_video_renderer = video_renderers.get(1);
//                                flag = 2;
//                            else if (target_name.equals("guizi") && video_renderers.get(0).texId() != 0) {
//                                video = new ARVideo();
//                                video.openTransparentVideoFile("view2.mp4", video_renderers.get(0).texId());
//                                current_video_renderer = video_renderers.get(0);
//                                flag = 3;
//                            } else if (target_name.equals("top") && video_renderers.get(0).texId() != 0) {
//                                video = new ARVideo();
//                                video.openTransparentVideoFile("view2.mp4", video_renderers.get(0).texId());
//                                current_video_renderer = video_renderers.get(0);
//                                flag = 4;
//                            }
                            handler.sendEmptyMessage(100);
                        }
                        if (video != null) {
                            video.onFound();
                            tracked_target = id;
                            active_target = id;
                        }


                    }
                    ImageTarget imagetarget = target instanceof ImageTarget ? (ImageTarget) (target) : null;
                    if (imagetarget != null) {
                        if (current_video_renderer != null) {
                            video.update();
                            if (video.isRenderTextureAvailable()) {
                                current_video_renderer.render(camera.projectionGL(0.2f, 500.f), targetInstance.poseGL(), imagetarget.size());
                            }
                        }
                    }
                }
            } else {
                if (tracked_target != 0) {
                    video.onLost();
                    tracked_target = 0;
                }
            }
        } finally

        {
            frame.dispose();
        }
    }
}
