import javax.swing.*;
import java.util.ArrayList;

public class window extends JFrame {
	private JButton btnOk, btnClr;
	private JTextField tfx1,tfx2,tfx3,tfx4, tfy1, tfy2, tfy3, tfy4;
	private JTextField tfd1, tfd2, tfd3, tfd4,tfite,tfpob,tfind,tfe;
	private JLabel lblx1,lblx2,lblx3,lblx4, lbly1, lbly2, lbly3, lbly4;
	private JLabel lbld1, lbld2, lbld3, lbld4,lblite,lblpob,lblind,lble;
	private JScrollPane sp;
	private JTextArea ta;
	private int ite, pob, ind;
	private double error;
	public window(){
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Trilateration");
		initComp();
	}

	private void initComp(){
		initLabel();
		initTf();

		btnOk = new JButton("Ok");
		btnOk.setBounds(10,250,100,30);
		add(btnOk);
		btnOk.addActionListener(e->btnOkClick());

		btnClr = new JButton("Limpiar");
		btnClr.setBounds(120,250,100,30);
		add(btnClr);
		btnClr.addActionListener(e->btnClrClick());

		ta = new JTextArea();
		ta.setBounds(350, 10, 330, 300);

		sp = new JScrollPane(ta);
		sp.setBounds(350, 10, 330, 300);
		add(sp);

	}

	private void initLabel(){
		lblx1 = new JLabel("x1:");
		lblx1.setBounds(10,10,70,30);
		add(lblx1);
		lblx2 = new JLabel("x2:");
		lblx2.setBounds(10,50,70,30);
		add(lblx2);
		lblx3 = new JLabel("x3:");
		lblx3.setBounds(10,90,70,30);
		add(lblx3);
		lblx4 = new JLabel("x4:");
		lblx4.setBounds(10,130,70,30);
		add(lblx4);

		lbly1 = new JLabel("y1:");
		lbly1.setBounds(120,10,70,30);
		add(lbly1);
		lbly2 = new JLabel("y2:");
		lbly2.setBounds(120,50,70,30);
		add(lbly2);
		lbly3 = new JLabel("y3:");
		lbly3.setBounds(120,90,70,30);
		add(lbly3);
		lbly4 = new JLabel("y4:");
		lbly4.setBounds(120,130,70,30);
		add(lbly4);

		lbld1 = new JLabel("d1:");
		lbld1.setBounds(230,10,70,30);
		add(lbld1);
		lbld2 = new JLabel("d2:");
		lbld2.setBounds(230,50,70,30);
		add(lbld2);
		lbld3 = new JLabel("d3:");
		lbld3.setBounds(230,90,70,30);
		add(lbld3);
		lbld4 = new JLabel("d4:");
		lbld4.setBounds(230,130,70,30);
		add(lbld4);

		lblite = new JLabel("Iteraciones:");
		lblite.setBounds(10,170,100,30);
		add(lblite);

		lblpob = new JLabel("Poblaciones:");
		lblpob.setBounds(160,170,100,30);
		add(lblpob);

		lblind = new JLabel("Individuos:");
		lblind.setBounds(10,210,100,30);
		add(lblind);

		lble = new JLabel("Err:");
		lble.setBounds(160,210,50,30);
		add(lble);
	}

	private void initTf(){
		tfx1 = new JTextField();
		tfx1.setBounds(40,10,70,30);
		add(tfx1);
		tfx2 = new JTextField();
		tfx2.setBounds(40,50,70,30);
		add(tfx2);
		tfx3 = new JTextField();
		tfx3.setBounds(40,90,70,30);
		add(tfx3);
		tfx4 = new JTextField();
		tfx4.setBounds(40,130,70,30);
		add(tfx4);

		tfy1 = new JTextField();
		tfy1.setBounds(150,10,70,30);
		add(tfy1);
		tfy2 = new JTextField();
		tfy2.setBounds(150,50,70,30);
		add(tfy2);
		tfy3 = new JTextField();
		tfy3.setBounds(150,90,70,30);
		add(tfy3);
		tfy4 = new JTextField();
		tfy4.setBounds(150,130,70,30);
		add(tfy4);

		tfd1 = new JTextField();
		tfd1.setBounds(260,10,70,30);
		add(tfd1);
		tfd2 = new JTextField();
		tfd2.setBounds(260,50,70,30);
		add(tfd2);
		tfd3 = new JTextField();
		tfd3.setBounds(260,90,70,30);
		add(tfd3);
		tfd4 = new JTextField();
		tfd4.setBounds(260,130,70,30);
		add(tfd4);

		tfite = new JTextField();
		tfite.setBounds(110,170,40,30);
		add(tfite);

		tfpob = new JTextField();
		tfpob.setBounds(270,170,40,30);
		add(tfpob);

		tfind = new JTextField();
		tfind.setBounds(110,210,40,30);
		add(tfind);

		tfe = new JTextField();
		tfe.setBounds(190,210,50,30);
		add(tfe);
	}

	private double[][] getData(){
		double[][] data = new double[4][3];
		try{
			data[0][0] = Double.parseDouble(tfx1.getText());
			data[1][0] = Double.parseDouble(tfx2.getText());
			data[2][0] = Double.parseDouble(tfx3.getText());
			data[3][0] = Double.parseDouble(tfx4.getText());

			data[0][1] = Double.parseDouble(tfy1.getText());
			data[1][1] = Double.parseDouble(tfy2.getText());
			data[2][1] = Double.parseDouble(tfy3.getText());
			data[3][1] = Double.parseDouble(tfy4.getText());

			data[0][2] = Double.parseDouble(tfd1.getText());
			data[1][2] = Double.parseDouble(tfd2.getText());
			data[2][2] = Double.parseDouble(tfd3.getText());
			data[3][2] = Double.parseDouble(tfd4.getText());

			ite = Integer.parseInt(tfite.getText());
			pob = Integer.parseInt(tfpob.getText());
			ind = Integer.parseInt(tfind.getText());
			error = Double.parseDouble(tfe.getText());
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Error en el formato de datos");
		}

		return data;
	}

	private void btnOkClick(){
		double[][] data = getData();
		ArrayList<Genetic> gens = new ArrayList<Genetic>();
		for(int i = 0; i < pob; i++){
			gens.add(new Genetic(data,ind,error,4));
			gens.get(i).iterate(ite);
		}

		
		Thread t = new Thread(){
			int sz = gens.size();

			boolean iter = true;
			@Override
			public void run(){
				iter = gens.get(sz-1).iterating;
				while(iter){
					iter = gens.get(sz-1).iterating;
					try{
						Thread.sleep(100*ite/2);
					}catch(Exception ex){	
						ex.printStackTrace();
					}
				}
				String res = "";
				for(int i = 0; i < sz; i++){
					Genetic g = gens.get(i);
					res += "Poblacion: "+i+" x: "+g.optimus[0]+" y: "+g.optimus[1]+" z: "+g.optimus[2]+"\n";
				}
				ta.setText(res);
			}
		};
		t.start();
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
	}
}