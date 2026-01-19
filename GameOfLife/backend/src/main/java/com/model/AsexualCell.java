package com.model;

import com.service.CellManager;
import com.service.ResourcePool;

public class AsexualCell extends Cell {
    
    public AsexualCell(int id, CellManager manager, ResourcePool resourcePool) {
        super(id, manager, resourcePool);
    }
    
    @Override
    protected void tryToReproduce() throws InterruptedException {

        manager.sleepFor(1000); 
        
        //asexual reproduction
        manager.reproduce(this, null);
        
        //parent dies
        synchronized (this) {
            isAlive = false;
        }
    }
}
