/*
 * LgwPlugin.java
 *
 * Created on 9. Juni 2005, 20:17
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver;

/**
 *
 * @author jfried
 */
public abstract class LgwPlugin 
{
    protected ClientPool clients;
    protected org.jdom.Document doc;
    
    public LgwPlugin()
    {
        doc = new org.jdom.Document();
    }
    
    /** Methode um Plugin zu installieren
     * also datenbanken erzeugen und was halt so anfaellt
     */
    abstract public void installPlugin();
    
    /** Plugin initialisieren
     */
    abstract public void initialize();
    
    /** Eventbehandlung
     * Das Uebertragenen XML Dokument wird weitergegeben
     *
     * @param Document d XML document der JDOM Librarie
     */
    abstract public void recieveEvent(org.jdom.Document d);
    
    /** Sendet ein Event an den Server
     */
    abstract public org.jdom.Document sendEvent();
    
    public void setClients(ClientPool o)
    {
        clients = o;
    }
}
