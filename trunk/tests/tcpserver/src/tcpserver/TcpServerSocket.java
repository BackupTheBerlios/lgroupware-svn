/*
 * TcpServerSocket.java
 *
 * Created on 3. Juni 2005, 23:29
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package tcpserver;

import java.net.*;
import java.io.*;

/**
 *
 * @author jfried
 */
public class TcpServerSocket extends Thread
{
    protected Socket clientSock;
    protected BufferedReader is;
    protected PrintWriter pw;
    
    protected clientPool allClients;
    
    protected String clientIP;
    
    /** Creates a new instance of TcpServerSocket */
    public TcpServerSocket(Socket sock, String clnt, clientPool clients) 
    {
        clientSock = sock;
        clientIP = clnt;
        
        try
        {
            is = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));
            pw = new PrintWriter(sock.getOutputStream(), true);
        } catch(IOException e)
        {
            System.out.println("ERROR: " + e);
        }
        
        allClients = clients;
    }
    
    public void run()
    {
        String line;
        
        try
        {
            while((line = is.readLine()) != null)
            {
                broadcast(clientIP, line);
            }
        } catch(IOException e)
        {
            System.out.println("ERROR: " + e);
        } finally
        {
            // Socket wurde beendet, als tschuess...
            System.out.println(clientIP + " hat genug!");
            
            synchronized(allClients)
            {
                allClients.remove(this);
                System.out.println("Noch " + allClients.size() + " Clients!");
            }
        }
    }
        
    protected void close()
    {
        if(clientSock == null)
        {
            return;
        }

        try
        {
            clientSock.close();
            clientSock = null;
        } catch (IOException e)
        {
            System.out.println("ERROR: " + e);
        }
    }
    
    public void send(String mesg)
    {
        pw.println(mesg);
    }
    
    public void broadcast(String sender, String mesg)
    {
        while(allClients.hasNext())
        {
            TcpServerSocket sib = (TcpServerSocket)allClients.next();
            sib.send(sender + ": " + mesg);
        }
        
        allClients.rewind();
    }
}
