package com.cyberiansoft.test.objectpools;

import org.apache.poi.ss.formula.functions.T;

public class TypeValidator  implements Pool.Validator<T> {
    @Override
    public boolean isValid(T type) {
            if(type == null)
            {
                return false;
            }
            return true;
    }

    @Override
    public void invalidate(T type) {
        type = null;
    }
}
