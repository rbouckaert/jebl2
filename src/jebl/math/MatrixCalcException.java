package jebl.math;


public class MatrixCalcException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MatrixCalcException() { super(); }
	public MatrixCalcException(String message) { super(message); }
	
	public static class NotSquareMatrixException extends MatrixCalcException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public NotSquareMatrixException() { super(); }
		public NotSquareMatrixException(String message) { super(message); }
	}
	public static class PositiveDefiniteException extends MatrixCalcException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public PositiveDefiniteException() { super(); }
		public PositiveDefiniteException(String message) { super(message); }
	}
}
