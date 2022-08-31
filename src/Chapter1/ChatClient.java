/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sbj5479
 */
public class ChatClient {

    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 1045; // host port number

    public ChatClient() {
    }

    public void startClient() {
        Socket socket = null;

        // connect to server
        try {
            socket = new Socket(HOST_NAME, HOST_PORT);
        } catch (IOException e) {
            System.err.println("Client could not make connection: " + e);
            System.exit(-1);
        }

        //create reader and writer threads
        try {
            Reader reader = new Reader(socket);
            Writer writer = new Writer(socket);

            Thread rThread = new Thread(reader);
            Thread wThread = new Thread(writer);

            // start threads
            rThread.start();
            wThread.start();

            boolean finished = false;
            // keep client alive until 'QUIT' is entered
            do {
                //user has input quit string
                //writer is closed, close reader and client
                if (writer.isFinished()) {
                    System.out.println("disconnecting...");
                    reader.setFinished(true);
                    finished = true;
                }
            } while (!finished);
            
            //client is quitting so close the socket
            socket.close();
        } catch (IOException e) {
            System.err.println("Client error with server: " + e);
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startClient();
    }

    //inner class for reader thread
    private class Reader implements Runnable {

        BufferedReader br;
        private Socket socket;
        private boolean finished;
        
        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        //uses socket from client
        public Reader(Socket socket) {
            this.socket = socket;
            setFinished(false);

            try {
                br = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Client error: " + e);
            }
        }

        @Override
        public void run() {
            //while thread is alive read response from server (broadcast)
            //and print to this client
            try {
                do {
                    String serverResponse = br.readLine();
                    System.out.println(serverResponse);
                } while (!isFinished());
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    //inner class for writer thread
    private class Writer implements Runnable {

        PrintWriter pw;
        private Socket socket;
        private boolean finished;
        Scanner keyboardInput;

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        //uses socket of client
        //scanner for keyboard input
        public Writer(Socket socket) {
            this.socket = socket;
            setFinished(false);
            this.keyboardInput = new Scanner(System.in);

            try {
                pw = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.err.println("Client error: " + e);
            }
        }

        @Override
        public void run() {
            //while thread is alive ask for input
            do {
                String input = keyboardInput.nextLine();
                //check if input == QUIT (exit string)
                
                    pw.println(input);
                if (input.toUpperCase().indexOf("QUIT") == 0) {
                    //stop this client
                    setFinished(true);
                } 
            } while (!isFinished());
            pw.close();
        }
    }
}
