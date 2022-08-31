/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sbj5479
 */
public class Bakery {

    private int nextTicketNumber = 1;
    private List<Customer> customers;

    public void acquireLock(Customer customer) {
//        lamport acquire
        customer.setChoosing(true);
        customer.setpNum(nextTicketNumber++);
        customer.setChoosing(false);

        for (Customer cust : customers) {
            while (cust.isChoosing()) {
                Thread.yield();
            }
            while ((cust.getpNum() != 0 && cust.getpNum() < customer.getpNum())
                    || (cust.getpNum() == customer.getpNum() && cust.getId() < customer.getId())) {
                Thread.yield();
            }
        }
//        has finished waiting and has lock
    }

    public void releaseLock(Customer customer) {
        customer.setpNum(0);
    }

    public static void main(String[] args) {
        Bakery bakery = new Bakery();
        bakery.customers = new ArrayList();

        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer(i, bakery);
            bakery.customers.add(customer);

        }
        for(Customer cust : bakery.customers)
        {
            Thread thread = new Thread(cust);
            thread.start();
        }
    }
}
