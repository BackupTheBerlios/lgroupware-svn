/*
 * Auth.java
 *
 * Created on 7. Juni 2005, 17:54
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver.auth;

import lgwserver.auth.*;

/**
 *
 * @author jfried
 */
public abstract class Auth 
{
    /** Die Initialisierung
     */
    public void initialize()
    {
    }
    
    /** Beenden
     */
    public void terminate()
    {
    }
    
    /** Authentifiezierung
     * Überprüft benutzername und password
     * @param String username Benutzername
     * @param String password Passwort
     */
    abstract public boolean authenticate(String username, String password);
}
