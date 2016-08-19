package com.viniciusam.usecase.executor;

import com.viniciusam.usecase.usecase.base.UseCase;

/**
 * Created by Vinicius on 15/08/2016.
 */
public interface Executor {

    void execute(final UseCase<?> useCase);
    void stop();

}
