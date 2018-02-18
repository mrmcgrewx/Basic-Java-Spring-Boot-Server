package models;

import java.io.Serializable;

/**
 * Created by kylemcgrew on 11/29/17.
 */

public class Credentials implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {this.password = password; }
}
