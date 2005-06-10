/*
 * Main.java
 *
 * Created on 2. Juni 2005, 19:37
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
import org.jdom.output.*;

// public synchronized void blub(...)
// nur ein thread darf immer auf die methode zugreifen!!
/**
 *
 * @author jfried
 */
public class Main
{   
    protected ClientPool clients;
    protected ServerSocket servSock;
    protected Preference prefs;
    
    private String configFile;
    private ArrayList pluginList;
    
    protected Map<Object, Object> pluginHash;
    
    /** Creates a new instance of Main */
    public Main(String file) 
    {
        configFile = file;
        
        prefs = new Preference(configFile);
        loadPlugins();
        
        clients = new ClientPool();
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
                cl.pluginHash = pluginHash;
                synchronized (clients)
                {
                    clients.add(cl);
                    // hier den plugins die clients bekannt geben
                    Iterator i = pluginHash.keySet().iterator();
                    while(i.hasNext())
                    {
                        String key = (String)i.next();
                        ((LgwPlugin)pluginHash.get(key)).setClients(clients);
                    }
                    cl.start();
                }
            }
        } catch(IOException e)
        {
            System.out.println(">> FEHLER: I/O Exception in runServer: " + e);
        }
    }

    /** Plugins laden 
     * und sie in der gui bekannt geben (durch buttons)
     */
    private void loadPlugins()
    {
        // Da kommen die dynamischen buttons rinn
        ArrayList btnList = new ArrayList();
        pluginList = new ArrayList();
        pluginHash = new HashMap<Object, Object>();
        
        List l = prefs.getList("/lgw/plugins/*");
        Iterator i = l.iterator();
        
        while(i.hasNext())
        {
            Element e = (Element)i.next();
            
            // Plugins laden
            LgwPlugin plugin = null;
            try
            {
                System.out.println(">> plugin: " + e.getAttributeValue("name"));
                Class pluginClass = Class.forName("lgwserver.plugins." + e.getAttributeValue("name"));
                Object pluginObject = pluginClass.newInstance();
                pluginHash.put(e.getAttributeValue("name"), (LgwPlugin)pluginObject);
                ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).initialize();
                //pluginHash.put("test", lf);
            } catch(Exception ex)
            {
                System.out.println("Verdammte Huedde!! Konnte Plugin nicht laden..." + ex);
            }
        }
        
    }     
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("Starting TCPServer...");
        
        Main server = new Main(args[0]);
        server.runServer();
        System.out.println("ERROR: TcpServer beendet...!!!");
    }
    
}
