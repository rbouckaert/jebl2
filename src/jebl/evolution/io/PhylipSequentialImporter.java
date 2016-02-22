package jebl.evolution.io;

import beast.core.Param;
import jebl.evolution.sequences.BasicSequence;
import jebl.evolution.sequences.Sequence;
import jebl.evolution.sequences.SequenceType;
import jebl.evolution.taxa.Taxon;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import beast.core.BEASTObject;

/**
 * Class for importing PHYLIP sequential file format
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: PhylipSequentialImporter.java 840 2007-11-09 04:52:39Z twobeers $
 */
public class PhylipSequentialImporter extends BEASTObject implements SequenceImporter {

    /**
     * Constructor
     */
    public PhylipSequentialImporter(
		@Param(name="reader", description="auto converted jebl2 parameter") Reader reader,
		@Param(name="sequenceType", description="auto converted jebl2 parameter") SequenceType sequenceType,
		@Param(name="maxNameLength", description="auto converted jebl2 parameter") Integer maxNameLength) {
        helper = new ImportHelper(reader);

        this.reader = reader;
        this.sequenceType = sequenceType;
        this.maxNameLength = maxNameLength;
    }

    /**
     * importSequences.
     */
    @Override
	public List<Sequence> importSequences() throws IOException, ImportException {

        List<Sequence> sequences = new ArrayList<>();

        try {

            int taxonCount = helper.readInteger();
            int siteCount = helper.readInteger();

            String firstSeq = null;

            for (int i = 0; i < taxonCount; i++) {
                StringBuilder name = new StringBuilder();

                char ch = helper.read();
                int n = 0;
                while (!Character.isWhitespace(ch) && (maxNameLength < 1 || n < maxNameLength)) {
                    name.append(ch);
                    ch = helper.read();
                    n++;
                }

                StringBuilder seq = new StringBuilder(siteCount);
                helper.readSequence(seq, sequenceType, "", siteCount, "-", "?", ".", firstSeq);

                if (firstSeq == null) {
                    firstSeq = seq.toString();
                }
                sequences.add(new BasicSequence(sequenceType, Taxon.getTaxon(name.toString()), seq.toString()));
            }
        } catch (EOFException e) {
        }

        return sequences;
    }

    private final ImportHelper helper;
    private SequenceType sequenceType;
    private int maxNameLength = 10;
    
	@Override
	public void initAndValidate() {
		// nothing to do
	}


	public Integer getMaxNameLength() {
		return maxNameLength;
	}

	public void setMaxNameLength(Integer maxNameLength) {
		this.maxNameLength = maxNameLength;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public SequenceType getSequenceType() {
		return sequenceType;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setSequenceType(SequenceType sequenceType) {
		this.sequenceType = sequenceType;
	}
	
	private Reader reader;

}