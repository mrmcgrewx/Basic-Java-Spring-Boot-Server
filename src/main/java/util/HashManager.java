package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by kylemcgrew on 11/27/17.
 */

public class HashManager {

    static public String hashpw(String password) {
        String hashed = BCrypt.hashpw(password,BCrypt.gensalt());
        return hashed;
    }

    static public String hashpw(String password, int rounds) {
        String hashed = BCrypt.hashpw(password,BCrypt.gensalt(rounds));
        return hashed;
    }

    static public Boolean checkpw(String candidate,String hash) {
        if(BCrypt.checkpw(candidate,hash))
            return true;
        else
            return false;
    }
}
