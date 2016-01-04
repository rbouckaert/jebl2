package jebl.evolution.align;

import jebl.util.ProgressListener;

/**
 * @author Matt Kearse
 * @version $Id: CompoundAlignmentProgressListener.java 1057 2010-05-24 03:20:46Z matt_kearse $
 */
class CompoundAlignmentProgressListener  {
    //private boolean cancelled = false;
    private int sectionsCompleted = 0;
    private int totalSections;
    private final ProgressListener progress;
    private int sectionSize= 1;

    public CompoundAlignmentProgressListener(ProgressListener progress, int totalSections) {
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
}
