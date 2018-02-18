package util;

import java.util.UUID;

/**
 * Created by kylemcgrew on 12/4/17.
 */
public class UUIDManager {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
