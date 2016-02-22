/*
 * Copyright (c) 2005 JEBL Development team. All Rights Reserved.
 */

package jebl.evolution.io;

import beast.core.Param;
import jebl.evolution.sequences.Sequence;
import jebl.evolution.taxa.Taxon;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

import beast.core.BEASTObject;

/**
 * Class for exporting a fasta file format.
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: FastaExporter.java 442 2006-09-05 21:59:20Z matt_kearse $
 */
public class FastaExporter extends BEASTObject implements SequenceExporter {

    /**
     * Constructor
     */
    public FastaExporter(
		@Param(name="writer", description="auto converted jebl2 parameter") Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    /**
     * export alignment or set of sequences.
     */
    @Override
	public void exportSequences(Collection<? extends Sequence> sequences) throws IOException {
        for (Sequence sequence : sequences) {
            final Taxon taxon = sequence.getTaxon();
            String desc = (String) sequence.getAttribute(FastaImporter.descriptionPropertyName);
            if(desc== null) desc = (String) taxon.getAttribute(FastaImporter.descriptionPropertyName);
            writer.println(">" + taxon.getName().replace(' ','_') + ((desc != null) ? (" " + desc) : ""));
            writer.println(sequence.getString());
        }
    }

    private PrintWriter writer;
    
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