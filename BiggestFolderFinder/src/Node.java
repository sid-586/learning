import java.io.File;
import java.util.ArrayList;

public class Node {

    private File folder;
    private ArrayList<Node> children;
    private long size;
    private int level;
    private long sizeLimit;

    public Node(File folder, long sizeLimit) {
        this.folder = folder;
        this.sizeLimit = sizeLimit;
        children = new ArrayList<>();
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node node) {
        children.add(node);
        node.setLevel(level + 1);
        node.setSizeLimit(sizeLimit);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        String size = ReadableSizeCalculator.getHumanReadableSize(getSize());
        builder.append(folder.getName() + " - " + size + "\n");
        for (Node child : children) {
            if (child.getSize() >= sizeLimit) {
                builder.append("   ".repeat(level + 1)).append(child.toString());
            }
        }
        return builder.toString();
    }

    private void setLevel(int level) {
        this.level = level;
    }
    private long setSizeLimit(long sizeLimit) {
        return sizeLimit;
    }
    public long getSizeLimit() {
        return sizeLimit;
    }
}

