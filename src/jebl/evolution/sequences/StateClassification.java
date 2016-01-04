package jebl.evolution.sequences;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: StateClassification.java 206 2006-02-02 17:49:56Z rambaut $
 */
public interface StateClassification {

    String getName();

    Set<String> getSetNames();
    String getSetName(State state);
    Set<State> getStateSet(String setName);

    public class Default implements StateClassification {
        public Default(String name, String[] setNames, State[][] stateSets) {
            this.name = name;
            int i = 0;
            for (String setName : setNames) {
                Set<State> stateSet = new HashSet<>();
                for (State state : stateSets[i]) {
                    stateSet.add(state);
                }
                stateMap.put(setName, stateSet);
                i++;
            }
        }

        @Override
		public String getName() {
            return name;
        }

        @Override
		public Set<String> getSetNames() {
            return stateMap.keySet();
        }

        @Override
		public String getSetName(State state) {
            for (String setName : getSetNames()) {
                if (getStateSet(setName).contains(state)) return setName;
            }
            return null;
        }

        @Override
		public Set<State> getStateSet(String setName) {
            return stateMap.get(setName);
        }

        private final String name;

        private final Map<String, Set<State>> stateMap = new HashMap<>();
    }
}
