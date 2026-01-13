package ohm.softa.a10.workers;

import java.util.concurrent.ThreadLocalRandom;

import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.kitchen.*;
import ohm.softa.a10.model.*;

public class Waiter implements Runnable{
    String name;
    ProgressReporter progressReporter;
    KitchenHatch kitchenHatch;

    public Waiter(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
        this.name = name;
        this.progressReporter = progressReporter;
        this.kitchenHatch = kitchenHatch;
    }

    public void run(){
        while (true){
            synchronized(kitchenHatch){
                if(kitchenHatch.getOrderCount() == 0 && kitchenHatch.getDishesCount() == 0){
                    progressReporter.notifyWaiterLeaving();
                    return;
                }
                while(kitchenHatch.getDishesCount()<=0){
                    try {
                        kitchenHatch.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                kitchenHatch.dequeueDish();
                kitchenHatch.notifyAll();
                kitchenHatch.printDishes();
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            progressReporter.updateProgress();
            
        }
    }
}
