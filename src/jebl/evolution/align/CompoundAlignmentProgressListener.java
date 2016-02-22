package jebl.evolution.align;

import beast.core.BEASTObject;
import beast.core.Param;
import jebl.util.ProgressListener;

/**
 * @author Matt Kearse
 * @version $Id: CompoundAlignmentProgressListener.java 1057 2010-05-24 03:20:46Z matt_kearse $
 */
class CompoundAlignmentProgressListener extends BEASTObject  {
    //private boolean cancelled = false;
    private int sectionsCompleted = 0;
    private int totalSections;
    private ProgressListener progress;
    private int sectionSize= 1;

    public CompoundAlignmentProgressListener(
		@Param(name="progress", description="auto converted jebl2 parameter") ProgressListener progress,
		@Param(name="totalSections", description="auto converted jebl2 parameter") Integer totalSections) {
        this.totalSections = totalSections;
        this.progress = progress;
    }

    public void setSectionSize(int size) {
        this.sectionSize = size;
    }

    public void incrementSectionsCompleted(int count) {
        sectionsCompleted += count;
    }

    public boolean isCanceled() {
//        return cancelled;
        return progress.isCanceled();
    }

    public ProgressListener getMinorProgress() {
        return minorProgress;
    }

    private ProgressListener minorProgress = new ProgressListener() {
        @Override
		protected void _setProgress(double fractionCompleted) {
            assert fractionCompleted>=0 && fractionCompleted<=1;
//            System.out.println("progress =" + fractionCompleted+ " sections =" + sectionsCompleted+ "/" + totalSections);
            double totalProgress = (sectionsCompleted + fractionCompleted*sectionSize) / totalSections;
            // if( totalProgress > 1.0 )  System.out.println(totalProgress);
            progress.setProgress(totalProgress);
        }

        @Override
		protected void _setIndeterminateProgress() {
            progress.setIndeterminateProgress();
        }

        @Override
		protected void _setMessage(String message) {
            progress.setMessage(message);
        }

        @Override
		public boolean isCanceled() {
            return progress.isCanceled();
        }
    };

	public ProgressListener getProgress() {
		return progress;
	}

	/** should not be used other than in BEAST framework **/
	@Deprecated
	public void setProgress(ProgressListener progress) {
		this.progress = progress;
	}

	public Integer getTotalSections() {
		return totalSections;
	}

	public void setTotalSections(Integer totalSections) {
		this.totalSections = totalSections;
	}

	@Override
	public void initAndValidate() {
		// nothing to do
		
	}

}