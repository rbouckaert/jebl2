/*
 * SequenceStateException.java
 *
 * (c) 2002-2005 JEBL Development Core Team
 *
 * This package may be distributed under the
 * Lesser Gnu Public Licence (LGPL)
 */
package jebl.evolution.sequences;

import beast.core.Param;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 *
 * @version $Id: SequenceStateException.java 185 2006-01-23 23:03:18Z rambaut $
 */
public class SequenceStateException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SequenceStateException(
		@Param(name="s", description="auto converted jebl2 parameter") String s) {
        super(s);
        this.s = s;
    }

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	private String s;
}