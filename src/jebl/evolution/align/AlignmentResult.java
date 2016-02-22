package jebl.evolution.align;

import beast.core.BEASTObject;
import beast.core.Param;

/**
 * @author Matt Kearse
 * @version $Id: AlignmentResult.java 650 2007-03-12 20:09:10Z twobeers $
 *
 * Used for representing the results of a sequence alignment. Basically just stores
 * an array representing whether or not each position in the alignment is a gap or not.
 */
class AlignmentResult extends BEASTObject {
    int size;
    boolean values[];
    // true represents a character from the original sequence, and false represents a gap
    // for example:
    // original sequence: abcd
    // values: true, false, false, true, true, false, true
    // then the resulting alignment is: a--bc-d

    public AlignmentResult(
		@Param(name="size", description="auto converted jebl2 parameter") Integer size) {
        this.size = 0;
        // TT: Wouldn't a BitSet be better?
        values =new boolean[size];
    }

    public void append(String result) {
        for (char character : result.toCharArray()) {
            if(character =='-')
                values [ size ++ ]= false;
            else
                values [ size ++ ] = true;
        }
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(values[i] ? "X" : "-");

        }
        System.out.println ();
    }

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public void initAndValidate() {
		// nothing to do
	}

}