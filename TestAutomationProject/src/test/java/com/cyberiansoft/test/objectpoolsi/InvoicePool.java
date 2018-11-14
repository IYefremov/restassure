package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.r360.InvoiceDTO;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class InvoicePool extends GenericObjectPool<InvoiceDTO> {

    /**
     * Constructor.
     *
     * It uses the default configuration for pool provided by
     * apache-commons-pool2.
     *
     * @param factory
     */
    public InvoicePool(PooledObjectFactory<InvoiceDTO> factory) {
        super(factory);
    }

    /**
     *
     *
     * @param factory
     * @param config
     */
    public InvoicePool(PooledObjectFactory<InvoiceDTO> factory,
                  GenericObjectPoolConfig config) {
        super(factory, config);
    }
}
