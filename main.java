public class main {
	public static void main(String[] args) {
		double[] r1 = {3,7.85,3};
		double[] r2 = {-3.5,3.5,5.8};
		double[] r3 = {1.75,-3.5,8.5};
		double[] r4 = {5.2,-0.5,6.25};
		double[][] ref = {r1,r2,r3,r4};

		Genetic g = new Genetic(ref,15,0.05,4);
		g.iterate(100);
	}
}