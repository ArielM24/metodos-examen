import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Genetic {
	public double error_porcentage, error;
	public double[][] ref, results;
	public double[] limx, limy, optimus;
	public int mx, my, press, nvec, nref, timeOut = 60000;
	public Random rand;
	public ArrayList<String> vectors;
	public boolean greaterOptimus = false, iterating = false;


	public Genetic(double[][] ref,int nvec ,double error_porcentage, int press){
		this.ref = ref;
		this.nref = ref.length;
		this.press = press;
		this.nvec = nvec;
		this.error_porcentage = error_porcentage;
		this.results = new double[nvec][3];
		this.optimus = new double[4];
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
		for(int i = 1; i < nref; i++){
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

	public double[] getOptimus(){
		for(int i = 0; i < nvec; i++){
			if(greaterOptimus){
				if(results[i][2] > optimus[2]){
					optimus[0] = results[i][0];
					optimus[1] = results[i][1];
					optimus[2] = results[i][2];
					optimus[3] = i;
				} 
			}else{
				if(results[i][2] < optimus[2]){
					optimus[0] = results[i][0];
					optimus[1] = results[i][1];
					optimus[2] = results[i][2];
					optimus[3] = i;
				} 
			}

		}
		return optimus;	
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
		iterating = true;
		makeVectors();
		try{
			Thread t1 = new Thread(){
				@Override
				public void run(){
					double zp = 0, zt = 0, zaux = 0;
					ArrayList<String> sec = new ArrayList<String>(), str = new ArrayList<String>();
					double[] zvalues = new double[nvec];
					double[] zacum = new double[nvec];
					double[] rands = new double[nvec];
					for(int k = 0; k < iterations; k++){
						if(isInterrupted()){
							System.out.println("Interrupted :"+getName());
							break;
						}

						for(int i = 0; i < nvec; i++){
							if(isInterrupted()){
								System.out.println("Interrupted :"+getName());
								break;
							}
							String v = vectors.get(i);
							double x = vecToX(v);
							double y = vecToY(v);
							zvalues[i] = getZ(x,y);	
							zt += zvalues[i];
							results[i][0] = x;
							results[i][1] = y;
							results[i][2] = zvalues[i];

							str.add(i+" |"+v+" |"+x+" |"+y+" |"+zvalues[i]);
						}
						for(int i = 0; i < nvec; i++){
							if(isInterrupted()){
								System.out.println("Interrupted :"+getName());
								break;
							}
							zp = zvalues[i]/zt;
							zaux += zp;
							zacum[i] = zaux;
							rands[i] = rand.nextDouble();
							str.set(i,str.get(i) + " |"+zp + " |"+ zaux+" |"+rands[i]);
						}
						for(int i = 0; i < nvec; i++){
							if(isInterrupted()){
								System.out.println("Interrupted :"+getName());
								break;
							}
							int j = 0;
							while(zacum[j] < rands[i]){
								j++;
							}
							str.set(i,str.get(i) + " |" + j);
							if(!sec.contains(vectors.get(j))){
								sec.add(vectors.get(j));
							}
						}
						int len = sec.size();
						while(len < nvec){
							if(isInterrupted()){
								System.out.println("Interrupted :"+getName());
								break;
							}
							String vec = mutate(sec.get(0));
							while(!isValid(vec)){
								vec = mutate(sec.get(rand.nextInt(len)));
							}
							sec.add(vec);
							len++;
						}
						str.add(0,getName()+"\t"+k+":\nid   |vector\t|x\t|y\t|z\t|%z\t|%zacum\t|rand\t|next");
						str.add("\t\t\t\t\tZsum: "+zt);
						for(String s: str){
							System.out.println(s);
						}
						vectors = new ArrayList<String>(sec);
						zt = 0;
						zaux = 0;
						sec.clear();
						str.clear();
					}
					iterating = false;
				}
			};
			t1.start();

			Timer t = new Timer(timeOut, new ActionListener (){
				@Override
				public void actionPerformed(ActionEvent e){
					t1.interrupt();
					iterating = false;
					System.out.println("killed");
				}
			});
			t.setRepeats(false);
			t.start();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String mutate(String vector){
		char[] v = vector.toCharArray();
		char index = (char)rand.nextInt(mx+my);
		v[index] = v[index] == '0' ? '1': '0';
		return new String(v);
	}

	public void getError(){
		error = -(getZ(0,0)/nref) * error_porcentage;
	}

	public double getZ(double x, double y){
		double z = 0f;
		for(int i = 0; i < nref; i++){
			double auxx = Math.pow(x - ref[i][0], 2);
			double auxy = Math.pow(y - ref[i][1], 2);
			z += Math.pow(auxx + auxy - Math.pow(ref[i][2],2),2);
		}
		return -z;
	}

	public boolean isValid(double x, double y){
		for(int i = 0; i < nref; i++){
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