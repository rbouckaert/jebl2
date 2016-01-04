package jebl.evolution.trees;

import beast.core.Param;
import jebl.evolution.distances.DistanceMatrix;
import jebl.evolution.graphs.Node;
import jebl.evolution.taxa.Taxon;

import java.util.List;
import java.util.Arrays;

/**
 * constructs a UPGMA tree from pairwise distances
 *
 * @version $Id: UPGMATreeBuilder.java 301 2006-04-17 15:35:01Z rambaut $
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @author Joseph Heled
 *
 * Adapted from BEAST code.
 */

class UPGMATreeBuilder extends ClusteringTreeBuilder<Tree> {
     // want a rooted tree
    private final SimpleRootedTree tree;

    /**
     * constructor UPGMA tree
     *
     * @param distanceMatrix distance matrix
     */
    public UPGMATreeBuilder(
		@Param(name="distanceMatrix", description="auto converted jebl2 parameter") DistanceMatrix distanceMatrix) {
        super(distanceMatrix, 2);
        tree = new SimpleRootedTree();
    }

    //
    // Protected and Private stuff
    //

    @Override
	protected Tree getTree() {
        return tree;
    }

    @Override
	protected Node createExternalNode(Taxon taxon) {
        return tree.createExternalNode(taxon);
    }

    @Override
	protected Node createInternalNode(Node[] nodes, double[] distances) {
        List<Node> a = Arrays.asList(nodes);
        Node node = tree.createInternalNode(a);
        tree.setHeight(node, distances[0]);
        return node;
    }

    @Override
	protected double[] joinClusters() {
        Double d = getDist(besti, bestj) / 2.0;
        return new double[] {d};
    }

    @Override
	protected double updatedDistance(int k) {
        int i = besti;
        int j = bestj;
        int ai = alias[i];
        int aj = alias[j];

        double tipSum = tipCount[ai] + tipCount[aj];

        return 	((tipCount[ai]) / tipSum) * getDist(k, i) +
                ((tipCount[aj]) / tipSum) * getDist(k, j);
    }

	public DistanceMatrix getDistanceMatrix() {
		return distanceMatrix;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}

}