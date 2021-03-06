/*
 * BasicSequence.java
 *
 * (c) 2002-2005 JEBL Development Core Team
 *
 * This package may be distributed under the
 * Lesser Gnu Public Licence (LGPL)
 */
package jebl.evolution.sequences;

import beast.core.Param;
import jebl.evolution.taxa.Taxon;
import jebl.util.AttributableHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beast.core.BEASTObject;

/**
 * A default implementation of the Sequence interface.
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 * @version $Id: BasicSequence.java 1042 2009-12-08 00:14:20Z amyzeta $
 */
public class BasicSequence extends BEASTObject implements Sequence {

    /**
     * Creates a sequence with a name corresponding to the taxon name.
     *
     * Use CharSequence so both a String and a StringBuilder are fine
     *
     * @param taxon
     * @param sequenceString
     */

    public BasicSequence(
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
        this.sequenceCharacters = new byte[len];

        for (int i = 0; i < len; i++) {
            char c = sequenceString.charAt(i);
            State state = sequenceType.getState(c);

            if (state == null) {
                // Something is wrong. Keep original length by inserting an unknown state
                sequenceCharacters[i] ='?';
            }
            else {
                sequenceCharacters[i] = (byte)c;
            }
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
    public BasicSequence(
		@Param(name="sequenceType", description="auto converted jebl2 parameter") SequenceType sequenceType,
		@Param(name="taxon", description="auto converted jebl2 parameter") Taxon taxon,
		@Param(name="states", description="auto converted jebl2 parameter") List<State> states) {

        this.sequenceType = sequenceType;
        this.taxon = taxon;
        this.sequenceCharacters = new byte[states.size()];
        for (int i = 0; i < sequenceCharacters.length; i++) {
            sequenceCharacters[i] = (byte)states.get(i).getCode().charAt(0);
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
        StringBuilder buffer = new StringBuilder(sequenceCharacters.length);
        for (int i : sequenceCharacters) {
            buffer.append((char) i);
        }
        return buffer.toString();
    }

    public String getCleanString() {
        StringBuilder buffer = new StringBuilder(sequenceCharacters.length);
        for (int i : sequenceCharacters) {
            State state = sequenceType.getState((char)i);
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
        return Arrays.asList(sequenceType.toStateArray(getStateIndices()));
    }

    @Override
	public byte[] getStateIndices() {
        byte results[]=new byte[sequenceCharacters.length];
        for (int i = 0; i < sequenceCharacters.length; i++) {
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
        return sequenceCharacters;
    }

    /**
     * @return the state at site.
     */
    @Override
	public State getState(int site) {
        return sequenceType.getState((char)sequenceCharacters[site]);
    }

    /**
     * Returns the length of the sequence
     *
     * @return the length
     */
    @Override
	public int getLength() {
        return sequenceCharacters.length;
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
    private final byte[] sequenceCharacters; // this is really an array of characters, but using bytes since we don't store high-ascii characters

   // private Map<String, Object> attributeMap = null;
   @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;

       BasicSequence that = (BasicSequence) o;

       if (!Arrays.equals(sequenceCharacters, that.sequenceCharacters)) return false;
       if (sequenceType != null ? !sequenceType.equals(that.sequenceType) : that.sequenceType != null) return false;
       if (taxon != null ? !taxon.equals(that.taxon) : that.taxon != null) return false;

       return true;
   }

    @Override
    public int hashCode() {
        int result = (taxon != null ? taxon.hashCode() : 0);
        result = 31 * result + (sequenceType != null ? sequenceType.hashCode() : 0);
        result = 31 * result + (sequenceCharacters != null ? Arrays.hashCode(sequenceCharacters) : 0);
        return result;
    }

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