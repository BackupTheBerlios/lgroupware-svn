/*
 * AuthFactory.java
 *
 * Created on 7. Juni 2005, 17:57
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
public class AuthFactory 
{
    
    /** Creates a new instance of AuthFactory */
    public AuthFactory() 
    {
    }
    
    /** Auth Objekt erzeugen
     * @param String type Typ der Authentifizierung
     */
    public Object getAuthObject(String type)
    {
        Auth auth = null;
        
        try
        {
            Class authClass = Class.forName("lgwserver.auth.Auth" + type);
            Object authObject = authClass.newInstance();
            auth = (Auth)authObject;
        } catch(Exception e)
        {
            System.out.println("FEHLER: AUTH: " + e);
        }
        auth.initialize();
        
        
        return auth;
    }
}
