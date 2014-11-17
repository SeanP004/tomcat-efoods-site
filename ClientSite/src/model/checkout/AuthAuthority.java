package model.checkout;

/**
 * This class represents the
 * authenication authority. It provides
 * a set of methods that abstraction the
 * actual means of authenicating the user.
 * (i.e.: could be basic auth, OAuth,
 * OpenID, or even a simple HashMap lookup.)
 * as well as the source of authenication.
 */
public class AuthAuthority {

    private static AuthAuthority singleton;

    private AuthAuthority() { }

    public Account requestSignIn(String authType, String id) {
        return null;
    }

    public boolean checkCredentials(Account user) {
        return true;
    }

    // Static

    /**
     * Return a reference to the Catalog singleton.
     * If the singleton does not exist, create it.
     *
     * @throws          DataAccessException
     */
    public static final AuthAuthority getCatalog() {
        if (singleton == null) {
            singleton = new AuthAuthority();
        }
        return singleton;
    }

} // AuthAuthority
