import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionDataManager {

    private static SessionDataManager SESSION_DATA_MANAGER;

    private static ThreadLocal<HashMap<String, Object>> tSessionData = ThreadLocal.withInitial(HashMap::new);

    public static SessionDataManager getInstance() {
        if (Objects.isNull(SESSION_DATA_MANAGER)) {
            synchronized (SessionDataManager.class) {
                SESSION_DATA_MANAGER = new SessionDataManager();
            }
        }
        return SESSION_DATA_MANAGER;
    }

    public synchronized void setSessionData(String key, Object value) {
        tSessionData.get().put(key, value);
    }

    public synchronized Object getSessionData(String key) {
        return tSessionData.get().get(key);
    }
}
