package com.viniciusam.usecase.executor;

import android.os.Handler;
import android.os.Looper;

import com.viniciusam.usecase.usecase.base.UseCase;

/**
 * Created by vinicius.moreira on 18/08/2016.
 */
public class LooperExecutor implements Executor {

    private Handler mMainHandler;
    private Handler mWorkHandler;

    public LooperExecutor() {
        mMainHandler = new Handler(Looper.getMainLooper());
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mWorkHandler = new Handler(Looper.myLooper());
                Looper.loop();
            }
        }.start();
    }

    @Override
    public void execute(final UseCase<?> useCase) {
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    useCase.beforeExecute();
                    Object o = useCase.run();
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            useCase.callOnSuccess();
                        }
                    });
                } catch (final Exception e) {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            useCase.callOnError(e);
                        }
                    });
                } finally {
                    useCase.afterExecute();
                }
            }
        });
    }

    @Override
    public void stop() {
        if (mWorkHandler != null) {
            mWorkHandler.getLooper().quit();
        }
    }
}
