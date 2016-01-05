// AminoAcidModel.java
//
// (c) 1999-2001 PAL Development Core Team
//
// This package may be distributed under the
// terms of the Lesser GNU General Public License (LGPL)

package jebl.evolution.substmodel;

import jebl.evolution.sequences.SequenceType;


/**
 * base class of rate matrices for amino acids
 *
 * @version $Id: AminoAcidModel.java 185 2006-01-23 23:03:18Z rambaut $
 *
 * @author Korbinian Strimmer
 */
public abstract class AminoAcidModel extends AbstractRateMatrix
{

    //
    // Protected stuff
    //

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor
    protected AminoAcidModel(Double[] f)
    {
        // Dimension = 20
        super(20);

        setSequenceType(SequenceType.AMINO_ACID);
        
    	double [] freqs = new double[f.length];
    	for (int i = 0 ; i < f.length; i++) {
    		freqs[i] = f[i];
    	}

        setFrequencies(freqs);
    }

}