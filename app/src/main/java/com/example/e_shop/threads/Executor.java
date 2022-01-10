package com.example.e_shop.threads;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class Executor {
    private static final Object LOCK = new Object();
    private static Executor sInstance;
    private final java.util.concurrent.Executor diskIO;
    private final java.util.concurrent.Executor mainThread;
    private final java.util.concurrent.Executor networkIO;

    private Executor(java.util.concurrent.Executor diskIO, java.util.concurrent.Executor networkIO, java.util.concurrent.Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static Executor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Executor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public java.util.concurrent.Executor diskIO() {
        return diskIO;
    }

    public java.util.concurrent.Executor mainThread() {
        return mainThread;
    }

    public java.util.concurrent.Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements java.util.concurrent.Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
