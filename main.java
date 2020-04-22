import javax.swing.UIManager;

public class main {
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		window w = new window();
		w.setBounds(0,0,700,350);
		w.setLocationRelativeTo(null);
		w.setResizable(false);
		w.setVisible(true);
	}
}