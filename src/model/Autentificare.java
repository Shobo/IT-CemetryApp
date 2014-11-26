package model;

/**
 * Created by Andreea on 25/11/14.
 */
public class Autentificare {

    public String authentificate(String username, String password, String job) {
        if (("user".equalsIgnoreCase(username)) && ("pass".equals(password)) && ("Inspector".equals(job)) ) {
            return "Success";
        } else {
            return "Failure";
        }
    }
}
