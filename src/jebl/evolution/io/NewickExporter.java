package jebl.evolution.io;

import jebl.evolution.trees.Tree;
import jebl.evolution.trees.Utils;
import java.io.Writer;
import java.io.IOException;
import java.util.Collection;

import beast.core.BEASTObject;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: NewickExporter.java 429 2006-08-26 18:17:39Z rambaut $
 */
public class NewickExporter extends BEASTObject implements TreeExporter {
    public NewickExporter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Export a single tree
     *
     * @param tree
     * @throws java.io.IOException
     */
    @Override
	public void exportTree(Tree tree) throws IOException {
        writeTree(tree);
    }

    /**
     * Export a collection of trees
     *
     * @param trees
     * @throws java.io.IOException
     */
    @Override
	public void exportTrees(Collection<? extends Tree> trees) throws IOException {
        for (Tree tree : trees) {
            writeTree(tree);
        }
    }

    private void writeTree(Tree tree) throws IOException {
        writer.write(Utils.toNewick(Utils.rootTheTree(tree)));
    }

    private final Writer writer;
    
	@Override
	public void initAndValidate() throws Exception {
		// nothing to do
	}

}
