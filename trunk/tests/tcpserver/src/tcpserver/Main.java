/*
 * Main.java
 *
 * Created on 2. Juni 2005, 21:07
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package tcpserver;

import java.net.*;
import java.io.*;
import java.util.*;

// public synchronized void blub(...)
// nur ein thread darf immer auf die methode zugreifen!!
/**
 *
 * @author jfried
 */
public class Main
{   
    protected clientPool clients;
    protected ServerSocket servSock;
    
    /** Creates a new instance of Main */
    public Main() 
    {
        clients = new clientPool();
        try
        {
            servSock = new ServerSocket(1055); // Port
        } catch(IOException e)
        {
            System.out.println("I/O Exception in Main.<init>" + e);
            System.exit(1);
        }
    }
    
    public void runServer()
    {
        try
        {
            while (true)
            {
                Socket us = servSock.accept();
                String hostName = us.getInetAddress().getHostName();
                System.out.println("Verbindung von: " + hostName);
                TcpServerSocket cl = new TcpServerSocket(us, hostName, clients);
                synchronized (clients)
                {
                    clients.add(cl);
                    cl.start();
                    cl.send("Servus! Nummer: " + clients.size());
                }
            }
        } catch(IOException e)
        {
            System.out.println(">> FEHLER: I/O Exception in runServer: " + e);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("Starting TCPServer...");
        
        Main server = new Main();
        server.runServer();
        System.out.println("ERROR: TcpServer beendet...!!!");
    }
    
}
