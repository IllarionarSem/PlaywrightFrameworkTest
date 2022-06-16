package utils;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    public static <T> T getObject(String body, Class<T> clazz) {
        return new Gson().fromJson(body, clazz);
    }

    public static <T> List<T> getObjects(String body, Type type) {
        return new Gson().fromJson(body, type);
    }
}
