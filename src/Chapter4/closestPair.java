/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chapter4;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Greay
 */
public class closestPair {

   

    private ArrayList<Point> pointsArray;
    private ArrayList<ArrayList> array;
    private double middlePoint;
    

    public closestPair(ArrayList<Point> points) {
        this.pointsArray = points;
        
    }

    public void display() {

        for (Point p : pointsArray) {
            System.out.println(p);
        }

    }
    
    public double getMiddle() {
        return middlePoint;
    }

  
    public void setMiddle(double middle) {
        this.middlePoint = middle;
    }

    public void displayList(ArrayList<Point> list) {
        for (Point p : list) {
            System.out.println(p);
        }
    }
    
    public void bruteForce(ArrayList<Point> list)
    {
        double smallestD = 10;
        ArrayList<Point> smallestP = new ArrayList<>();
        Point temp = new Point(0,1);
        Point temp2 = new Point(1,0);
        smallestP.add(temp);
        smallestP.add(temp2);
        
        for(int i = 0; i < list.size(); i++)
        {
            for(int j = 0; j < list.size(); j ++)
            {
                if(list.get(i) != list.get(j))
                {
                    double distance  = list.get(i).distance(list.get(j));
                    if (distance < smallestD)
                    {
                        smallestP.remove(1);
                        smallestP.remove(0);
                        smallestP.add(list.get(i));
                        smallestP.add(list.get(j));
                        smallestD = distance;
                    }
                }
            }
        }
        System.out.println("brute poitns: ");
        for(Point p : smallestP)
        {
            System.out.println(p);
        }
        System.out.println("brute dist: " + smallestD);
    }

    public void sort() {
        
        Collections.sort(pointsArray, new PointCompare());
    }
    
    public void sortY(ArrayList<Point> array)
    {
        Collections.sort(array, new PointCompareY());
    }

    public ArrayList<ArrayList> split(ArrayList<Point> list) {
        int end = list.size();
        int middle = end / 2;

        ArrayList arrays = new ArrayList();
        ArrayList<Point> left = new ArrayList();
        ArrayList<Point> right = new ArrayList();
        
        for (int i = 0; i < middle; i++) {
            left.add(list.get(i));
        }
        for (int j = middle; j < end; j++) {
            right.add(list.get(j));
        }
        
        arrays.add(left);
        arrays.add(right);
        return arrays;

    }
    
    public ArrayList<ArrayList> firstSplit(ArrayList<Point> list) {
        int end = list.size();
        int middle = end / 2;

        ArrayList arrays = new ArrayList();
        ArrayList<Point> left = new ArrayList();
        ArrayList<Point> right = new ArrayList();
        
        for (int i = 0; i < middle; i++) {
            left.add(list.get(i));
        }
        for (int j = middle; j < end; j++) {
            right.add(list.get(j));
        }
        
        double leftMax = left.get(left.size()-1).x;
        System.out.println(leftMax);
        double rightMin = right.get(0).x;
        System.out.println(rightMin);
        double middleDiff = (rightMin - leftMax)/2;
        double middleX = leftMax + middleDiff;
        System.out.println(middleX);
        setMiddle(middleX);
        System.out.println(getMiddle());
        
        arrays.add(left);
        arrays.add(right);
        return arrays;

    }

    public ArrayList<Point> recursion(ArrayList<Point> list) {
        ArrayList<Point> closestPair = new ArrayList<>();
        ArrayList<Point> tempPair = new ArrayList<>();

        int n = list.size();
        System.out.println("n: " + n);
        
        //if list has 2 elements, must be smallest
        if (n <= 2) {
            closestPair.add(list.get(0));
            closestPair.add(list.get(1));
        } else {
            //if list is greater than 2 split again
            ArrayList<ArrayList> arrays = split(list);
            ArrayList<Point> left = recursion(arrays.get(0));
            ArrayList<Point> right = recursion(arrays.get(1));
            
            //compare the smallest distances of these sub left+right
            double distance1 = left.get(0).distance(left.get(1));
            double distance2 = right.get(0).distance(right.get(1));
            //left is smaller
            if(distance1 < distance2)
            {
                tempPair.add(left.get(0));
                tempPair.add(left.get(1));
            }
            else //right is smaller
            {
                tempPair.add(right.get(0));
                tempPair.add(right.get(1));
            }
        }
        
        //add the smallest of left/right to recursion list
        if(closestPair.isEmpty())
        {
            closestPair.addAll(tempPair);
        }
//        else if(!closestPair.isEmpty() && !tempPair.isEmpty())
//        {
//            //
//            
//            double distance3 = closestPair.get(0).distance(closestPair.get(1));
//            double distance4 = tempPair.get(0).distance(tempPair.get(1));
//            
//            if(distance3 > distance4)
//            {
//                closestPair.remove(0);
//                closestPair.remove(1);
//                
//                closestPair.add(tempPair.get(0));
//                closestPair.add(tempPair.get(1));   
//            }  
//        }
        
        double sDistance = closestPair.get(0).distance(closestPair.get(1));
        System.out.println("distance: " + sDistance);
        return closestPair;
    }
    
