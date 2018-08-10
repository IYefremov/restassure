package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class WOPool extends GenericObjectPool<WorkOrderDTO> {

    /**
     * Constructor.
     *
     * It uses the default configuration for pool provided by
     * apache-commons-pool2.
     *
     * @param factory
     */
    public WOPool(PooledObjectFactory<WorkOrderDTO> factory) {
        super(factory);
    }

    /**
     *
     *
     * @param factory
     * @param config
     */
    public WOPool(PooledObjectFactory<WorkOrderDTO> factory,
                    GenericObjectPoolConfig config) {
        super(factory, config);
    }
}
