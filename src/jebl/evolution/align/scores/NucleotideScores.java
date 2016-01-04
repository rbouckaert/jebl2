package jebl.evolution.align.scores;

import beast.core.Param;
import jebl.evolution.sequences.NucleotideState;
import jebl.evolution.sequences.Nucleotides;
import jebl.evolution.sequences.State;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Richard Moir
 * @author Alexei Drummond
 *
 * @version $Id: NucleotideScores.java 916 2008-05-26 03:33:27Z matt_kearse $
 * 
 */
public class NucleotideScores extends Scores {

    float match = 5;
    float mismatchTransition = -4;
    float mismatchTransversion = -4;
    String name = "";
    //private boolean includeAmbiguities;
    private String alphabet =
            Nucleotides.getCanonicalStates().get(0).getCode() +
                    Nucleotides.getCanonicalStates().get(1).getCode() +
                    Nucleotides.getCanonicalStates().get(2).getCode() +
                    Nucleotides.getCanonicalStates().get(3).getCode() +"U";

    public static final NucleotideScores IUB = new NucleotideScores(1.0f, -0.9f);
    public static final NucleotideScores CLUSTALW = new NucleotideScores(1.0f, 0.0f);

    protected NucleotideScores() {
    }

    public NucleotideScores(
		@Param(name="scores", description="auto converted jebl2 parameter") NucleotideScores scores) {
        name = scores.name;
        alphabet = scores.getAlphabet();
        match = scores.match;
        mismatchTransition = scores.mismatchTransition;
        mismatchTransversion = scores.mismatchTransversion;
    }

    /**
     * @param match match score
     * @param misMatch mismatch score
     */
    public NucleotideScores(
		@Param(name="match", description="auto converted jebl2 parameter") Float match,
		@Param(name="misMatch", description="auto converted jebl2 parameter") Float misMatch) {
        this("", match, misMatch);
    }

    public NucleotideScores(
		@Param(name="match", description="auto converted jebl2 parameter") Float match,
		@Param(name="misMatch", description="auto converted jebl2 parameter") Float misMatch,
		@Param(name="ambiguousMatch", description="auto converted jebl2 parameter") Float ambiguousMatch) {
        this("", match, misMatch, misMatch, ambiguousMatch, false);
    }

    public NucleotideScores(
		@Param(name="name", description="auto converted jebl2 parameter") String name,
		@Param(name="match", description="auto converted jebl2 parameter") Float match,
		@Param(name="misMatch", description="auto converted jebl2 parameter") Float misMatch) {
        this(name, match, misMatch, misMatch, 0f, true);
    }

    public NucleotideScores(
		@Param(name="name", description="auto converted jebl2 parameter") String name,
		@Param(name="match", description="auto converted jebl2 parameter") Float match,
		@Param(name="mismatchTransition", description="auto converted jebl2 parameter") Float mismatchTransition,
		@Param(name="mismatchTransversion", description="auto converted jebl2 parameter") Float mismatchTransversion) {
        this.name = name;
        buildScores(match, mismatchTransition, mismatchTransversion, 0, false);
    }

    public NucleotideScores(
		@Param(name="name", description="auto converted jebl2 parameter") String name,
		@Param(name="match", description="auto converted jebl2 parameter") Float match,
		@Param(name="mismatchTransition", description="auto converted jebl2 parameter") Float mismatchTransition,
		@Param(name="mismatchTransversion", description="auto converted jebl2 parameter") Float mismatchTransversion,
		@Param(name="ambiguousMatch", description="auto converted jebl2 parameter") Float ambiguousMatch,
		@Param(name="useWeightedAmbigousMatches", description="auto converted jebl2 parameter") Boolean useWeightedAmbigousMatches) {
        this.name = name;
        buildScores(match, mismatchTransition, mismatchTransversion, ambiguousMatch, true, useWeightedAmbigousMatches);
    }

    public NucleotideScores(
		@Param(name="scores", description="auto converted jebl2 parameter") Scores scores,
		@Param(name="percentmatches", description="auto converted jebl2 parameter") Double percentmatches) {
        double match = Math.log(percentmatches/(4 *.25 *.25));
        double mismatch = Math.log((1-percentmatches)/(12 * .25 *.25));

        // normalize match from scores
        float ma = scores.score['A']['A'];
        float mm = (float)(mismatch * (ma/match));

        name = ((int)Math.round(100*percentmatches)) + "% similarity";
        buildScores(ma, mm, mm, 0, true);
        includeAdditionalCharacters(this, scores.getExtraResidues());
    }

    void buildScores(float match, float mismatchTransition, float mismatchTransversion, float ambiguousMatch, boolean includeAmbiguities) {
        buildScores(match, mismatchTransition, mismatchTransversion, ambiguousMatch, includeAmbiguities,false);
    }

