package seedu.address.ui.taggui;

import seedu.address.model.tag.Tag;

import java.util.Map;
import java.util.Set;

public class GraphLayout implements Layout {

    public static final double GAP_VERTICAL = TagCell.NODE_HEIGHT;
    public static final double GAP_HORIZONTAL = TagCell.NODE_WIDTH * 0.5;

    private final TagGraph graph;
    private final Map<Integer, Set<Tag>> tagLevelMap;

    public GraphLayout(TagGraph graph, Map<Integer, Set<Tag>> map) {
        this.graph = graph;
        tagLevelMap = map;
    }

    public void execute() {
        int maxLevel = tagLevelMap.size() - 1;
        int currentLevel = 0;
        double currentHeight = 0;
        while (currentLevel <= maxLevel) {
            int numNodesInLevel = tagLevelMap.get(currentLevel).size();
            double leftMost = (numNodesInLevel - 1) * (GAP_HORIZONTAL + TagCell.NODE_WIDTH) / 2;

            Set<Tag> levelTagSet = tagLevelMap.get(currentLevel);
            double xCoordinate = leftMost;
            for (Tag tag : levelTagSet) {
                graph.setCellLocation(tag, xCoordinate, currentHeight);
                xCoordinate = xCoordinate - GAP_HORIZONTAL - TagCell.NODE_WIDTH;
            }

            currentLevel++;
            currentHeight = currentHeight - GAP_VERTICAL - TagCell.NODE_HEIGHT;
        }
    }


}
