package jebl.evolution.trees;

import beast.core.BEASTObject;
import beast.core.Param;
import jebl.evolution.taxa.Taxon;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * data structure for a set of splits
 *
 * @version $Id: SplitSystem.java 317 2006-05-03 23:42:12Z alexeidrummond $
 *
 * @author Korbinian Strimmer
 */
public class SplitSystem extends BEASTObject {
	//
	// Public stuff
	//

	/**
	 * @param taxa  the list of taxa
	 * @param size     number of splits
	 */
	public SplitSystem(
		@Param(name="taxa", description="auto converted jebl2 parameter") final Collection<Taxon> taxa,
		@Param(name="size", description="auto converted jebl2 parameter") Integer size)
	{
		this.taxa = Collections.unmodifiableList(new ArrayList<>(taxa));

		labelCount = taxa.size();
		splitCount = size;

		splits = new boolean[splitCount][labelCount];
		
		this.size = size;
	}

	/** get number of splits */
	public int getSplitCount()
	{
		return splitCount;
	}

	/** get number of labels */
	public int getLabelCount()
	{
		return labelCount;
	}

	/** get split vector */
	public boolean[][] getSplitVector()
	{
		return splits;
	}

	/** get split */
	public boolean[] getSplit(int i)
	{
		return splits[i];
	}


	/** get taxon list */
	public List<Taxon> getTaxa() { return taxa; }

	/**
	  + test whether a split is contained in this split system
	  * (assuming the same leaf order)
	  *
	  * @param split split
	  */
	public boolean hasSplit(boolean[] split)
	{
		for (int i = 0; i < splitCount; i++)
		{
			if (SplitUtils.isSame(split, splits[i])) return true;
		}

		return false;
	}


	/** print split system */
	@Override
	public String toString()
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		for (int i = 0; i < labelCount; i++)
		{
			pw.println(taxa.get(i));
		}
		pw.println();


		for (int i = 0; i < splitCount; i++)
		{
			for (int j = 0; j < labelCount; j++)
			{
				if (splits[i][j] == true)
					pw.print('*');
				else
					pw.print('.');
			}

			pw.println();
		}

		return sw.toString();
	}


	//
	// Private stuff
	//

	private int labelCount, splitCount;
	private List<Taxon> taxa;
	private boolean[][] splits;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}


	public void setTaxa(final Collection<Taxon> taxa) {
		this.taxa.clear();
		this.taxa.addAll(taxa);
	}
	
	public void setTaxa(Taxon taxa) {
		this.taxa.add(taxa);
	}

	private int size;

	@Override
	public void initAndValidate() {
		// nothing to do
		
	}

}