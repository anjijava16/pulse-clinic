package com.pulse.desktop.controller.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author befast
 */
public enum ThreadPoolService {
    INSTANCE;
    
    private ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
    
    private ThreadPoolService() {
    }
    
    public void execute(Runnable task) {
        this.THREAD_POOL.submit(task);
    }
}
