package jebl.evolution.align;

import beast.core.Param;

class TracebackAffine extends Traceback {

    int k;

    public TracebackAffine(
		@Param(name="k", description="auto converted jebl2 parameter") Integer k,
		@Param(name="i", description="auto converted jebl2 parameter") Integer i,
		@Param(name="j", description="auto converted jebl2 parameter") Integer j) {

        this.k = k;
        this.i = i;
        this.j = j;
    }
    
    public final void setTraceback(int k, int i, int j) {
    	this.i = i;
    	this.j = j;
    	this.k = k;
    }

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Integer getJ() {
		return j;
	}

	public void setJ(Integer j) {
		this.j = j;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}


}