import javax.swing.*;
import java.util.ArrayList;

public class window extends JFrame {
	private JRadioButton rb3, rb4;
	private JCheckBox cb;
	private JButton btnOk, btnClr;
	private JTextField tfx1,tfx2,tfx3,tfx4, tfy1, tfy2, tfy3, tfy4;
	private JTextField tfd1, tfd2, tfd3, tfd4,tfite,tfpob,tfind,tfe;
	private JLabel lblx1,lblx2,lblx3,lblx4, lbly1, lbly2, lbly3, lbly4;
	private JLabel lbld1, lbld2, lbld3, lbld4,lblite,lblpob,lblind,lble;
	private JScrollPane sp;
	private JTextArea ta;
	private int ite, pob, ind, puntos = 4;
	private double error;
	public window(){
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Trilateration");
		initComp();
	}

	private void initComp(){
		initRb();
		initLabel();
		initTf();

		cb = new JCheckBox("Polares");
		cb.setBounds(230,10,100,30);
		add(cb);

		btnOk = new JButton("Ok");
		btnOk.setBounds(10,300,100,30);
		add(btnOk);
		btnOk.addActionListener(e->btnOkClick());

		btnClr = new JButton("Limpiar");
		btnClr.setBounds(120,300,100,30);
		add(btnClr);
		btnClr.addActionListener(e->btnClrClick());

		ta = new JTextArea();
		ta.setBounds(350, 60, 330, 300);

		sp = new JScrollPane(ta);
		sp.setBounds(350, 60, 330, 300);
		add(sp);

	}

	private void initRb(){
		ButtonGroup tg = new ButtonGroup();
		rb3 = new JRadioButton("3 puntos");
		rb3.setBounds(10,10,100,30);
		add(rb3);
		rb3.addActionListener(e-> rb3Click());
		rb4 = new JRadioButton("4 puntos");
		rb4.setBounds(120,10,100,30);
		add(rb4);
		rb4.addActionListener(e-> rb4Click());
		rb4.setSelected(true);
		tg.add(rb3);
		tg.add(rb4);
	}

	private void rb3Click(){
		tfx4.setEnabled(false);
		tfy4.setEnabled(false);
		tfd4.setEnabled(false);
		puntos = 3;
	}

	private void rb4Click(){
		tfx4.setEnabled(true);
		tfy4.setEnabled(true);
		tfd4.setEnabled(true);
		puntos = 4;
	}

	private void initLabel(){
		lblx1 = new JLabel("x1:");
		lblx1.setBounds(10,60,70,30);
		add(lblx1);
		lblx2 = new JLabel("x2:");
		lblx2.setBounds(10,100,70,30);
		add(lblx2);
		lblx3 = new JLabel("x3:");
		lblx3.setBounds(10,140,70,30);
		add(lblx3);
		lblx4 = new JLabel("x4:");
		lblx4.setBounds(10,180,70,30);
		add(lblx4);

		lbly1 = new JLabel("y1:");
		lbly1.setBounds(120,60,70,30);
		add(lbly1);
		lbly2 = new JLabel("y2:");
		lbly2.setBounds(120,100,70,30);
		add(lbly2);
		lbly3 = new JLabel("y3:");
		lbly3.setBounds(120,140,70,30);
		add(lbly3);
		lbly4 = new JLabel("y4:");
		lbly4.setBounds(120,180,70,30);
		add(lbly4);

		lbld1 = new JLabel("d1:");
		lbld1.setBounds(230,60,70,30);
		add(lbld1);
		lbld2 = new JLabel("d2:");
		lbld2.setBounds(230,100,70,30);
		add(lbld2);
		lbld3 = new JLabel("d3:");
		lbld3.setBounds(230,140,70,30);
		add(lbld3);
		lbld4 = new JLabel("d4:");
		lbld4.setBounds(230,180,70,30);
		add(lbld4);

		lblite = new JLabel("Iteraciones:");
		lblite.setBounds(10,220,100,30);
		add(lblite);

		lblpob = new JLabel("Poblaciones:");
		lblpob.setBounds(160,220,100,30);
		add(lblpob);

		lblind = new JLabel("Individuos:");
		lblind.setBounds(10,260,100,30);
		add(lblind);

		lble = new JLabel("Err:");
		lble.setBounds(160,260,50,30);
		add(lble);
	}

	private void initTf(){
		tfx1 = new JTextField();
		tfx1.setBounds(40,60,70,30);
		add(tfx1);
		tfx2 = new JTextField();
		tfx2.setBounds(40,100,70,30);
		add(tfx2);
		tfx3 = new JTextField();
		tfx3.setBounds(40,140,70,30);
		add(tfx3);
		tfx4 = new JTextField();
		tfx4.setBounds(40,180,70,30);
		add(tfx4);

		tfy1 = new JTextField();
		tfy1.setBounds(150,60,70,30);
		add(tfy1);
		tfy2 = new JTextField();
		tfy2.setBounds(150,100,70,30);
		add(tfy2);
		tfy3 = new JTextField();
		tfy3.setBounds(150,140,70,30);
		add(tfy3);
		tfy4 = new JTextField();
		tfy4.setBounds(150,180,70,30);
		add(tfy4);

		tfd1 = new JTextField();
		tfd1.setBounds(260,60,70,30);
		add(tfd1);
		tfd2 = new JTextField();
		tfd2.setBounds(260,100,70,30);
		add(tfd2);
		tfd3 = new JTextField();
		tfd3.setBounds(260,140,70,30);
		add(tfd3);
		tfd4 = new JTextField();
		tfd4.setBounds(260,180,70,30);
		add(tfd4);

		tfite = new JTextField();
		tfite.setBounds(110,220,40,30);
		add(tfite);

		tfpob = new JTextField();
		tfpob.setBounds(270,220,40,30);
		add(tfpob);

		tfind = new JTextField();
		tfind.setBounds(110,260,40,30);
		add(tfind);

		tfe = new JTextField();
		tfe.setBounds(190,260,50,30);
		add(tfe);
	}

