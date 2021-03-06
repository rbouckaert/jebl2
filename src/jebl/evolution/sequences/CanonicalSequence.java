package jebl.evolution.sequences;

import beast.core.Param;
import jebl.evolution.taxa.Taxon;
import jebl.util.AttributableHelper;

import java.util.Set;

import beast.core.BEASTObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A default implementation of the Sequence interface
 * that converts sequence characters to
 * States  such that calling getString() will always return
 * uppercase residues with nucleotide U residues converted to T
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: BasicSequence.java 641 2007-02-16 11:56:21Z rambaut $
 */
public class CanonicalSequence extends BEASTObject implements Sequence {

    /**
     * Creates a sequence with a name corresponding to the taxon name.
     *
     * Use CharSequence so both a String and a StringBuilder are fine
     *
     * @param taxon
     * @param sequenceString
     */

    public CanonicalSequence(
		@Param(name="sequenceType", description="auto converted jebl2 parameter") SequenceType sequenceType,
		@Param(name="taxon", description="auto converted jebl2 parameter") Taxon taxon,
		@Param(name="sequenceString", description="auto converted jebl2 parameter") CharSequence sequenceString) {

        if (sequenceType == null) {
            throw new IllegalArgumentException("sequenceType is not allowed to be null");
        }
        if (taxon == null) {
            throw new IllegalArgumentException("taxon is not allowed to be null");
        }

        this.sequenceType = sequenceType;
        this.taxon = taxon;
        final int len = sequenceString.length();
        this.sequence = new byte[len];

        for (int i = 0; i < len; i++) {
            State state = sequenceType.getState(sequenceString.charAt(i));

            if (state == null) {
                // Something is wrong. Keep original length by inserting an unknown state
                state = sequenceType.getUnknownState();
            }
            sequence[i] = (byte)state.getIndex();
        }
        this.sequenceString = sequenceString;
    }

    /**
     * Creates a sequence with a name corresponding to the taxon name
     *
     * @param taxon
     * @param sequenceType
     * @param states
     */
    public CanonicalSequence(
		@Param(name="sequenceType", description="auto converted jebl2 parameter") SequenceType sequenceType,
		@Param(name="taxon", description="auto converted jebl2 parameter") Taxon taxon,
		@Param(name="states", description="auto converted jebl2 parameter") List<State> states) {

        this.sequenceType = sequenceType;
        this.taxon = taxon;
        this.sequence = new byte[states.size()];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = (byte)states.get(i).getIndex();
        }
    }

    /**
     * @return the type of symbols that this sequence is made up of.
     */
    @Override
	public SequenceType getSequenceType() {
        return sequenceType;
    }

    /**
     * @return a string representing the sequence of symbols.
     */
    @Override
	public String getString() {
        StringBuilder buffer = new StringBuilder(sequence.length);
        for (int i : sequence) {
            buffer.append(sequenceType.getState(i).getCode());
        }
        return buffer.toString();
    }

    public String getCleanString() {
        StringBuilder buffer = new StringBuilder(sequence.length);
        for (int i : sequence) {
            State state = sequenceType.getState(i);
            if (state.isAmbiguous() || state.isGap()) continue;
            buffer.append(sequenceType.getState(i).getCode());
        }
        return buffer.toString();
    }

    /**
     * @return an array of state objects.
     */
    @Override
	public List<State> getStates() {
        return Arrays.asList(sequenceType.toStateArray(sequence));
    }

    @Override
	public byte[] getStateIndices() {
        return sequence;
    }

    /**
     * @return the state at site.
     */
    @Override
	public State getState(int site) {
        return sequenceType.getState(sequence[site]);
    }

    /**
     * Returns the length of the sequence
     *
     * @return the length
     */
    @Override
	public int getLength() {
        return sequence.length;
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

    @Override
	public String toString() {
        return getString();
    }

    // Attributable IMPLEMENTATION

    @Override
	public void setAttribute(String name, Object value) {
        if (helper == null) {
            helper = new AttributableHelper();
        }
        helper.setAttribute(name, value);
    }

    @Override
	public Object getAttribute(String name) {
        if (helper == null) {
            return null;
        }
        return helper.getAttribute(name);
    }

    @Override
	public void removeAttribute(String name) {
        if (helper != null) {
            helper.removeAttribute(name);
        }
    }

    @Override
	public Set<String> getAttributeNames() {
        if (helper == null) {
            return Collections.emptySet();
        }
        return helper.getAttributeNames();
    }

    @Override
	public Map<String, Object> getAttributeMap() {
        if (helper == null) {
            return Collections.emptyMap();
        }
        return helper.getAttributeMap();
    }

    private AttributableHelper helper = null;

    // private members

    private Taxon taxon;
    private SequenceType sequenceType;
    private final byte[] sequence;

   // private Map<String, Object> attributeMap = null;
    
	@Override
	public void initAndValidate() {
		// nothing to do
	}


	public CharSequence getSequenceString() {
		return sequenceString;
	}

	public void setSequenceString(CharSequence sequenceString) {
		this.sequenceString = sequenceString;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setSequenceType(SequenceType sequenceType) {
		this.sequenceType = sequenceType;
	}

	public void setStates(State states) {
		throw new RuntimeException("Not implmented yet");
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setTaxon(Taxon taxon) {
		this.taxon = taxon;
	}

	CharSequence sequenceString;

}