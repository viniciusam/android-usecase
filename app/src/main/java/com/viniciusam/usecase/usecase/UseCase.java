package com.viniciusam.usecase.usecase;

/**
 * Created by Vinicius on 15/08/2016.
 */
public interface UseCase<E> {

    E run() throws Exception;

}
