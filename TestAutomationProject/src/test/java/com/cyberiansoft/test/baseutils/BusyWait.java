package com.cyberiansoft.test.baseutils;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class BusyWait {

    private Predicate<Object> predicate;
    private int timeout = 20000;
    private int interval = 500;
    private Supplier<? extends RuntimeException> exceptionSupplier = this::baseException;
    private boolean shouldThrowException = true;

    private int elapsedTime;

    private BusyWait() {
    }

    public static BusyWaitBuilder create(Predicate<Object> predicate) {
        return new BusyWaitBuilder(predicate);
    }

    public static BusyWaitBuilder create(int timeout, int interval, Predicate<Object> predicate) {
        return create(predicate)
                .withTimeout(timeout)
                .withInterval(interval);
    }

    private void execute() {
        while (!predicate.test(null) && elapsedTime < timeout) {
            WaitUtilsWebDriver.waitABit(interval);
            elapsedTime += interval;
        }

        if (elapsedTime >= timeout && shouldThrowException) {
            throw exceptionSupplier.get();
        }
    }

    private RuntimeException baseException() {
        return new IllegalStateException(String.format("BusyWait timeout of %s ms has passed", timeout));
    }

    public static final class BusyWaitBuilder {

        private final BusyWait busyWait = new BusyWait();

        private BusyWaitBuilder(Predicate<Object> predicate) {
            busyWait.predicate = predicate;
        }

        public BusyWaitBuilder withTimeout(int timeout) {
            busyWait.timeout = timeout;
            return this;
        }

        public BusyWaitBuilder withInterval(int interval) {
            busyWait.interval = interval;
            return this;
        }

        public void execute() {
            busyWait.execute();
        }
    }
}
