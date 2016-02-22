package jebl.evolution.io;

import beast.core.Param;
import jebl.evolution.trees.Tree;
import jebl.evolution.trees.Utils;
import java.io.Writer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import beast.core.BEASTObject;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: NewickExporter.java 429 2006-08-26 18:17:39Z rambaut $
 */
public class NewickExporter extends BEASTObject implements TreeExporter {
    public NewickExporter(
		@Param(name="writer", description="auto converted jebl2 parameter") Writer writer) {
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

    private Writer writer;
    
	@Override
	public void initAndValidate() {
		// nothing to do
	}


	public Writer getWriter() {
		return writer;
	}

	
	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

}