package jebl.evolution.align;

import beast.core.Param;
import jebl.evolution.align.scores.Scores;

// Alignment with affine gap costs; smart linear-space algorithm

abstract class AlignLinearSpaceAffine extends AlignAffine {

    float[][][] F;  // the matrices used to compute the alignment
    
    public AlignLinearSpaceAffine(
		@Param(name="sub", description="auto converted jebl2 parameter") Scores sub,
		@Param(name="openGapPenalty", description="auto converted jebl2 parameter") Float openGapPenalty,
		@Param(name="extendGapPenalty", description="auto converted jebl2 parameter") Float extendGapPenalty) {
    	super(sub, openGapPenalty, extendGapPenalty);
    }
    
    /**
     * Performs the alignment. Abstract.
     * 
     * @param sq1
     * @param sq2
     */
    @Override
	public abstract void doAlignment(String sq1, String sq2);
    
    @Override
	public void prepareAlignment(String sq1, String sq2) {
    	this.n = sq1.length(); this.m = sq2.length();
		this.seq1 = sq1;
        this.seq2 = sq2;
    	F = new float[3][2][m+1];
    }

    /**
     * Print matrix used to calculate this alignment.
     * 
     * @param out Output to print to.
     */
    @Override
	public void printf(Output out) {
    	for (int k=0; k<3; k++) {
    		out.println("F[" + k + "]:");
    		for (int j=0; j<=m; j++) {
    			for (int i=0; i<F[k].length; i++)
    				out.print(padLeft(formatScore(F[k][i][j]), 5));
    			out.println();
    		}
    	}
    }

    static void swap01(Object[] A)
    { Object tmp = A[1]; A[1] = A[0]; A[0] = tmp; }

	@Override
	public Float getExtendGapPenalty() {
		return extendGapPenalty;
	}

	@Override
	public void setExtendGapPenalty(Float extendGapPenalty) {
		this.extendGapPenalty = extendGapPenalty;
	}

	@Override
	public Float getOpenGapPenalty() {
		return openGapPenalty;
	}

	@Override
	public void setOpenGapPenalty(Float openGapPenalty) {
		this.openGapPenalty = openGapPenalty;
	}

	@Override
	public Scores getSub() {
		return sub;
	}

	@Override
	public void setSub(Scores sub) {
		this.sub = sub;
	}

}