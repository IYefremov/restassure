package com.cyberiansoft.test.baseutils;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConditionWaiter {

    private Predicate<Object> predicate;
    private int timeout = 10000;
    private int interval = 500;
    private Supplier<? extends RuntimeException> exceptionSupplier = this::baseException;
    private boolean throwException = true;

    private int expiredTime;

    private ConditionWaiter() {
    }

    public static ConditionWaiterBuilder create(Predicate<Object> predicate) {
        return new ConditionWaiterBuilder(predicate);
    }

    public static ConditionWaiterBuilder create(int timeout, int interval, Predicate<Object> predicate) {
        return create(predicate)
                .withTimeout(timeout)
                .withInterval(interval);
    }

    private void execute() {
        while (!predicate.test(null) && expiredTime < timeout) {
            WaitUtilsWebDriver.waitABit(interval);
            expiredTime += interval;
        }

        if (expiredTime >= timeout && throwException) {
            throw exceptionSupplier.get();
        }
    }

    private RuntimeException baseException() {
        return new IllegalStateException(String.format("Timeout of %s ms has passed", timeout));
    }

    public static final class ConditionWaiterBuilder {

        private final ConditionWaiter conditionWaiter = new ConditionWaiter();

        private ConditionWaiterBuilder(Predicate<Object> predicate) {
            conditionWaiter.predicate = predicate;
        }

        public ConditionWaiterBuilder withTimeout(int timeout) {
            conditionWaiter.timeout = timeout;
            return this;
        }

        public ConditionWaiterBuilder withInterval(int interval) {
            conditionWaiter.interval = interval;
            return this;
        }

        public void execute() {
            conditionWaiter.execute();
        }
    }
}