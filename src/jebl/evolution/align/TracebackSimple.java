package jebl.evolution.align;

import beast.core.Param;

class TracebackSimple extends Traceback {

    public TracebackSimple(
		@Param(name="i", description="auto converted jebl2 parameter") Integer i,
		@Param(name="j", description="auto converted jebl2 parameter") Integer j) {
        this.i = i; this.j = j;
    }
    
    public final void setTraceback(int i, int j) {
    	this.i = i;
    	this.j = j;
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

}