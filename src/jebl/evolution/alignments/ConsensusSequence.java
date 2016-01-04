package jebl.evolution.alignments;

import jebl.evolution.sequences.Sequence;
import jebl.evolution.sequences.SequenceType;
import jebl.evolution.sequences.State;
import jebl.evolution.taxa.Taxon;
import jebl.util.AttributableHelper;

import java.util.Map;
import java.util.Set;

/**
 * @author rambaut
 * @author Alexei Drummond
 * @version $Id: ConsensusSequence.java 365 2006-06-28 07:34:56Z pepster $
 */
public class ConsensusSequence implements Sequence {
    /**
     * Creates a FilteredSequence wrapper to the given source sequence.
     *
     * @param source
     */
    public ConsensusSequence(Taxon taxon, Alignment source) {
        this(taxon, source, false);
    }

    /**
     * Creates a FilteredSequence wrapper to the given source sequence.
     *
     * @param source
     */
    public ConsensusSequence(Taxon taxon, Alignment source, boolean includeAmbiguities) {

        this.taxon = taxon;
        this.source = source;
        this.includeAmbiguities = includeAmbiguities;
    }

    public Alignment getSource() {
        return source;
    }

    /**
     * @return the type of symbols that this sequence is made up of.
     */
    @Override
	public SequenceType getSequenceType() {
        return source.getSequenceType();
    }

    /**
     * @return a string representing the sequence of symbols.
     */
    @Override
	public String getString() {
        if (sequence == null) {
            sequence = jebl.evolution.sequences.Utils.getStateIndices(constructConsensus(source, includeAmbiguities));
        }

        SequenceType sequenceType = getSequenceType();
        StringBuilder buffer = new StringBuilder();
        for (int i : sequence) {
            buffer.append(sequenceType.getState(i).getCode());
        }
        return buffer.toString();
    }

    /**
     * @return an array of state objects.
     */
    @Override
	public State[] getStates() {
        if (sequence == null) {
            sequence = jebl.evolution.sequences.Utils.getStateIndices(constructConsensus(source, includeAmbiguities));
        }
        return getSequenceType().toStateArray(sequence);
    }

    @Override
	public byte[] getStateIndices() {
        if (sequence == null) {
            sequence = jebl.evolution.sequences.Utils.getStateIndices(constructConsensus(source, includeAmbiguities));
        }
        return sequence;
    }

    /**
     * @return the state at site.
     */
    @Override
	public State getState(int site) {
        if (sequence == null) {
            sequence = jebl.evolution.sequences.Utils.getStateIndices(constructConsensus(source, includeAmbiguities));
        }
        return getSequenceType().getState(sequence[site]);
    }

    /**
     * Returns the length of the sequence
     *
     * @return the length
     */
    @Override
	public int getLength() {
        if (sequence == null) {
            sequence = jebl.evolution.sequences.Utils.getStateIndices(constructConsensus(source, includeAmbiguities));
        }
        return sequence.length;
    }

    public static State[] constructConsensus(Alignment source, boolean includeAmbiguities) {
        State[] consensus = new State[source.getPatterns().size()];
        int i = 0;
        for (Pattern pattern : source.getPatterns()) {
            consensus[i] = pattern.getMostFrequentState(includeAmbiguities);
            i++;
        }

        return consensus;
    }

    /**
     * @return that taxon that this sequence represents (primarily used to match sequences with tree nodes)
     */
    @Override
	public Taxon getTaxon() {
        return taxon;
    }

    /**
     * Sequences are compared by their taxa
     *
     * @param o another sequence
     * @return an integer
     */
    @Override
	public int compareTo(Object o) {
        return taxon.compareTo(((Sequence) o).getTaxon());
    }

    // Attributable implementation

    @Override
	public void setAttribute(String name, Object value) {
        attributableHelper.setAttribute(name, value);
    }

    @Override
	public Object getAttribute(String name) {
        return attributableHelper.getAttribute(name);
    }

    @Override
	public void removeAttribute(final String name) {
        attributableHelper.removeAttribute(name);
    }

    @Override
	public Set<String> getAttributeNames() {
        return attributableHelper.getAttributeNames();
    }

    @Override
	public Map<String, Object> getAttributeMap() {
        return attributableHelper.getAttributeMap();
    }

    // private members

    private final Taxon taxon;
    private final Alignment source;
    private byte[] sequence = null;
    private final boolean includeAmbiguities;

    private final AttributableHelper attributableHelper = new AttributableHelper();
}
