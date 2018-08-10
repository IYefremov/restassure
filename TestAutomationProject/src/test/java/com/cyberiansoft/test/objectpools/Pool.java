package com.cyberiansoft.test.objectpools;

import java.util.Iterator;

public interface Pool< T > {

    T get();

    void release(T t);

    void clear();

    int size();

    public Iterator<T> iterator();

    public static interface Validator < T > {

        public boolean isValid(T t);

        public void invalidate(T t);
    }
}
