/*
 * TcpClientSocket.java
 *
 * Created on 5. Juni 2005, 15:02
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwclient;

import java.io.*;
import java.util.*;
import java.net.*;
import org.jdom.JDOMException;

/**
 *
 * @author jfried
 */
public class TcpClientSocket extends Thread
{
    protected Socket sock;
    protected BufferedReader is;
    public PrintWriter out;

    private boolean connected;
    protected Object gui, prefs;
    
    public Map<Object, Object> pluginHash;
    
    /**
     * Abfragen ob die Verbindung bereits steht
     */
    public boolean isConnected()
    {
        return connected;
    }
    
    public void run()
    {
        String line;
        String buffer="";
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
                    proto = new Protokol(new StringReader(buffer), gui, prefs, pluginHash);
                    buffer = "";
                    if(proto.hasAnswer() == true)
                    {
                        out.println(proto.getXML());
                    }
                }
            }        
        } catch(JDOMException jex)
        {
            System.out.println("XML FEHLER: " + jex);
        } catch(IOException e)
        {
            System.out.println("IO FEHLER: " + e);
        }
    }
    
    /** Client GUI setzten
     * das die Klasse auf die GUI zugreifen kann.
     */
    public void setGUI(Object o)
    {
        gui = o;
    }
    
    /** Preference klasse setzten
     */
    public void setPrefs(Object o)
    {
        prefs = o;
    }
    
    public void send(String s)
    {
        out.println(s);
        out.flush();
    }
    
    /** Creates a new instance of TcpClientSocket */
    public TcpClientSocket(String server_name, int Port)
    {
        connected = false;
        try
        {
            sock = new Socket(server_name, Port);
            is = new BufferedReader(new InputStreamReader(
                    sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream());
        } catch(IOException e)
        {
            System.out.println("ERROR: " + e);
            return;
        }
        connected = true;
    }
}
