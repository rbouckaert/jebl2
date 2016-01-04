package jebl.evolution.sequences;

import beast.core.Param;

/**
 * @author rambaut
 *         Date: Jul 27, 2005
 *         Time: 12:48:31 AM
 */
public class GaplessSequence extends FilteredSequence {

	public GaplessSequence(
		@Param(name="source", description="auto converted jebl2 parameter") Sequence source) {
		super(source);
	}

	@Override
	protected State[] filterSequence(Sequence source) {
		return jebl.evolution.sequences.Utils.stripGaps(source.getStates());
	}


	@Override
	public Sequence getSource() {
		return source;
	}

	@Override
	public void setSource(Sequence source) {
		this.source = source;
	}

}