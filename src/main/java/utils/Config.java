package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {
    public static String getValue(String key) {
        return ResourceBundle.getBundle("config").getString(key);
    }
}
