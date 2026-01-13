package ohm.softa.a10.kitchen;

import java.util.Deque;
import java.util.LinkedList;

import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

public class KitchenHatchImpl implements KitchenHatch{
    int maxMeals;
    Deque<Order> orders;
    Deque<Dish> dishes = new LinkedList<Dish>();

    public KitchenHatchImpl(int maxMeals, Deque<Order> orders){ 
        this.maxMeals = maxMeals;
        this.orders = orders;
    }

    /**
	 * Get the count how many meals can be placed in the hatch
	 * @return max count
	 */
	public int getMaxDishes(){
        return maxMeals;
    }

    public void printDishes(){
        System.out.println("Dishes: " +dishes.size());
    }

    public void printOrders(){
        System.out.println("Orders: " +orders.size());
    }
	/**
	 * Dequeue an outstanding order
	 * @param timeout timeout to pass to the wait call if no orders are present
	 * @return an order or null if all orders are done
	 */
	public Order dequeueOrder(long timeout){
        return orders.pop();
    }

	/**
	 * Get the remaining count of orders
	 * @return count of orders
	 */
	public int getOrderCount(){
        return orders.size();
    }

	/**
	 * Dequeue a completed dish
	 * @param timeout timeout to pass to the wait call if no meals are present
	 * @return hopefully hot dish to serve to a guest
	 */
	public Dish dequeueDish(long timeout){
        return dishes.pop();
    }

	/**
	 * Enqueue a new completed dish to be served by a waiter
	 * @param m Dish to enqueue
	 */
	public void enqueueDish(Dish m){
        dishes.add(m);
    }

	/**
	 * Get the total count of dishes in the kitchen hatch
	 * @return total count of dishes
	 */
	public int getDishesCount(){
        return dishes.size();
    }
}