     public void middleComp(ArrayList<Point> left, ArrayList<Point> right){
        double leftDist = left.get(0).distance(left.get(1));
         System.out.println("LEFt POITNS: ");
        for(Point p : left)
        {
            System.out.println(p);
        }
         System.out.println("DISTANCE: " + leftDist);
        double rightDist = right.get(0).distance(right.get(1));
         System.out.println("RIGHT POITSN");
         for(Point p: right)
         {
             System.out.println(p);
         }
         System.out.println("DISTANCE: " + rightDist);
        double minDist = Double.min(leftDist, rightDist);
        ArrayList<Point> smallestPoints = new ArrayList<>();
        double smallestDistance = 0;
        if(leftDist == minDist)
        {
            smallestPoints.add(left.get(0));
            smallestPoints.add(left.get(1));
            smallestDistance = left.get(0).distance(left.get(1));
        }else
        {
            smallestPoints.add(right.get(0));
            smallestPoints.add(right.get(1));
            smallestDistance = right.get(0).distance(right.get(1));
        }
        
        
         System.out.println("Min dist: " + minDist);
         double middlePoint = getMiddle();
         System.out.println(middlePoint);
         double leftParam = middlePoint - minDist;
         double rightParam = middlePoint + minDist;
         System.out.println(leftParam);
         System.out.println(rightParam);
         
         ArrayList<Point> middleList = new ArrayList<>();
         
         //need to comapre points between left and right param by y
         for(Point p : pointsArray)
         {
             if(p.x > leftParam && p.x < rightParam)
                 middleList.add(p);
         }
         
         sortY(middleList);
         
         for(Point p : middleList)
         {
             System.out.println(p);
             
         }
         
         for(int i = 0; i< middleList.size(); i ++)
         {
             Point current  = middleList.get(i);
             double yCheck = current.y + minDist;
             for(int j = i+1; j < middleList.size(); j++)
             {
                 if((middleList.get(j).y < yCheck) && (middleList.get(j).y < smallestDistance))
                 {
                     System.out.println("Smaller found");
                     double distSmall = current.distance(middleList.get(j));
                     System.out.println(distSmall);
                     minDist = distSmall;
                     smallestPoints.remove(1);
                     smallestPoints.remove(0);
                     smallestPoints.add(current);
                     smallestPoints.add(middleList.get(j));
                     smallestDistance = smallestPoints.get(0).distance(smallestPoints.get(1));
                 }
             }
         }
         
         System.out.println("Smallest ARE: ");
         for(Point p: smallestPoints)
         {
             System.out.println(p);
         }
         System.out.println("Smallest Dist IS: " + smallestDistance);
         
    }

    public void findClosest() {

        //part 1
        //sort points by x
        System.out.println("Sorting points");
        this.sort();

        //part 2
        //put sorted points into left and right
        System.out.println("Splitting points");
        this.array = firstSplit(pointsArray);

        
        ArrayList Left = array.get(0);
        ArrayList Right = array.get(1);
        System.out.println("Left: ");
        this.displayList(Left);
        System.out.println("Right: ");
        this.displayList(Right);

        //part 3
        //recursively find closest points
        ArrayList<Point> smallLeft = new ArrayList<>();
        ArrayList<Point> smallRight = new ArrayList<>();
        smallLeft = this.recursion(Left);
        smallRight = this.recursion(Right);
        
       System.out.println("Smallest distance in Left: " + smallLeft.get(0).distance(smallLeft.get(1)));
       System.out.println("Smallest distance in Right: " + smallRight.get(0).distance(smallRight.get(1)));
    
       
       
       //now need to cmpare them as the closes could be bewtween the lft and right line
    //go down the middle and create a new list that encompasses a few on each side
    //start at bottom and calc y dsitance 
    //part 4 
    middleComp(smallLeft, smallRight);
    
    }
    
    
    
   
    
    public class PointCompare implements Comparator<Point> {

        public int compare(Point a, Point b) {
            if (a.x < b.x) {
                return -1;
            } else if (a.x > b.x) {
                return 1;
            } else {
                return 0;
            }
        }
        

    }
    
     public class PointCompareY implements Comparator<Point> {

        public int compare(Point a, Point b) {
            if (a.y < b.y) {
                return -1;
            } else if (a.y > b.y) {
                return 1;
            } else {
                return 0;
            }
        }
        

    }

   
    
    
    public static void main(String[] args) {
        ArrayList<Point> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 32; i++) {
            Point newPoint = new Point(rand.nextInt(10), rand.nextInt(10));
            list.add(newPoint);
        }
        closestPair alg = new closestPair(list);


        alg.findClosest();
        
        alg.bruteForce(list);
    }
}
