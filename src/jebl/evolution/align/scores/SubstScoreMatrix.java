package jebl.evolution.align.scores;

import beast.core.Param;
import jebl.evolution.sequences.SequenceType;
import jebl.evolution.substmodel.RateMatrix;

/**
 * @author Alexei Drummond
 *
 * @version $Id: SubstScoreMatrix.java 360 2006-06-22 07:42:48Z pepster $
 */
public class SubstScoreMatrix extends Scores {

    SequenceType sequenceType;
    String alphabet;

    public SubstScoreMatrix(
		@Param(name="rateMatrix", description="auto converted jebl2 parameter") RateMatrix rateMatrix) {

        alphabet = SequenceType.Utils.getAlphabet(sequenceType);

        int m = alphabet.length();

        double[][] transProbs = new double[m][m];
        rateMatrix.getTransitionProbabilities(transProbs);

        buildScores(log(transProbs));
        this.rateMatrix = rateMatrix;
    }

    private float[][] log(double[][] values) {

        float[][] logValues = new float[values.length][values[0].length];

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                logValues[i][j] = (float)Math.log(values[i][j]);
            }
        }

        return logValues;
    }

    @Override
	public String getName() {
        return toString();
    }

    @Override
	public String getAlphabet() {
        return alphabet;
    }

	public RateMatrix getRateMatrix() {
		return rateMatrix;
	}

	public void setRateMatrix(RateMatrix rateMatrix) {
		this.rateMatrix = rateMatrix;
	}
	
	private RateMatrix rateMatrix;

}