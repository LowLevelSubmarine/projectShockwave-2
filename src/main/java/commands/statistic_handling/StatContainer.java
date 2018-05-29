package commands.statistic_handling;

import java.util.HashMap;

public class StatContainer {
    private HashMap<String, Object> data = new HashMap<>();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatContainer) {
            StatContainer b = (StatContainer) obj;
            return b.data.equals(this.data);
        } else {
            return false;
        }
    }
}
