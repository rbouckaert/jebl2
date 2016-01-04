package jebl.evolution.trees;

import beast.core.Param;
import jebl.evolution.graphs.Node;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: SortedRootedTree.java 627 2007-01-15 03:50:40Z pepster $
 */
public class SortedRootedTree extends FilteredRootedTree {

    public enum BranchOrdering {
		INCREASING_NODE_DENSITY("increasing"),
		DECREASING_NODE_DENSITY("decreasing");

		BranchOrdering(String name) {
			this.name = name;
		}

		@Override
		public String toString() { return name; }

		private String name;
	}

    public SortedRootedTree(
		@Param(name="source", description="auto converted jebl2 parameter") final RootedTree source,
		@Param(name="branchOrdering", description="auto converted jebl2 parameter") BranchOrdering branchOrdering) {
        super(source);
	    switch (branchOrdering) {
		    case INCREASING_NODE_DENSITY:
			    this.comparator = new Comparator<Node>() {
			        @Override
					public int compare(Node node1, Node node2) {
			            return jebl.evolution.trees.Utils.getExternalNodeCount(source, node2) -
					            jebl.evolution.trees.Utils.getExternalNodeCount(source, node1);
			        }

			        //public boolean equals(Node node1, Node node2) {
			        //   return compare(node1, node2) == 0;
			        //}
			    };
			break;
		    case DECREASING_NODE_DENSITY:
			    this.comparator = new Comparator<Node>() {
			        @Override
					public int compare(Node node1, Node node2) {
			            return jebl.evolution.trees.Utils.getExternalNodeCount(source, node1) -
					            jebl.evolution.trees.Utils.getExternalNodeCount(source, node2);
			        }

			        //public boolean equals(Node node1, Node node2) {
			        //    return compare(node1, node2) == 0;
			        //}
			    };
			break;
		    default:
			    throw new IllegalArgumentException("Unknown enum value");
	    }
	    
	    this.branchOrdering = branchOrdering;
    }

    public SortedRootedTree(
		@Param(name="source", description="auto converted jebl2 parameter") RootedTree source,
		@Param(name="comparator", description="auto converted jebl2 parameter") Comparator<Node> comparator) {
	    super(source);
        this.comparator = comparator;
    }

    @Override
	public List<Node> getChildren(Node node) {
        List<Node> sourceList = source.getChildren(node);
        Collections.sort(sourceList, comparator);
        return sourceList;
    }

	// PRIVATE members

    private Comparator<Node> comparator;

	public BranchOrdering getBranchOrdering() {
		return branchOrdering;
	}

	public void setBranchOrdering(BranchOrdering branchOrdering) {
		this.branchOrdering = branchOrdering;
	}

	public Comparator<Node> getComparator() {
		return comparator;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setComparator(Comparator<Node> comparator) {
		this.comparator = comparator;
	}

	@Override
	public RootedTree getSource() {
		return source;
	}

	@Override
	public void setSource(RootedTree source) {
		this.source = source;
	}
	
	BranchOrdering branchOrdering;
}