package jebl.evolution.align;

import beast.core.BEASTObject;

public abstract class Traceback extends BEASTObject {

    int i, j;                     // absolute coordinates
    
    public final int getX() { return i; }

    public final int getY() { return j; }

    @Override
	public String toString() { return "("+getX() + ", " + getY()+")";};
	
	@Override
	public void initAndValidate() throws Exception {
		// nothing to do
	}
}
