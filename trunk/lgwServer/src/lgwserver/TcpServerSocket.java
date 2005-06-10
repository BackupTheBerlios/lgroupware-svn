/*
 * TcpServerSocket.java
 *
 * Created on 5. Juni 2005, 13:14
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver;

import java.net.*;
import java.io.*;
import java.util.*;

import org.jdom.*;

/**
 *
 * @author jfried
 */
public class TcpServerSocket extends Thread
{
    protected Socket clientSock;
    protected BufferedReader is;
    protected PrintWriter pw;
    
    protected ClientPool allClients;
    
    protected String clientIP;
    
    public Map<Object, Object> pluginHash;
    
    /** Creates a new instance of TcpServerSocket */
    public TcpServerSocket(Socket sock, String clnt, ClientPool clients) 
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
        String buffer="";
        int sendChar;
        Protokol proto;
        
        try
        {
            
            
            while((line = is.readLine()) != null)
            {
                if(! line.equalsIgnoreCase(""))
                    buffer += line + "\n";
                
                if(line.compareTo("</lgw>") == 0)
                {
                    // aktionen ausfuehren
                    //System.out.println(buffer);
                    //broadcast(clientIP, buffer);
                    proto = new Protokol(new StringReader(buffer), pluginHash);
                    buffer = "";
                    pw.println(proto.getXML());
                }
            }
        } catch(IOException e)
        {
            System.out.println("ERROR: " + e);
        } catch(JDOMException jex)
        {
            System.out.println("JDOM-ERROR: " + jex);
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
