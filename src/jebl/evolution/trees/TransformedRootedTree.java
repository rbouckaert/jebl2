package jebl.evolution.trees;

import beast.core.Param;
import jebl.evolution.graphs.Node;

/**
 * This RootedTree class wraps another RootedTree and transforms
 * the branch lengths and node heights using various functions.
 * Currently implemented are equal lengths (all branch lengths
 * are 1.0) and cladogram (the height of a node is proportional
 * to the number of external nodes). Note that all these functions
 * are recalculated on the fly for every call to getHeight and
 * getLength and it may be desirable to precalculate and cache them.
 * @author Andrew Rambaut
 * @version $Id: TransformedRootedTree.java 545 2006-11-28 00:08:34Z twobeers $
 */
public class TransformedRootedTree extends FilteredRootedTree {

	public enum Transform {
	    EQUAL_LENGTHS("equal"),
	    CLADOGRAM("cladogram"),
	    PROPORTIONAL("proportional");

	    Transform(String name) {
	        this.name = name;
	    }

	    @Override
		public String toString() { return name; }

	    private String name;
	}

    public TransformedRootedTree(
		@Param(name="source", description="auto converted jebl2 parameter") final RootedTree source,
		@Param(name="transform", description="auto converted jebl2 parameter") Transform transform) {
        super(source);
        this.transform = transform;
    }

    @Override
	public boolean hasHeights() {
        return true;
    }

    @Override
	public double getHeight(Node node) {
        switch (transform) {
            case EQUAL_LENGTHS:
                int treeLength = getMaxPathLength(getRootNode());
                int rootPathLength = getPathLengthToRoot(node);
                return treeLength - rootPathLength;
            case CLADOGRAM:
                return getMaxPathLength(node);
            case PROPORTIONAL:
                return getCladeSize(node) - 1;
            default:
                throw new IllegalArgumentException("Unknown enum value");
        }
    }

    @Override
	public boolean hasLengths() {
        return true;
    }

    @Override
	public double getLength(Node node) {
        switch (transform) {
            case EQUAL_LENGTHS:
                return 1.0;
            case CLADOGRAM:
            case PROPORTIONAL:
                Node parent = getParent(node);
                if (parent == null) return 0.0; // is the root
                return getHeight(parent) - getHeight(node);
            default:
                throw new IllegalArgumentException("Unknown enum value");
        }
    }

    private int getCladeSize(Node node) {
        if (isExternal(node)) {
            return 1;
        }
        int size = 0;
        for (Node child : getChildren(node)) {
            size += getCladeSize(child);
        }
        return size;
    }

    private int getMaxPathLength(Node node) {
        if (isExternal(node)) {
            return 0;
        }
        int maxPathLength = 0;
        for (Node child : getChildren(node)) {
            int pathLength = getMaxPathLength(child);
            if (pathLength > maxPathLength) {
                maxPathLength = pathLength;
            }
        }
        return maxPathLength + 1;
    }

    private int getPathLengthToRoot(Node node) {
        int pathLength = 0;
        Node parent = getParent(node);
        while (parent != null) {
            pathLength++;
            parent = getParent(parent);
        }
        return pathLength;
    }

    private Transform transform;

	@Override
	public RootedTree getSource() {
		return source;
	}

	@Override
	public void setSource(RootedTree source) {
		this.source = source;
	}

	public Transform getTransform() {
		return transform;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setTransform(Transform transform) {
		this.transform = transform;
	}

}