	private double[][] getData(){
		double[][] data = new double[puntos][3];
		try{
			data[0][0] = Double.parseDouble(tfx1.getText());
			data[1][0] = Double.parseDouble(tfx2.getText());
			data[2][0] = Double.parseDouble(tfx3.getText());
			
			data[0][1] = Double.parseDouble(tfy1.getText());
			data[1][1] = Double.parseDouble(tfy2.getText());
			data[2][1] = Double.parseDouble(tfy3.getText());
			
			data[0][2] = Double.parseDouble(tfd1.getText());
			data[1][2] = Double.parseDouble(tfd2.getText());
			data[2][2] = Double.parseDouble(tfd3.getText());
			

			if(rb4.isSelected()){
				data[3][0] = Double.parseDouble(tfx4.getText());
				data[3][1] = Double.parseDouble(tfy4.getText());
				data[3][2] = Double.parseDouble(tfd4.getText());
			}

			ite = Integer.parseInt(tfite.getText());
			pob = Integer.parseInt(tfpob.getText());
			ind = Integer.parseInt(tfind.getText());
			error = Double.parseDouble(tfe.getText());
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Error en el formato de datos");
		}

		if(cb.isSelected()){
			data = convertPolar(data);
		}

		return data;
	}

	private double[][] convertPolar(double[][] polar){
		double[][] data = new double[puntos][3];
		for(int i = 0; i < puntos; i++){
			double[] xy = polarToCart(polar[i][0],polar[i][1]);
			data[i][0] = xy[0];
			data[i][1] = xy[1];
			data[i][2] = polar[i][2];
		}
		return data;
	}

	public static double[] polarToCart(double lat, double lon){
		double[] cart = new double[2];
		cart[1] = (40030.174/360) * Math.cos(lat * (2 * Math.PI / 360));
		cart[0] = cart[1] * lon;
		return cart;
	}

	public static double[] cartToPolar(double x, double y){
		double[] polar = new double[2];
		polar[0] = (360/(2 * Math.PI)) * Math.acos(y * (360/40030.174));
		polar[1] = x/y;
		return polar;
	}

	private void btnOkClick(){
		double[][] data = getData();
		ArrayList<Genetic> gens = new ArrayList<Genetic>();
		if(cb.isSelected()){
			error = error/1000000;
		}
		for(int i = 0; i < pob; i++){
			gens.add(new Genetic(data,ind,error,4));
			gens.get(i).iterate(ite);
		}
		int sz = gens.size();
		String res = "";
		for(int i = 0; i < sz; i++){
			Genetic g = gens.get(i);
			g.getOptimus();
			res += "Poblacion: "+i+" x: "+g.optimus[0]+" y: "+g.optimus[1]+" z: "+g.optimus[2]+"\n";
		}
		ta.setText(res + getAverage(gens));
	}

	private String getAverage(ArrayList<Genetic> gens){
		String res = "";
		double x = 0, y = 0, z = 0,xmin = 0, ymin = 0, zmin = 0, xmax = 0, ymax = 0, zmax = 0;
		for(int i = 0; i < gens.size(); i++){
			double[] g = gens.get(i).optimus;
			x += g[0];
			y += g[1];
			z += g[2];

			if(i == 0){
				xmin = g[0];
				ymin = g[1];
				zmin = g[2];

				xmax = g[0];
				ymax = g[1];
				zmax = g[2];
			}else{
				if(zmin > g[2]){
					zmax = zmin;
					xmax = xmin;
					ymax = ymin;

					xmin = g[0];
					ymin = g[1];
					zmin = g[2];
				}
			}

		}
		x /= gens.size();
		y /= gens.size();
		z /= gens.size();
		res += "min:\nx: "+xmin+" y: "+ymin+" z: "+zmin+"\n";
		res += "max:\nx: "+xmax+" y: "+ymax+" z: "+zmax+"\n";
		res += "promedio:\nx: "+x+" y: "+y+" z: "+z+"\n";

		if(cb.isSelected()){
			double[] prom = cartToPolar(x,y);
			double[] min = cartToPolar(xmin,ymin);
			double[] max = cartToPolar(xmax,ymax);
			res += "polar:\n";
			res += "min:\nlatiud: "+min[0]+" longitud: "+min[1]+" z: "+zmin+"\n";
			res += "max:\nlatiud: "+max[0]+" y: "+max[1]+" z: "+zmax+"\n";
			res += "promedio:\nlatiud: "+prom[0]+" longitud: "+prom[1]+" z: "+z+"\n";
		}
		return res;
	}

	private void btnClrClick(){
		tfx1.setText("");
		tfx2.setText("");
		tfx3.setText("");
		tfx4.setText("");

		tfy1.setText("");
		tfy2.setText("");
		tfy3.setText("");
		tfy4.setText("");

		tfd1.setText("");
		tfd2.setText("");
		tfd3.setText("");
		tfd4.setText("");

		tfite.setText("");
		tfpob.setText("");
		tfind.setText("");
		tfe.setText("");
		ta.setText("");
	}
}