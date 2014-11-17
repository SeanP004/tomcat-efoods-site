package model.account;

import java.util.*;

/**
 * This class represents the root
 * authenication authority. It provides
 * a set of methods that abstraction the
 * actual means of authenicating the user.
 * (i.e.: could be basic auth, OAuth,
 * OpenID, or even a simple HashMap lookup.)
 * as well as the source of authenication.
 * 
 * Allows the user to signin with any known
 * authenication authority.
 */
public class RootAuth {

    private static RootAuth singleton;

    private Map<String, AuthAuthority> auths;
    
    private RootAuth() {
        auths = new HashMap<String, AuthAuthority>();
    }

    // Public - Authority Operations

    private boolean hasAuthority(String authName) {
        return auths.containsKey(authName);
    }   

    public void addAuthority(String authName, AuthAuthority auth) {
        auths.put(authName, auth);
    }

    public AuthAuthority getAuthority(String authName) {
        return auths.get(authName);
    }

    public List<AuthAuthority> getAuthorities() {
        return new ArrayList<AuthAuthority>(auths.values());
    }

    public void removeAuthority(String authName) {
        if (hasAuthority(authName)) {
            auths.remove(authName);
        }
    }

    // Public - Amount Operations
    
    public Account requestSignIn(String authName) {
        if (!hasAuthority(authName)) {
            return null;
        }
        return getAuthority(authName).requestSignIn();
    }

    public boolean checkCredentials(Account user, String authName) {
        Account acc = requestSignIn(authName);
        return acc != null && acc.getId() == user.getId();
    }

    // Static

    /**
     * Return a reference to the Catalog singleton.
     * If the singleton does not exist, create it.
     *
     * @throws          DataAccessException
     */
    public static final RootAuth getCatalog() {
        if (singleton == null) {
            singleton = new RootAuth();
        }
        return singleton;
    }

} // AuthAuthority
