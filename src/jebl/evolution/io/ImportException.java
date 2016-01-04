package jebl.evolution.io;

/**
 * @author Andrew Rambaut
 * @author Alexei Drummond
 *
 * @version $Id: ImportException.java 838 2007-11-09 04:07:00Z twobeers $
 */
public class ImportException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImportException() { super(); }
	public ImportException(String message) { super(message); }
    public ImportException(String message, Throwable cause) { super(message, cause); }
    public String userMessage() { return getMessage(); }

    public static class DuplicateFieldException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public DuplicateFieldException() { super(); }
		public DuplicateFieldException(String message) { super(message); }
	}

	public static class BadFormatException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public BadFormatException() { super(); }
		public BadFormatException(String message) { super(message); }
	}

	public static class UnparsableDataException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public UnparsableDataException() { super(); }
		public UnparsableDataException(String message) { super(message); }
	}

	public static class MissingFieldException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public MissingFieldException() { super(); }
		public MissingFieldException(String message) { super(message); }
        @Override
		public String userMessage() { return "Unsupported value for field " + getMessage(); }
	}

	public static class ShortSequenceException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ShortSequenceException() { super(); }
		public ShortSequenceException(String message) { super(message); }
        @Override
		public String userMessage() { return "Sequence is too short: " + getMessage(); }
    }

	public static class TooFewTaxaException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public TooFewTaxaException() { super(); }
		public TooFewTaxaException(String message) { super(message); }
        @Override
		public String userMessage() { return "Number of taxa is less than expected: " +
                (getMessage() != null ? getMessage() : ""); }
    }

    public static class DuplicateTaxaException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public DuplicateTaxaException() { super(); }
		public DuplicateTaxaException(String message) { super(message); }
	}

    public static class UnknownTaxonException extends ImportException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public UnknownTaxonException() { super(); }
		public UnknownTaxonException(String message) { super(message); }
	}


}