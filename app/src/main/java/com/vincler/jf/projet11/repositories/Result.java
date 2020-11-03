package com.vincler.jf.projet11.repositories;

public interface Result<T> {
    void onResult(T result);
    void onError();
}

