package seedu.address.ui.taggui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphLevelAlgorithm {

    public static <T> Map<Integer, Set<T>> calculateLevels(Map<T, Set<T>> parentChildMap,
               Map<T, ? extends Set<T>> childParentMap) {
        HashMap<Integer, Set<T>> levelNodeMap = new HashMap<>();

        int currentLevel = 0;

        Map<T, Set<T>> parentChildCopy = parentChildMap;
        Map<T, Set<T>> childParentCopy = deepCopyHashMap(childParentMap);
        levelNodeMap.put(currentLevel, new HashSet<>());

        while (!childParentCopy.isEmpty()) {
            Map<T, Set<T>> tempParentChildCopy = deepCopyHashMap(parentChildCopy);
            for (Map.Entry<T, Set<T>> entry : new HashMap<>(childParentCopy).entrySet()) {
                T child = entry.getKey();
                boolean isParent = parentChildCopy.containsKey(child)
                        && !parentChildCopy.get(child).isEmpty();
                if (isParent) {
                    continue;
                }

                levelNodeMap.get(currentLevel).add(child);
                Set<T> parentSet = childParentCopy.get(child);
                updateParentChildMap(tempParentChildCopy, parentSet, child);
                childParentCopy.remove(child);
            }
            currentLevel++;
            parentChildCopy = tempParentChildCopy;

            levelNodeMap.put(currentLevel, new HashSet<>());
            for (T parent : new HashSet<>(parentChildCopy.keySet())) {
                if (parentChildCopy.get(parent).isEmpty()) {
                    levelNodeMap.get(currentLevel).add(parent);
                    parentChildCopy.remove(parent);
                }
            }
        }
        return levelNodeMap;
    }

    private static <T> void updateParentChildMap(Map<T, Set<T>> parentChildMap, Set<T> parentSet, T child) {
        for (T parent : parentSet) {
            Set<T> currentChildSet = parentChildMap.get(parent);
            currentChildSet.remove(child);
        }
    }

    private static <T> Map<T, Set<T>> deepCopyHashMap(Map<? extends T, ? extends Set<T>> map) {
        return new HashMap<>(map.entrySet().stream().collect(() -> new HashMap<>(),
                (newMap, entry) -> newMap.put(entry.getKey(), new HashSet<>(entry.getValue())),
                (map1, map2) ->   map1.putAll(map2)));
    }

    public static void main(String[] args) {
        Map<Integer, Set<Integer>> parentChildMap = new HashMap<>();
        parentChildMap.put(11, Set.of(9, 10));
        parentChildMap.put(10, Set.of(1, 5, 7));
        parentChildMap.put(9, Set.of(7));
        parentChildMap.put(7, Set.of(5, 2));
        parentChildMap.put(5, Set.of(2, 3));
        parentChildMap.put(2, Set.of(1));
        parentChildMap.put(4, Set.of(1, 2));
        Map<Integer, Set<Integer>> childParentMap = new HashMap<>();
        childParentMap.put(2, Set.of(4, 5, 7));
        childParentMap.put(5, Set.of(7, 10));
        childParentMap.put(1, Set.of(4, 2, 10));
        childParentMap.put(7, Set.of(9, 10));
        childParentMap.put(3, Set.of(5));
        childParentMap.put(9, Set.of(11));
        childParentMap.put(10, Set.of(11));
        System.out.println(calculateLevels(parentChildMap, childParentMap));
    }

}
