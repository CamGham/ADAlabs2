/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chapter2;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Greay
 */
public class ParityOutputStream extends FilterOutputStream {

    //boolean queue. needs to hold the remaining bit for
    //each iteration so is only instanitateed once
    Queue<Boolean> queue = new LinkedBlockingQueue<Boolean>();

    //pass instance of parent outputstream
    public ParityOutputStream(OutputStream out) {
        super(out);
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        int value = b;

        //loop through each bit 
        for (int i = 0; i < 8; i++) {
            //nexBit is true if the bit is 1 (starting at most significant)
            boolean nextBit = (value & 0x80) != 0;
            //add this to the queue
            queue.add(nextBit);
            //shift to next bit (move from left to right)
            value = value << 1;
        }

        //perform this loop while the queue size is at least 7
        //will be run almost every time as 8 bits are entered.
        //However each loop leaves 1 bit: as the other 7 are output
        //each loop these remaining bits form to create its own 7 bit output
        //thus on the 7th iteration 2 outputs are produced
        do {
            //the byte outputed
            int assembledByte = 0;
            //whether the 
            boolean paritySet = false;

            for (int i = 0; i < 7; i++) {
                //grab the next bit
                boolean nextBit = queue.poll();
                
                //
                assembledByte = (assembledByte << 1) + (nextBit ? 1 : 0);
                //flip the 'even' boolean
                if (nextBit) {
                    paritySet = !paritySet;
                }
            }

            //run if num 1's is odd
            if (paritySet) {
                //add 1 to make it even
                assembledByte = assembledByte | 0x80;
            }
            //dont udner stand hwo to add 0
            
            
            System.out.println("queue size: " + queue.size());
            System.out.println("assembled byte: " + assembledByte);

        } while (queue.size() >= 7);

    }

    public static void main(String[] args) throws IOException {
        //create parity outputstream, passing in parent outputstream (simple sout)
        ParityOutputStream pos = new ParityOutputStream(System.out);
        pos.write(65);//'A'
        pos.write(66);//'B'
        pos.write(67);//'C'
        pos.write(68);//'D'
        pos.write(69);//'E'
        pos.write(70);//'F'

        //should have two outputs
        System.out.println("this is the 7th write: expect 2 bytes");
        pos.write(71);//'G'
    }

}
