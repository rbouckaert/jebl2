package jebl.evolution.sequences;

import beast.core.Param;

/**
 * @author rambaut
 *         Date: Jul 27, 2005
 *         Time: 12:48:31 AM
 */
public class TranslatedSequence extends FilteredSequence {

	public TranslatedSequence(
		@Param(name="source", description="auto converted jebl2 parameter") Sequence source,
		@Param(name="geneticCode", description="auto converted jebl2 parameter") GeneticCode geneticCode) {
		super(source);

		this.geneticCode = geneticCode;
	}

	@Override
	protected State[] filterSequence(Sequence source) {
		return jebl.evolution.sequences.Utils.translate(source.getStates(), geneticCode);
	}

    /**
     * @return the type of symbols that this sequence is made up of.
     */
    @Override
	public SequenceType getSequenceType() {
        return SequenceType.AMINO_ACID;
    }

	private GeneticCode geneticCode;


	public GeneticCode getGeneticCode() {
		return geneticCode;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setGeneticCode(GeneticCode geneticCode) {
		this.geneticCode = geneticCode;
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