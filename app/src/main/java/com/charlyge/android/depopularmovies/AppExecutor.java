package com.charlyge.android.depopularmovies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public class AppExecutor {
private static final Object LOCK = new Object();
private static AppExecutor sInstance;
private final Executor diskIo;
private final Executor networkIO;
private final Executor mainThreadIO;

private AppExecutor(Executor diskIo,Executor networkIO,Executor mainThreadIO){
    this.diskIo= diskIo;
    this.networkIO = networkIO;
    this.mainThreadIO = mainThreadIO;

}

    public static AppExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor getMainThreadIO() {
        return mainThreadIO;
    }

    public Executor getDiskIo() {
        return diskIo;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
