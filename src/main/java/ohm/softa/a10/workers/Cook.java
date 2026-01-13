package ohm.softa.a10.workers;

import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.kitchen.*;
import ohm.softa.a10.model.*;

public class Cook implements Runnable{
    String name;
    ProgressReporter progressReporter;
    KitchenHatch kitchenHatch;

    public Cook(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
        this.name = name;
        this.progressReporter = progressReporter;
        this.kitchenHatch = kitchenHatch;
    }

    public void run(){
        Order o;
        while(true){
            synchronized(kitchenHatch){
                if(kitchenHatch.getOrderCount() == 0){
                    progressReporter.notifyCookLeaving();
                    return;
                }
                o = kitchenHatch.dequeueOrder();
                kitchenHatch.printOrders();
                progressReporter.updateProgress();
                kitchenHatch.notifyAll();
            }
            Dish d = new Dish(o.getMealName());
            try {
                Thread.sleep(d.getCookingTime());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; 
            }
            synchronized(kitchenHatch){
                while(kitchenHatch.getDishesCount()>=kitchenHatch.getMaxDishes()){
                try {
                    kitchenHatch.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
                kitchenHatch.enqueueDish(d);
                kitchenHatch.printDishes();
                progressReporter.updateProgress();
                kitchenHatch.notifyAll();
            }
        }
                
    }
}
