package jebl.evolution.trees;

import beast.core.Param;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: ReRootedTree.java 776 2007-09-05 11:17:12Z rambaut $
 */
public class ReRootedTree extends FilteredRootedTree {

    public enum RootingType {
		MID_POINT("midpoint"),
		LEAST_SQUARES("least squares");

		RootingType(String name) {
			this.name = name;
		}

		@Override
		public String toString() { return name; }

		private String name;
	}

    public ReRootedTree(
		@Param(name="source", description="auto converted jebl2 parameter") final RootedTree source,
		@Param(name="rootingType", description="auto converted jebl2 parameter") RootingType rootingType) {
        super(source);
	    switch (rootingType) {
		    case MID_POINT:
			break;
		    case LEAST_SQUARES:
			break;
		    default:
			    throw new IllegalArgumentException("Unknown enum value");
	    }
	    this.rootingType = rootingType;
    }

	// PRIVATE members
//    private final Node rootChild1;
//    private final Node rootChild2;
//    private final double rootLength1;
//    private final double rootLength2;

	public RootingType getRootingType() {
		return rootingType;
	}

	public void setRootingType(RootingType rootingType) {
		this.rootingType = rootingType;
	}

	@Override
	public RootedTree getSource() {
		return source;
	}

	@Override
	public void setSource(RootedTree source) {
		this.source = source;
	}

	RootingType rootingType;
}