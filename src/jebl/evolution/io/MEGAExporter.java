package jebl.evolution.io;

import beast.core.Param;
import jebl.evolution.alignments.Alignment;
import jebl.evolution.sequences.Sequence;
import jebl.evolution.sequences.SequenceType;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import beast.core.BEASTObject;

/**
 * * Export to MEGA.
 *
 * @author Joseph Heled
 * @version $Id: MEGAExporter.java 530 2006-11-15 04:01:44Z stevensh $
 */
public class MEGAExporter extends BEASTObject implements AlignmentExporter {
    private PrintWriter writer;

    /**
     *
     * @param writer where export text goes
     */
    public MEGAExporter(
		@Param(name="writer", description="auto converted jebl2 parameter") Writer writer,
		@Param(name="comment", description="auto converted jebl2 parameter") String comment) {
        this.writer = new PrintWriter(writer);
        this.writer.println("#mega");
        if( comment != null ) {
            this.writer.println("!" + comment);
        }
        this.comment = comment;
    }

    /**
     * 
     * @param alignment the alignment to export
     * @param name the name of the alignment
     * @throws IOException
     */
    public void exportAlignment(Alignment alignment, String name) throws IOException{
        writer.print("!Title ");
        writer.print(name);
        writer.println(";");

        writer.print("!Format DataType=");
        String dataType =
                alignment.getSequenceType() == SequenceType.NUCLEOTIDE?
                "nucleotide": "protein";
        writer.println(dataType + ";");
        exportAlignment(alignment);
    }

    /**
     * @deprecated Files created by this export method won't be importable by MEGA (because they don't have titles).  Use {@link #exportAlignment(jebl.evolution.alignments.Alignment, String)}  instead.
     * @param alignment
     * @throws IOException
     */
    @Deprecated
	@Override
	public void exportAlignment(Alignment alignment) throws IOException {
        List<Sequence> seqs = alignment.getSequenceList();

        for( Sequence seq : seqs )  {
            writer.println();
            writer.println("#" + seq.getTaxon().getName().replaceAll(" ","_"));
            writer.println(seq.getString());
        }
    }
    
	@Override
	public void initAndValidate() {
		// nothing to do
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}
	
	private String comment;
}