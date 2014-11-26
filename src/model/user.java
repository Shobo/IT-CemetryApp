package model;

/**
 * Created by Andreea on 25/11/14.
 */
public class user {

    private String username;
    private String password;
    private String job;

    public user(String username, String password, String job) {
        this.username = username;
        this.password = password;
        this.job = job;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob() {
        this.job = job;
    }
}
