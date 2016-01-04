package jebl.evolution.trees;

import beast.core.Param;
import jebl.evolution.graphs.Edge;
import jebl.evolution.graphs.Node;
import jebl.evolution.taxa.Taxon;

import java.util.List;
import java.util.Map;
import java.util.Set;

import beast.core.BEASTObject;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: FilteredRootedTree.java 936 2008-08-06 14:12:07Z rambaut $
 */
public abstract class FilteredRootedTree extends BEASTObject implements RootedTree {

    public FilteredRootedTree(
		@Param(name="source", description="auto converted jebl2 parameter") RootedTree source) {
        this.source = source;
    }

	public RootedTree getSource() {
		return source;
	}

    @Override
	public boolean conceptuallyUnrooted() {
        return source.conceptuallyUnrooted();
    }

    @Override
	public List<Node> getChildren(Node node) {
	    return source.getChildren(node);
    }

    @Override
	public boolean hasHeights() {
        return source.hasHeights();
    }

    @Override
	public double getHeight(Node node) {
        return source.getHeight(node);
    }

    @Override
	public boolean hasLengths() {
        return source.hasLengths();
    }

    @Override
	public double getLength(Node node) {
        return source.getLength(node);
    }

    @Override
	public Node getParent(Node node) {
        return source.getParent(node);
    }

    @Override
	public Node getRootNode() {
        return source.getRootNode();
    }

    @Override
	public Set<Node> getExternalNodes() {
        return source.getExternalNodes();
    }

    @Override
	public Set<Node> getInternalNodes() {
        return source.getInternalNodes();
    }

	@Override
	public Set<Edge> getExternalEdges() {
		return source.getExternalEdges();
	}

	@Override
	public Set<Edge> getInternalEdges() {
		return source.getInternalEdges();
	}

    @Override
	public Node getNode(Taxon taxon) {
        return source.getNode(taxon);
    }

    @Override
	public Set<Taxon> getTaxa() {
        return source.getTaxa();
    }

    @Override
	public Taxon getTaxon(Node node) {
        return source.getTaxon(node);
    }

    @Override
	public boolean isExternal(Node node) {
        return source.isExternal(node);
    }

    @Override
	public List<Node> getAdjacencies(Node node) {
        return source.getAdjacencies(node);
    }

    @Override
	public List<Edge> getEdges(Node node) {
        return source.getEdges(node);
    }

    @Override
	public Set<Edge> getEdges() {
        return source.getEdges();
    }

	@Override
	public Node[] getNodes(Edge edge) {
	    return source.getNodes(edge);
	}

    @Override
	public Edge getEdge(Node node1, Node node2) throws NoEdgeException {
        return source.getEdge(node1, node2);
    }

    @Override
	public double getEdgeLength(Node node1, Node node2) throws NoEdgeException {
        return source.getEdgeLength(node1, node2);
    }

    @Override
	public Set<Node> getNodes() {
        return source.getNodes();
    }

    @Override
	public Set<Node> getNodes(int degree) {
        return source.getNodes(degree);
    }

	@Override
	public boolean isRoot(Node node) {
		return source.isRoot(node);
	}

    @Override
	public void renameTaxa(Taxon from, Taxon to) {
        source.renameTaxa(from, to);
    }

    // Attributable IMPLEMENTATION

	@Override
	public void setAttribute(String name, Object value) {
		source.setAttribute(name, value);
	}

	@Override
	public Object getAttribute(String name) {
		return source.getAttribute(name);
	}

    @Override
	public void removeAttribute(String name) {
        source.removeAttribute(name);
    }

    @Override
	public Set<String> getAttributeNames() {
		return source.getAttributeNames();
	}

	@Override
	public Map<String, Object> getAttributeMap() {
		return source.getAttributeMap();
	}

	// PRIVATE members

	protected RootedTree source;

	@Override
	public void initAndValidate() throws Exception {
		// nothing to do
	}


	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setSource(RootedTree source) {
		this.source = source;
	}

}