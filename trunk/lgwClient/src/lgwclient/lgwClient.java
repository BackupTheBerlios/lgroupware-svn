/*
 * lgwClient.java
 *
 * Created on 5. Juni 2005, 14:57
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwclient;
import java.io.*;
import java.util.*;

import org.jdom.*;
import org.jdom.output.*;

/**
 *
 * @author jfried
 */
public class lgwClient 
{
   protected TcpClientSocket sock;
   protected ClientGUI clientGUI;
   protected Preference prefs;
   
   private String configFile;
   private ArrayList pluginList;
   
   protected Map<Object, Object> pluginHash;
    
    /** Creates a new instance of lgwClient */
    public lgwClient(String file) 
    {
        String in;
        configFile = file;
        
        BufferedReader is = new BufferedReader(
                new InputStreamReader(System.in));
                
        prefs = new Preference(configFile);
        
        sock = new TcpClientSocket(
                prefs.getElement("/lgw/server/host").getTextTrim(), 
                Integer.parseInt(prefs.getElement("/lgw/server/port").getTextTrim()));
        sock.start();
        
        clientGUI = new ClientGUI(prefs);
        loadPlugins();
        
        sock.setGUI(clientGUI);
        sock.setPrefs(prefs);
        sock.pluginHash = pluginHash;
        
        while(sock.isConnected() != true)
        {
            clientGUI.lblStatusBarConnectionState.setText("Verbinde...");
        }
        
        clientGUI.lblStatusBarConnectionState.setText(
                "Authentifiziere mit " + prefs.getElement("/lgw/server/host").getTextTrim());        
        
        // @todo Das muss noch ins protokol...
        //sock.send("");
        Document doc = new Document();
        Element root = new Element("lgw");
        Element plugin = new Element("plugin");
        Element username = new Element("username");
        Element password = new Element("password");
        
        username.setText(prefs.getElement("/lgw/auth/username").getTextTrim());
        password.setText(prefs.getElement("/lgw/auth/password").getTextTrim());
        
        plugin.addContent(username);
        plugin.addContent(password);
        plugin.setAttribute("name", "auth");
        root.addContent(plugin);
        doc.addContent(root);
        
        
        XMLOutputter outp = new XMLOutputter();
        
       // try
       // {
            outp.setFormat(Format.getPrettyFormat());
            sock.send(outp.outputString(doc));

            
            
            //outp.output(doc, sock.out);
            
            //sock.out.flush();
        //    outp.outp
            
           // sock.out.println("");
           // sock.out.flush();
            //sock.out.print(outp.outputString(doc));
         //   sock.out.println();
         //   sock.out.flush();
         //   System.out.println("HIER!!!!");
        /*} catch(IOException e)
        {
            System.out.println("IO Fehler: " + e);
        }*/
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
        
        int starthoehe = 10;
        
        List l = prefs.getList("/lgw/plugins/*");
        Iterator i = l.iterator();
        
        while(i.hasNext())
        {
            Element e = (Element)i.next();
            btnList.add(new PluginButton());
            ((PluginButton)btnList.get(btnList.size()-1)).pluginName = e.getAttributeValue("name");
            ((PluginButton)btnList.get(btnList.size()-1)).setIndex(btnList.size()-1);
            org.openide.awt.Mnemonics.setLocalizedText((PluginButton)btnList.get(btnList.size()-1), e.getAttributeValue("button"));
            clientGUI.panelPaneAction.add((PluginButton)btnList.get(btnList.size()-1), new org.netbeans.lib.awtextra.AbsoluteConstraints(10, starthoehe, -1, -1));

            ((PluginButton)btnList.get(btnList.size()-1)).addActionListener(new java.awt.event.ActionListener() 
            {
                public void actionPerformed(java.awt.event.ActionEvent evt) 
                {
                    btnPluginClicked(evt);
                }
            });
            
            // Plugins laden
            LgwPlugin plugin = null;
            try
            {
                Class pluginClass = Class.forName("lgwclient.plugins." + e.getAttributeValue("name"));
                Object pluginObject = pluginClass.newInstance();
                pluginHash.put(e.getAttributeValue("name"), pluginObject);
                ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).setNetworkSocket(sock);
            } catch(Exception ex)
            {
                System.out.println("Verdammte Huedde!! Konnte Plugin nicht laden..." + ex);
            }
            //pluginList.add();
            starthoehe += 40;
        }
        
    }    
    
    private void btnPluginClicked(java.awt.event.ActionEvent evt) 
    {
        clientGUI.scrollPaneMainFrame.setViewportView((javax.swing.JPanel)pluginHash.get(((PluginButton)evt.getSource()).pluginName));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        if(args.length == 0)
        {
            System.out.println("Usage: lgwClient <config-path>");
            return;
        }
        
        //System.out.println("configPath: " + configPath);
        new lgwClient(args[0]);
    }
    
}
