/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chapter4;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Greay
 */
public class KnapsackProblem {

    private int capacity;
    private ArrayList<Item> items;
    private int[][] array;
    private boolean[][] optimals;
    private int rows;
    private int columns;

    public KnapsackProblem(int weight, ArrayList a) {
        this.capacity = weight;
        this.items = a;
        this.rows = items.size() + 1;
        this.columns = (capacity / 10) + 1;
        this.array = new int[rows][columns];
        this.optimals = new boolean[rows][columns];
    }

    public void solve() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == 0) {
                    array[i][j] = 0;
                    optimals[i][j] = false;
                } else {
                    Item currentItem = items.get(i - 1);
                    int aboveBen = array[i - 1][j];
                    //can fit
                    if (currentItem.weight / 10 <= j) {
                        int diff = j - currentItem.weight / 10;
                        int oldBen = array[i - 1][diff];
                        int currentBen = oldBen + currentItem.benefit;
                        //is benefit greater than previous row
                        if (currentBen > aboveBen) {
                            array[i][j] = currentBen;
                            optimals[i][j] = true;
                        } else {
                            array[i][j] = array[i - 1][j];
                            optimals[i][j] = false;
                        }
                    } else {
                        //grab above
                        array[i][j] = array[i - 1][j];
                        optimals[i][j] = false;
                    }
                }
            }
        }
        
        System.out.println("Highest benefit: " + array[rows-1][columns-1]);
        //need to track the path
        System.out.println("Path: " + optimals[rows-1][columns-1]);
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
//            System.out.println("i ");
            for (int j = 0; j < columns; j++) {
//                System.out.println("j ");
                System.out.print(array[i][j] + " ");
            }
            System.out.println("");
        }
//    System.out.println(Arrays.toString(array));
    }

    public static class Item {

        private int benefit;
        private int weight;

        public Item(int b, int w) {
            this.benefit = b;
            this.weight = w;
        }

    }

    public static void main(String[] args) {
        ArrayList<Item> itemList = new ArrayList<>();
        Item item1 = new Item(60, 10);
        Item item2 = new Item(100, 20);
        Item item3 = new Item(120, 30);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        KnapsackProblem kp = new KnapsackProblem(50, itemList);
        kp.solve();
        kp.display();

    }

}
