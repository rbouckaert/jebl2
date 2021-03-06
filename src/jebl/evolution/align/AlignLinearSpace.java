package jebl.evolution.align;

import beast.core.Param;
import jebl.evolution.align.scores.Scores;

abstract class AlignLinearSpace extends AlignSimple {

    float[][] F = null;		// the matrices used to compute the alignment

    public AlignLinearSpace(
		@Param(name="sub", description="auto converted jebl2 parameter") Scores sub,
		@Param(name="d", description="auto converted jebl2 parameter") Float d) {
    	super(sub, d);
    }

    /**
     * Performs the alignment. Abstract.
     * 
     * @param seq1
     * @param seq2
     */
    @Override
	public abstract void doAlignment(String seq1, String seq2);

    @Override
	public void prepareAlignment(String sq1, String sq2) {
    	this.n = sq1.length(); this.m = sq2.length();
		this.seq1 = strip(sq1); this.seq2 = strip(sq2);
    	F = new float[2][m+1];
    }

    /**
     * Print matrix used to calculate this alignment.
     * 
     * @param out Output to print to.
     */
    @Override
	public void printf(Output out) {
        for (int j=0; j<=m; j++) {
            for (int i = 0; i < F.length; i++) {
                float[] f = F[i];
                out.print(padLeft(formatScore(f[j]), 5));
            }
            out.println();
        }
    }

    static void swap01(Object[] A) {
        Object tmp = A[1]; A[1] = A[0]; A[0] = tmp;
    }

	@Override
	public Float getD() {
		return d;
	}

	@Override
	public void setD(Float d) {
		this.d = d;
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