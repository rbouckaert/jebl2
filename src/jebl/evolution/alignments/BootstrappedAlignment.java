package jebl.evolution.alignments;

import beast.core.Param;
import beast.util.Randomizer;

/**
 * Date: 15/01/2006
 * Time: 10:13:50
 *
 * @author Joseph Heled
 * @version $Id: BootstrappedAlignment.java 940 2008-08-26 00:32:47Z stevensh $
 *
 */
public class BootstrappedAlignment extends ResampledAlignment {

    public BootstrappedAlignment(
		@Param(name="srcAlignment", description="auto converted jebl2 parameter") Alignment srcAlignment,
		@Param(name="seed", description="auto converted jebl2 parameter") long seed) {
    	Randomizer.setSeed(seed);
        final int nSites = srcAlignment.getSiteCount();
        int[] sites = new int[nSites];

        for(int n = 0; n < nSites; ++n) {
            sites[n] = Randomizer.nextInt(nSites);
        }

        init(srcAlignment, sites);
        
        this.srcAlignment = srcAlignment;
        this.seed = seed;
    }
    

    public BootstrappedAlignment(
		@Param(name="srcAlignment", description="auto converted jebl2 parameter") Alignment srcAlignment) {
        this(srcAlignment, Randomizer.getSeed());
    }

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public Alignment getSrcAlignment() {
		return srcAlignment;
	}

	public void setSrcAlignment(Alignment srcAlignment) {
		this.srcAlignment = srcAlignment;
	}

	private Alignment srcAlignment;
	private long seed;
}