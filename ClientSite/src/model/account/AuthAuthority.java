package model.account;

/**
 * This interface represents an abstract
 * authentication authority. It provides
 * a set of methods that abstraction the
 * actual means of authenicating the user.
 * (i.e.: could be basic auth, OAuth,
 * OpenID, or even a simple HashMap lookup.)
 * as well as the source of authentication.
 */
public interface AuthAuthority {

    /**
     * Create and return a new Account object
     * if the user has successfully logged in
     * the authentication authority. If the
     * authentication fails return null.
     *
     * @return Account or null
     */
    Account requestSignIn();

} // AuthAuthority
