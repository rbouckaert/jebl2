package jebl.evolution.alignments;

import beast.core.Param;
import beast.util.Randomizer;

/**
 * Date: 17/01/2006
 * Time: 08:18:32
 *
 * @author Joseph Heled
 * @version $Id: JackknifedAlignment.java 940 2008-08-26 00:32:47Z stevensh $
 *
 */
public class JackknifedAlignment extends ResampledAlignment {

    public JackknifedAlignment(
		@Param(name="srcAlignment", description="auto converted jebl2 parameter") Alignment srcAlignment,
		@Param(name="percent", description="auto converted jebl2 parameter") Double percent,
		@Param(name="seed", description="auto converted jebl2 parameter") long seed){
    	Randomizer.setSeed(seed);
        final int nSites = srcAlignment.getSiteCount();
        final int nNewSites = (int)Math.ceil(nSites * percent);
        int[] sites = new int[nSites];

        for(int n = 0; n < nSites; ++n) {
            sites[n] = n;
        }

         shuffle(sites);

        int[] newSites = new int[nNewSites];
        System.arraycopy(sites, 0, newSites, 0, nNewSites);
        init(srcAlignment, newSites);

    	this.percent = percent;
    	this.seed = seed;
    	this.srcAlignment = srcAlignment;
    }

    public JackknifedAlignment(
		@Param(name="srcAlignment", description="auto converted jebl2 parameter") Alignment srcAlignment,
		@Param(name="percent", description="auto converted jebl2 parameter") Double percent) {
         this(srcAlignment, percent, Randomizer.getSeed());
    }

    /**
     * Shuffles an array.
     */
    private void shuffle(int[] array) {
        int l = array.length;
        for (int i = 0; i < l; i++) {
            int index = Randomizer.nextInt(l-i) + i;
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Long getSeed() {
		return seed;
	}

	public void setSeed(Long seed) {
		this.seed = seed;
	}

	public Alignment getSrcAlignment() {
		return srcAlignment;
	}

	public void setSrcAlignment(Alignment srcAlignment) {
		this.srcAlignment = srcAlignment;
	}
	
	private Double percent;
	private long seed;
	private Alignment srcAlignment;
}