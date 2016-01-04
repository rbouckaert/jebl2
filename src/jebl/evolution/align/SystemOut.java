package jebl.evolution.align;

public class SystemOut extends Output {

    @Override
	public final void print(String s) { System.out.print(s); }
    @Override
	public final void println(String s) { System.out.println(s); }
    @Override
	public final void println() { System.out.println(); }

}