/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter1;

/**
 *
 * @author sbj5479
 */
public class Customer implements Runnable {

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    private volatile boolean choosing;
    private volatile int pNum;
    private int id;
    private Bakery bakery;

    public Customer(int id, Bakery bakery) {
        this.pNum = 0;
        this.choosing = false;
        this.id = id;
        this.bakery = bakery;
    }

    @Override
    public void run() {

        while (true) {
            System.out.println("Customer " + this.getId() + " is about to acquire the lock");
            bakery.acquireLock(this);
            System.out.println("Customer " + this.getId() + " has the lock");
            System.out.println("Customer " + this.getId() + " is about to release the lock");
            bakery.releaseLock(this);
            System.out.println("Customer " + this.getId() + " has released the lock");
        }
    }

    /**
     * @return the choosing
     */
    public boolean isChoosing() {
        return choosing;
    }

    /**
     * @param choosing the choosing to set
     */
    public void setChoosing(boolean choosing) {
        this.choosing = choosing;
    }

    /**
     * @return the pNum
     */
    public int getpNum() {
        return pNum;
    }

    /**
     * @param pNum the pNum to set
     */
    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

}
