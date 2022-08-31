/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sbj5479
 */
public class ChatServer {

    public static final int PORT = 1045; // some unused port number
    public boolean stopRequested;
    public List<ChatConnection> connections;

    public ChatServer() {
        this.stopRequested = false;
        this.connections = new ArrayList();
    }

    public void startServer() {
        stopRequested = false;
        ServerSocket serverSocket = null;

        //start server, request port
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started at "
                    + InetAddress.getLocalHost() + " on port " + PORT);
        } catch (IOException e) {
            System.err.println("Server can't listen on port: " + e);
            System.exit(-1);
        }
        
        //accept incoming sockets
        try{
            while(!stopRequested){
                Socket socket = serverSocket.accept();
                System.out.println("Connection made with "
               + socket.getInetAddress());
                
                //initialise a chat connection between the server and client
                ChatConnection chat = new ChatConnection(socket, this);
                connections.add(chat);
                //start connection
                Thread thread = new Thread(chat);
                thread.start();
            }
            //close server
            serverSocket.close();
        }
        catch (IOException e)
      {  System.err.println("Can't accept client connection: " + e);
      }
        System.out.println("Server has stopped");
    }
    
    public void requestStop()
   {  stopRequested = true;
   }
    
//    send message to all chatConnections
    public void broadcastMessage(String message){
        for(ChatConnection chat : connections)
        {
            chat.sendMessage(message);
        }
    }
    
    public static void main(String[] args)
   {  ChatServer server = new ChatServer();
      server.startServer();
   }

}
