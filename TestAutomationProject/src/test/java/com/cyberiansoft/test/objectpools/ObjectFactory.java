package com.cyberiansoft.test.objectpools;

import java.io.IOException;

public interface ObjectFactory < T > {

    public abstract T createNew() throws IOException;
}
