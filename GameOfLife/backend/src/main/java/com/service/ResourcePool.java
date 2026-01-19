package com.service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ResourcePool {
    private final Semaphore foodSemaphore;
    private final AtomicInteger availableFood;

    public ResourcePool(int initialFood) {
        //semaphore to prevent thread starvation
        this.foodSemaphore = new Semaphore(initialFood, true);
        this.availableFood = new AtomicInteger(initialFood);
    }

    public boolean tryToEat(int cellId, long timeoutMs) throws InterruptedException {
        //acquire food within the timeout
        boolean acquired = foodSemaphore.tryAcquire(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS);
        if (acquired) {
            availableFood.decrementAndGet();
            // System.out.println("Cell " + cellId + " ate. Food left: " + availableFood.get());
            return true;
        }
        return false;
    }

    public void addFood(int amount) {
        availableFood.addAndGet(amount);
        foodSemaphore.release(amount);
        System.out.println("Added " + amount + " food. Total: " + availableFood.get());
    }

    public void addFoodFromDeadCell(int deadCellId) {

        int amount = 1 + (int)(Math.random() * 5);
        addFood(amount);
        System.out.println("Cell " + deadCellId + " died and dropped " + amount + " food.");
    }
    

    public void clear() {

        foodSemaphore.drainPermits();
        availableFood.set(0);
    }

    public int getAvailableFood() {
        return availableFood.get();
    }
}
