import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;

public class Genetic {
	public double error_porcentage, error;
	public double[][] ref;
	public double[] limx, limy;
	public int mx, my, press, nvec;
	public Random rand;
	public ArrayList<String> vectors;

	public Genetic(double[][] ref,int nvec ,double error_porcentage, int press){
		this.ref = ref;
		this.press = press;
		this.nvec = nvec;
		this.error_porcentage = error_porcentage;
		limx = new double[2];
		limy = new double[2];
		rand  = new Random();
		vectors = new ArrayList<String>(nvec);
		getError();
		getLims();
		getMs();
	}

	public void getLims(){
		double xmin = ref[0][0], xmax = ref[0][0],ymin = ref[0][1], ymax = ref[0][1];
		for(int i = 1; i < 4; i++){
			xmin = xmin < ref[i][0] ? xmin : ref[i][0];
			xmax = xmax > ref[i][0] ? xmax : ref[i][0];

			ymin = ymin < ref[i][1] ? ymin : ref[i][1];
			ymax = ymax > ref[i][1] ? ymax : ref[i][1];
		}
		limx[0] = xmin;
		limx[1] = xmax;
		limy[0] = ymin;
		limy[1] = ymax;
	}
 
	public static String toBase(String n, int b1, int b2){
		return Integer.toString(Integer.parseInt(n,b1),b2);
	}

	public void getMs(){
		mx = (int)Math.round(lg((limx[1] - limx[0]) * Math.pow(10, press)) / lg(2));
		my = (int)Math.round(lg((limy[1] - limy[0]) * Math.pow(10, press)) / lg(2));
	}

	public static double lg(double n){
		return (Math.log(n) / Math.log(2));
	}

	public String makeVector(){
		String v = "";
		for(int i = 0; i < mx + my; i++){
			v += rand.nextInt(2);
		}
		return v;
	}

	public void makeVectors(){
		for(int i = 0; i < nvec; i++){
			System.out.println(i);
			String v = makeVector();
			double x = vecToX(v);
			double y = vecToY(v);
			while(!isValid(x,y)){
				v = makeVector();
				x = vecToX(v);
				y = vecToY(v);
			}
			vectors.add(v);
		}
	}

	public double vecToX(String v){
		String x = v.substring(0,mx);
		double n = Integer.parseInt(toBase(x,2,10));
		return n * ((limx[1] - limx[0]) / (Math.pow(2,mx)-1)) + limx[0];
	}

	public double vecToY(String v){
		String y = v.substring(mx + 1,mx + my);
		double n = Integer.parseInt(toBase(y,2,10));
		return n * ((limy[1] - limy[0]) / (Math.pow(2,my)-1)) + limy[0];
	}

	public void iterate(int iterations){
		makeVectors();
		double zp = 0, zt = 0, zaux = 0;
		ArrayList<String> sec = new ArrayList<String>();
		double[] zvalues = new double[nvec];
		double[] zacum = new double[nvec];
		double[] rands = new double[nvec];
		for(int k = 0; k < iterations; k++){
			System.out.println(k+":");
			for(int i = 0; i < nvec; i++){
				String v = vectors.get(i);
				double x = vecToX(v);
				double y = vecToY(v);
				zvalues[i] = getZ(x,y);	
				zt += zvalues[i];
				System.out.println(i+" |"+v+" |"+x+" |"+y+" |"+zvalues[i]);
			}
			System.out.println("\t\t\t\t\t"+zt);
			for(int i = 0; i < nvec; i++){
				zp = zvalues[i]/zt;
				zaux += zp;
				zacum[i] = zaux;
				rands[i] = rand.nextDouble();
				System.out.println(i+ " |"+zp + " |"+ zaux+" |"+rands[i]);
			}
			for(int i = 0; i < nvec; i++){
				int j = 0;
				while(zacum[j] < rands[i]){
					j++;
				}
				if(!sec.contains(vectors.get(j))){
					sec.add(vectors.get(j));
				}
			}
			int len = sec.size();
			while(len < nvec){
				String vec = mutate(sec.get(0));
				while(!isValid(vec)){
					vec = mutate(sec.get(rand.nextInt(len)));
				}
				sec.add(vec);
				len++;
			}
			vectors = new ArrayList<String>(sec);
			zt = 0;
			zaux = 0;
			sec.clear();
		}
	}

	public String mutate(String vector){
		char[] v = vector.toCharArray();
		v[rand.nextInt(mx+my)] = (char)(rand.nextInt(2) + 48);
		return new String(v);
	}

	public void getError(){
		error = -(getZ(0,0)/nvec) * error_porcentage;
	}

	public double getZ(double x, double y){
		double z = 0f;
		for(int i = 0; i < 4; i++){
			double auxx = Math.pow(x - ref[i][0], 2);
			double auxy = Math.pow(y - ref[i][1], 2);
			z += Math.pow(auxx + auxy - Math.pow(ref[i][2],2),2);
		}
		return -z;
	}

	public boolean isValid(double x, double y){
		for(int i = 0; i < 4; i++){
			double n = (Math.pow(x - ref[i][0],2) + Math.pow(y - ref[i][1],2) - Math.pow(ref[i][2],2));
			if(!(n <= error)){
				return false;
			}
		}
		return true;
	}

	public boolean isValid(String v){
		double x = vecToX(v);
		double y = vecToY(v);
		return isValid(x,y);
	}
}