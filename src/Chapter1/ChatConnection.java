/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter1;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author sbj5479
 */
public class ChatConnection implements Runnable {

    private Socket socket;
    private ChatServer mainServer;
    private PrintWriter pw;
    private BufferedReader br;
    private boolean isAlive;

    public ChatConnection(Socket socket, ChatServer server) throws IOException {
        this.socket = socket;
        this.mainServer = server;
        this.isAlive = true;
        //input and output 
        this.pw = new PrintWriter(socket.getOutputStream(), true);
        this.br = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
    }

    //print message to this client
    public void sendMessage(String message) {
        this.pw.println(message);
    }

    @Override
    public void run() {

        try {
            pw.println("Send your first message: ");
            do {
                //read latest message
                String clientMessage = br.readLine();
//                System.out.println(clientMessage);
                if (clientMessage == null || clientMessage.equals("")) {
                    System.out.println("A client has entered nothing");
                } else {
                    if(clientMessage.toUpperCase().indexOf("QUIT") == 0)
                        isAlive = false;
                    //server sends latest message (clientMessage) to all connections
                    //calls sendMessage
                    
                        mainServer.broadcastMessage(clientMessage);
                }
            } while (isAlive);

            pw.close();
            br.close();
            System.out.println("Closing connection with "
                    + socket.getInetAddress());
            socket.close();
        } catch (IOException e) {
            System.err.println("Server error with game: " + e);
        }
    }

}
