/*
 * BasicSequence.java
 *
 * (c) 2002-2005 JEBL Development Core Team
 *
 * This package may be distributed under the
 * Lesser Gnu Public Licence (LGPL)
 */
package jebl.evolution.sequences;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beast.core.BEASTObject;
import beast.core.Param;
import jebl.evolution.taxa.Taxon;
import jebl.util.AttributableHelper;

/**
 * A codon implementation of the Sequence interface.
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: BasicSequence.java 1042 2009-12-08 00:14:20Z amyzeta $
 */
public class CodonSequence extends BEASTObject implements Sequence {

    /**
     * Creates a sequence with a name corresponding to the taxon name
     *
     * @param taxon
     * @param states
     */
    public CodonSequence(
		@Param(name="taxon", description="auto converted jebl2 parameter") Taxon taxon,
		@Param(name="states", description="auto converted jebl2 parameter") List<State> states) {

        this.sequenceType = SequenceType.CODON;
        this.taxon = taxon;
        this.sequenceStates = new CodonState[states.size()];
        for (int i = 0; i < states.size(); i++) {
            sequenceStates[i] = states.get(i);
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
        StringBuilder buffer = new StringBuilder(sequenceStates.length);
        for (State state : sequenceStates) {
            buffer.append(state.getCode());
        }
        return buffer.toString();
    }

    public String getCleanString() {
        StringBuilder buffer = new StringBuilder(sequenceStates.length);
        for (State state : sequenceStates) {
            if (state.isAmbiguous() || state.isGap()) continue;
            buffer.append(state.getCode());
        }
        return buffer.toString();
    }

    /**
     * @return an array of state objects.
     */
    @Override
	public List<State> getStates() {
        return Arrays.asList(sequenceType.toStateArray(getStateIndices()));
    }

    @Override
	public byte[] getStateIndices() {
        byte results[]=new byte[sequenceStates.length];
        for (int i = 0; i < sequenceStates.length; i++) {
             results [i] = (byte) getState(i).getIndex();
        }
        return results;
    }


    /**
     * Get the sequence characters representing the sequence.
     * This return is a byte[] rather than a char[]
     * to avoid using twice as much memory as necessary.
     * The individual elements of the returned array can be cast to chars.
     * @return the sequence characters as an array of characters.
     */
    public byte[] getSequenceCharacters() {
        throw new UnsupportedOperationException("codons don't have single character codes");
//        return sequenceStates;
    }

    /**
     * @return the state at site.
     */
    @Override
	public State getState(int site) {
        return sequenceStates[site];
    }

    /**
     * Returns the length of the sequence
     *
     * @return the length
     */
    @Override
	public int getLength() {
        return sequenceStates.length;
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
    private final SequenceType sequenceType;
    private final State[] sequenceStates;

   // private Map<String, Object> attributeMap = null;
   @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;

       CodonSequence that = (CodonSequence) o;

       if (!Arrays.equals(sequenceStates, that.sequenceStates)) return false;
       if (sequenceType != null ? !sequenceType.equals(that.sequenceType) : that.sequenceType != null) return false;
       if (taxon != null ? !taxon.equals(that.taxon) : that.taxon != null) return false;

       return true;
   }

    @Override
    public int hashCode() {
        int result = (taxon != null ? taxon.hashCode() : 0);
        result = 31 * result + (sequenceType != null ? sequenceType.hashCode() : 0);
        result = 31 * result + (sequenceStates != null ? Arrays.hashCode(sequenceStates) : 0);
        return result;
    }
    
	@Override
	public void initAndValidate() {
		// nothing to do
	}

	public void setStates(State states) {
		throw new RuntimeException("Not implmented yet");
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setTaxon(Taxon taxon) {
		this.taxon = taxon;
	}

}