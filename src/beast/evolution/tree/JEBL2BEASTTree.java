package beast.evolution.tree;

import java.util.ArrayList;
import java.util.List;

import beast.core.Description;
import beast.core.Input;
import beast.core.StateNode;
import beast.core.StateNodeInitialiser;
import beast.core.Input.Validate;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.Node;
import beast.evolution.tree.Tree;
import jebl.evolution.trees.RootedTree;
import jebl.evolution.trees.TreeBuilder;

@Description("Converts JEBL tree into a BEAST tree. Handy for initalisation of a BEAST tree.")
public class JEBL2BEASTTree extends Tree implements StateNodeInitialiser {
	public Input<jebl.evolution.trees.TreeBuilder<RootedTree>> treeInput = new Input<>("jebltree", "JEBL tree to be converted to BEAST tree", Validate.REQUIRED);
	public Input<Tree> initialInput = new Input<>("initial", "BEAST tree to be initialised");
	
	
	@Override
	public void initAndValidate() {
		TreeBuilder<RootedTree> builder = treeInput.get();
		jebl.evolution.trees.RootedTree jeblTree = builder.build();
		jebl.evolution.graphs.Node jeblRoot = jeblTree.getRootNode();
		
		// duplicate tree
		root = newNode();
		root.setHeight(jeblTree.getHeight(jeblRoot));
		duplicate(jeblRoot, jeblTree, root);
		
		// renumber
		leafNodeCount = renumberLeafs(root, new int[0]);
		nodeCount = renumberInternal(root, new int[]{leafNodeCount});
		
		// set up node arrays
        initArrays();
        
        // set up taxon names
        TaxonSet taxonSet = m_taxonset.get();
        if (taxonSet != null) {
            List<String> txs = taxonSet.asStringList();
            m_sTaxaNames = txs.toArray(new String[txs.size()]);
            for (int i = 0; i < leafNodeCount; i++) {
            	if (txs.indexOf(m_nodes[i].getID()) >= 0) {
            		m_nodes[i].setNr(txs.indexOf(m_nodes[i].getID()));
            	};
            }
            listNodes(root, m_nodes);
        } else {
            m_sTaxaNames = new String[getNodeCount()];
            collectTaxaNames(getRoot());
            List<String> taxaNames = new ArrayList<>();
            for (String name : m_sTaxaNames) {
            	if (name != null) {
            		taxaNames.add(name);
            	}
            }
            m_sTaxaNames = taxaNames.toArray(new String[]{});
        }
        
        initStateNodes();
	}
	
	@Override
	public void initStateNodes() {
        if (initialInput.get() != null) {
        	initialInput.get().assignFrom(this);
        }
	}

	private int renumberInternal(Node node, int[] nr) {
		for (Node child : node.getChildren()) {
			renumberLeafs(child, nr);
		}
		if (!node.isLeaf()) {
			node.setNr(nr[0]);
			nr[0]++;
		}
		return nr[0];
	}

	private int renumberLeafs(Node node, int[] nr) {
		if (node.isLeaf()) {
			node.setNr(nr[0]);
			nr[0]++;
		} else {
			for (Node child : node.getChildren()) {
				renumberLeafs(child, nr);
			}
		}
		return nr[0];
	}

	private void duplicate(jebl.evolution.graphs.Node jeblNode, RootedTree jeblTree, Node node) {
		for (jebl.evolution.graphs.Node jeblChild : jeblTree.getChildren(jeblNode)) {
			Node child = newNode();
			child.setHeight(jeblTree.getHeight(jeblChild));
			if (jeblTree.isExternal(jeblChild)) {
				child.setID(jeblTree.getTaxon(jeblChild).getName());
			} else {
				duplicate(jeblChild, jeblTree, child);
			}
		}
		
	}

	@Override
	public void getInitialisedStateNodes(List<StateNode> stateNodes) {
		if (initialInput.get() != null) {
			stateNodes.add(initialInput.get());
		}
	}

}
