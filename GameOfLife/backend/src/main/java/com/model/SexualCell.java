package com.model;

import com.service.CellManager;
import com.service.ResourcePool;

public class SexualCell extends Cell {
    
    public SexualCell(int id, CellManager manager, ResourcePool resourcePool) {
        super(id, manager, resourcePool);
    }
    
    @Override
    protected void tryToReproduce() throws InterruptedException {
        int attempts = 0;
        SexualCell partner = null;
        

        while (attempts < 5 && partner == null && isAlive) {

            manager.sleepFor(1000);
            
            partner = manager.findMatingPartner(this);
            attempts++;
        }
        
        if (partner != null) {

            synchronized (manager) {

                if (partner.isWantingToReproduce() && partner.isAlive()) {
                    manager.reproduce(this, partner);
                    

                    this.resetAfterReproduction();
                    partner.resetAfterReproduction();
                }
            }
        } else {

            this.resetAfterReproduction();
        }
    }
}