    /**
     *
     * @param useWeightedAmbigousMatches true so that abiguities are converted to a fraction between 0 and 1 representing the fractional number of
     * matches of all canonical states represented by them. the score of such matches = mismatchScore + (match-mismatchScore)*fraction. For
     * example, if misamtch=0 and match = 1, then score(A,R)=0.5, score(T,R)=0, score(R,R)=0.5, score(B,B)=0.33.
     */
    void buildScores(float match, float mismatchTransition, float mismatchTransversion, float ambiguousMatch, boolean includeAmbiguities, boolean useWeightedAmbigousMatches) {
        this.match = match;
        this.mismatchTransition = mismatchTransition;
        this.mismatchTransversion = mismatchTransversion;
        //this.includeAmbiguities = includeAmbiguities;

//        final int states = includeAmbiguities? Nucleotides.getStateCount():Nucleotides.getCanonicalStateCount();
        List<NucleotideState> states = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (State state : Nucleotides.getStates()) {
            if (state.isGap()) continue;
            if (state.isAmbiguous()&& !includeAmbiguities) continue;
            states.add ((NucleotideState) state);
            builder.append (state.getCode ());
        }
        // Add RNA "U" and the corresponding canonical state which is T_STATE to the list:
        alphabet = builder.toString() + "U";
        states.add(Nucleotides.T_STATE);

        int statesCount = states.size();
        float[][] scores = new float[statesCount][statesCount];
        for (int i = 0; i < statesCount; i++) {
            NucleotideState state1 = states.get(i);
            for (int j = 0; j < statesCount; j++) {
                NucleotideState state2 = states.get(j);
                float value;
                if (state1.equals(state2) && !useWeightedAmbigousMatches) {
                    value = match;
                }
                else if (state1.possiblyEqual(state2)) {
                    if (useWeightedAmbigousMatches) {
                        float min=Math.min(mismatchTransition, mismatchTransversion);                        
                        value = (float) (min + state1.fractionEqual(state2)*(match-min));
                    }
                    else {
                        value = ambiguousMatch;
                    }
                }
                else if (
                    (Nucleotides.isPurine(state1) && Nucleotides.isPurine(state2)) ||
                    (Nucleotides.isPyrimidine(state1) && Nucleotides.isPyrimidine(state2)) ) {
                        value = mismatchTransition;
                    } else {
                    value = mismatchTransversion;
                }

               /* float val = (i == j) ? match :
                        ((isPurine(i) == isPurine(j)) ? mismatchTransition : mismatchTransversion);
               */
                scores[i][j] = value;
            }
        }
        buildScores(scores);
    }

   /*
    private boolean isPurine(int state) {
        return Nucleotides.isPurine(Nucleotides.CANONICAL_STATES[state]);
    }
    */

    @Override
	public String getName() {
        return name;
    }

    @Override
	public final String getAlphabet() {
        return alphabet + getExtraResidues ();
    }

    @Override
	public String toString() {
        String result = match + "/" + mismatchTransition;
        if(mismatchTransversion != mismatchTransition) {
            result = result + "/" + mismatchTransversion;
        }
        if(name.length()>  0){
            result = name + " (" + result + ")";
        }
        return result;
    }

	public Float getAmbiguousMatch() {
		return ambiguousMatch;
	}

	public void setAmbiguousMatch(Float ambiguousMatch) {
		this.ambiguousMatch = ambiguousMatch;
	}

	public Float getMatch() {
		return match;
	}

	public void setMatch(Float match) {
		this.match = match;
	}

	public Float getMisMatch() {
		return misMatch;
	}

	public void setMisMatch(Float misMatch) {
		this.misMatch = misMatch;
	}

	public Float getMismatchTransition() {
		return mismatchTransition;
	}

	public void setMismatchTransition(Float mismatchTransition) {
		this.mismatchTransition = mismatchTransition;
	}

	public Float getMismatchTransversion() {
		return mismatchTransversion;
	}

	public void setMismatchTransversion(Float mismatchTransversion) {
		this.mismatchTransversion = mismatchTransversion;
	}


	public void setName(String name) {
		this.name = name;
	}

	public Double getPercentmatches() {
		return percentmatches;
	}

	public void setPercentmatches(Double percentmatches) {
		this.percentmatches = percentmatches;
	}

	public Scores getScores() {
		return scores;
	}

	public void setScores(Scores scores) {
		this.scores = scores;
	}

	public Boolean getUseWeightedAmbigousMatches() {
		return useWeightedAmbigousMatches;
	}

	public void setUseWeightedAmbigousMatches(Boolean useWeightedAmbigousMatches) {
		this.useWeightedAmbigousMatches = useWeightedAmbigousMatches;
	}

	private Scores scores;
	private Boolean useWeightedAmbigousMatches;
	private Double percentmatches;
	private Float ambiguousMatch;
	private Float misMatch;

}