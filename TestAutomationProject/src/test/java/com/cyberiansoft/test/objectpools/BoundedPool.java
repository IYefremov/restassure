package com.cyberiansoft.test.objectpools;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class BoundedPool < T > extends AbstractPool < T > {

    private int size;

    private Queue< T > objects;

    private Validator < T > validator;
    private ObjectFactory < T > objectFactory;

    private Semaphore permits;

    private volatile boolean shutdownCalled;

    public BoundedPool(int size,
                       TypeValidator validator,
            ObjectFactory < T > objectFactory)
    {
        super();

        this.objectFactory = objectFactory;
        this.size = size;
        this.validator = (Validator<T>) validator;

        objects = new LinkedList< T >();
        //objects = new LinkedBlockingDeque<>();

        try {
            initializeObjects();
        } catch (IOException e) {
            e.printStackTrace();
        }

        shutdownCalled = false;
    }


    @Override
    public T get()
    {
        T t = null;

        if(!shutdownCalled)
        {
            if (permits != null) {
                if (permits.tryAcquire()) {
                    t = objects.poll();
                }
            } else {
                t = objects.poll();
            }

        }
        else
        {
            throw new IllegalStateException(
                    "Object pool already shutdown");
        }

        return t;
    }

    @Override
    public void clear()
    {
        shutdownCalled = true;

        clearResources();
    }

    @Override
    public int size() {
        return objects.size();
    }

    public Iterator<T> iterator() {
        return objects.iterator();
    }

    private void clearResources()
    {
        for(T t : objects)
        {
            validator.invalidate(t);
        }
    }

    @Override
    protected void returnToPool(T t)
    {
        boolean added = objects.add(t);

        if(added)
        {
            permits.release();
        }
    }

    @Override
    protected void handleInvalidReturn(T t)
    {

    }

    @Override
    protected boolean isValid(T t)
    {
        return validator.isValid(t);
    }

    private void initializeObjects() throws IOException {
        for(int i = 0; i < size; i++)
        {
            objects.add(objectFactory.createNew());
        }
    }
}
