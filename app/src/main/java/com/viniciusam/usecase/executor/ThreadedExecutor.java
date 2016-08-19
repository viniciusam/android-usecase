package com.viniciusam.usecase.executor;

import android.os.Handler;
import android.os.Looper;

import com.viniciusam.usecase.usecase.base.UseCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vinicius on 15/08/2016.
 */
public class ThreadedExecutor implements Executor {

    private boolean mIsRunning;
    private Handler mMainHandler;
    private ExecutorService mExecutorService;

    public ThreadedExecutor() {
        mIsRunning = false;
        mMainHandler = new Handler(Looper.getMainLooper());
        mExecutorService = Executors.newCachedThreadPool();
    }

    @Override
    public void execute(final UseCase<?> useCase) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mIsRunning  = true;

                try {
                    useCase.beforeExecute();
                    Object o = useCase.run();

                    if (mIsRunning) {
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                useCase.callOnSuccess();
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (mIsRunning) {
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                useCase.callOnError(e);
                            }
                        });
                    }
                } finally {
                    useCase.afterExecute();
                }
            }
        });
    }

    @Override
    public void stop() {
        mIsRunning = false;
        mExecutorService.shutdown();
    }

}
