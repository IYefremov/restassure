package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class InspPool extends GenericObjectPool<InspectionDTO> {

    /**
     * Constructor.
     *
     * It uses the default configuration for pool provided by
     * apache-commons-pool2.
     *
     * @param factory
     */
    public InspPool(PooledObjectFactory<InspectionDTO> factory) {
        super(factory);
    }

    /**
     *
     *
     * @param factory
     * @param config
     */
    public InspPool(PooledObjectFactory<InspectionDTO> factory,
                 GenericObjectPoolConfig config) {
        super(factory, config);
    }
}
