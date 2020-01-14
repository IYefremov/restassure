package com.cyberiansoft.test.objectpools;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.*;

public final class BoundedBlockingPool < T > extends AbstractPool < T >
        implements BlockingPool < T >  {

    private int size;

    private BlockingQueue< T > objects;

    private Validator < T > validator;
    private ObjectFactory < T > objectFactory;

    private ExecutorService executor =
            Executors.newCachedThreadPool();

    private volatile boolean shutdownCalled;

    public BoundedBlockingPool(
            int size,
            TypeValidator validator,
            ObjectFactory<T> objectFactory) throws IOException {
        super();

        this.objectFactory = objectFactory;
        this.size = size;
        this.validator = (Validator<T>) validator;

        objects = new LinkedBlockingQueue< T >(size);

        initializeObjects();

        shutdownCalled = false;
    }

    public T get(long timeOut, TimeUnit unit)
    {
        if(!shutdownCalled)
        {
            T t = null;

            try
            {
                t = objects.poll(timeOut, unit);

                return t;
            }
            catch(InterruptedException ie)
            {
                Thread.currentThread().interrupt();
            }

            return t;
        }

        throw new IllegalStateException(
                "Object pool is already shutdown");
    }

    public T get() {
        //if( objects.size() == 0)
         //   return null;

        if(!shutdownCalled)
        {

            T t = null;

            try
            {
                t = objects.take();
            }
            catch(InterruptedException ie)
            {
                System.out.println("throw");
                Thread.currentThread().interrupt();
            }
            return t;
        }

        throw new IllegalStateException(
                "Object pool is already shutdown");
    }

    public void clear()
    {
        shutdownCalled = true;

        executor.shutdownNow();

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
        if(validator.isValid(t))
        {
            executor.submit(new ObjectReturner(objects, t));
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

    private class ObjectReturner < E >
            implements Callable < Void >
    {
        private BlockingQueue < E > queue;
        private E e;

        public ObjectReturner(BlockingQueue < E > queue, E e)
        {
            this.queue = queue;
            this.e = e;
        }

        public Void call()
        {
            while(true)
            {
                try
                {
                    queue.put(e);
                    break;
                }
                catch(InterruptedException ie)
                {
                    Thread.currentThread().interrupt();
                }
            }

            return null;
        }
    }
}
