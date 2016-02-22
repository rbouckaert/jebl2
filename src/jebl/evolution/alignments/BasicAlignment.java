/*
 * BasicAlignment.java
 *
 * (c) 2005 JEBL Development Team
 *
 * This package is distributed under the
 * Lesser Gnu Public Licence (LGPL)
 */
package jebl.evolution.alignments;

import jebl.evolution.sequences.Sequence;
import jebl.evolution.sequences.SequenceType;
import jebl.evolution.sequences.State;
import jebl.evolution.taxa.Taxon;

import java.util.*;

import beast.core.BEASTObject;
import beast.core.Param;

/**
 * A basic implementation of the Alignment interface.
 *
 * @author Andrew Rambaut
 * @author Alexei Drummond
 *
 * @version $Id: BasicAlignment.java 1007 2009-07-17 16:01:47Z rambaut $
 */
public class BasicAlignment extends BEASTObject implements Alignment {

    /**
     * Constructs a basic alignment with no sequences.
     */
    public BasicAlignment() {}

    /**
     * Constructs a basic alignment from a collection of sequences. The sequence
     * objects are not copied.
     * @param sequences
     */
    public BasicAlignment(@Param(name="sequence", description="a collection of sequences") List<Sequence> sequences) {
        for (Sequence sequence : sequences) {
            put(sequence);
        }
        constructPatterns();
    }

    /**
     * Constructs a basic alignment from an array of sequences. The sequence
     * objects are not copied.
     * @param sequences
     */
    public BasicAlignment(Sequence[] sequences) {
        for (Sequence sequence : sequences) {
            put(sequence);
        }
        constructPatterns();
    }

    /**
     * @return a set containing all the sequences in this alignment.
     */
    @Override
	public Set<Sequence> getSequences() {
        return new HashSet<>(sequences.values());
    }

    @Override
	public List<Sequence> getSequenceList() {
        List<Sequence> seqs = new ArrayList<>();
        for (Taxon taxon : taxonList) {
            seqs.add(sequences.get(taxon));
        }
        return seqs;
    }

	@Override
	public SequenceType getSequenceType() {
		return sequenceType;
	}

	@Override
	public Sequence getSequence(Taxon taxon) {
	    return sequences.get(taxon);
	}

	@Override
	public int getSiteCount() {
	    return patterns.size();
	}

    @Override
	public int getPatternCount() {
        return patterns.size();
    }

	@Override
	public int getPatternLength() {
		return taxonList.size();
	}

    @Override
	public List<Pattern> getPatterns() {
        return patterns;
    }

	/**
	 * @return the list of taxa that the state values correspond to.
	 */
	@Override
	public List<Taxon> getTaxa() {
	    return taxonList;
	}

	/**
	 * Adds a sequence to this alignment
	 * @param sequence the new sequence.
	 */
	public void addSequence(Sequence sequence) {
	    put(sequence);
	    constructPatterns();
	}

    private void put(Sequence sequence) {
        if (sequenceType == null) {
            sequenceType = sequence.getSequenceType();
        }
        if (sequenceType != sequence.getSequenceType()) {
            throw new IllegalArgumentException(
                    "Type of sequence " + sequence.getTaxon().getName() +
                            " does not match that of other sequences in the alignment" +
                            " (data type = " + sequence.getSequenceType().getName() +
                            ", but expected " + sequenceType.getName() + ").");
        }

        if( taxonList.indexOf(sequence.getTaxon()) >= 0 ) {
           throw new IllegalArgumentException("duplicate sequence name " + sequence.getTaxon());
        }

        sequences.put(sequence.getTaxon(), sequence);
        taxonList.add(sequence.getTaxon());
    }

    private void constructPatterns() {
        patterns.clear();

        State[][] seqs = new State[sequences.size()][];
        int i = 0;
        int maxLen = 0;
        for (Sequence seq : getSequenceList()) {
            seqs[i] = seq.getStates().toArray(new State[]{});
            if (seqs[i].length > maxLen) {
                maxLen = seqs[i].length;
            }
            i++;
        }

        for (int j = 0; j < maxLen; j++) {
            List<State> states = new ArrayList<>();
            for (i = 0; i < seqs.length; i++) {
                if (j < seqs[i].length) {
                    states.add(seqs[i][j]);
                } else {
                    states.add(sequenceType.getGapState());
                }
            }
            patterns.add(new BasicPattern(states));
        }
    }

    private SequenceType sequenceType = null;
    private List<Taxon> taxonList = new ArrayList<>();
    private Map<Taxon, Sequence> sequences = new HashMap<>();
    private List<Pattern> patterns = new ArrayList<>();

    private class BasicPattern implements Pattern {

        public BasicPattern(List<State> states) {
            this.states = states;
        }

        /**
         * @return the data type of the states in this pattern.
         */
        @Override
		public SequenceType getSequenceType() {
            return sequenceType;
        }

        @Override
		public int getLength() {
            return states.size();
        }

        /**
         * @return the list of taxa that the state values correspond to.
         */
        @Override
		public List<Taxon> getTaxa() {
            return taxonList;
        }

	    @Override
		public State getState(int index) {
		    return states.get(index);
	    }

	    /**
	     * @return the list of state values of this pattern.
	     */
	    @Override
		public List<State> getStates() {
	        return states;
	    }

	    /**
	     * @return the set of state values of this pattern.
	     */
	    @Override
		public Set<State> getStateSet() {
	        return new HashSet<>(states);
	    }

	    @Override
		public double getWeight() {
		    return 1.0;
	    }

	    /**
	     * Get the most frequent state in this pattern.
	     * @return the most frequent state
	     */
	    @Override
		public State getMostFrequentState() {
            return getMostFrequentState(false);
	    }

        /**
         * Get the most frequent state in this pattern.
         * @param includeAmbiguous whether ambiguous states and gaps are included
         * @return the most frequent state
         */
        @Override
		public State getMostFrequentState(boolean includeAmbiguous) {
            int maxCount = 0;
            State mostFrequentState = null;
            int[] counts = new int[sequenceType.getStateCount()];
            for (State state : states) {
                counts[state.getIndex()] += 1;
                if ((includeAmbiguous || !state.isAmbiguous()) && counts[state.getIndex()] > maxCount) {
                    maxCount = counts[state.getIndex()];
                    mostFrequentState = state;
                }
            }
            if (mostFrequentState == null) {
                return sequenceType.getUnknownState();
            }
            return mostFrequentState;
        }

	    @Override
		public double getStateFrequency(State state) {
		    return ((double)getStateCount(state)) / states.size();
	    }

        @Override
		public int getStateCount(State state) {
            int count = 0;
            for (State s : states) {
                if (s == state) {
                    count += 1;
                }
            }
            return count;
        }

        private final List<State> states;
    }

	@Override
	public void initAndValidate() {
		// nothing to do
	}

}