import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
	private JLabel status;
	private JTextField sw,nm;
	private JButton reset;
	private JButton flag;
	private JPanel minefield;
	
	public MainWindow(){
		//close this after clicking on x button
		this.setLayout(null);
		this.getContentPane().setBackground(new Color(238,238,238));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 600);
		this.setTitle("Mine Sweeper");
		this.setIconImage((new ImageIcon("mine.png")).getImage());
	
		status = new JLabel();
		status.setVerticalAlignment(JLabel.CENTER);
		status.setForeground(Color.WHITE);
		status.setOpaque(true);
		status.setHorizontalAlignment(JLabel.CENTER);
		status.setBounds(20, 10, 950, 80);
		status.setIcon(new ImageIcon((new ImageIcon("Logo.png")).getImage().getScaledInstance(930, 70, Image.SCALE_SMOOTH)));
		this.add(status);
		
		JLabel tm = new JLabel("Time:");
		tm.setFont(new Font("Sansserif",Font.BOLD,21));
		tm.setOpaque(true);
		tm.setVerticalAlignment(JLabel.CENTER);
		tm.setBounds(20,100,100,40);
		this.add(tm);
		
		sw = new JTextField();
		sw.setFont(new Font("Sansserif",Font.BOLD,21));
		sw.setText("-- : -- : --");
		sw.setBackground(Color.WHITE);
		sw.setEditable(false);
		sw.setBounds(120, 100, 150, 40);
		sw.setHorizontalAlignment(JTextField.CENTER);	
		this.add(sw);
		
		reset = new JButton();
		reset.setFocusable(false);
		reset.setBackground(new Color(210,210,210));
		reset.setBounds(450,100,40,40);
		this.add(reset);
				
		flag = new JButton();
		flag.setFocusable(false);
		flag.setBackground(new Color(210,210,210));
		flag.setBounds(525,100,40,40);
		this.add(flag);
		
		JLabel mn = new JLabel("Mines:");
		mn.setFont(new Font("Sansserif",Font.BOLD,21));
		mn.setOpaque(true);
		mn.setVerticalAlignment(JLabel.CENTER);
		mn.setBounds(810,100,100,40);
		this.add(mn);
		
		nm = new JTextField();
		nm.setFont(new Font("Sansserif",Font.BOLD,21));
		nm.setText("0");
		nm.setBackground(Color.WHITE);
		nm.setEditable(false);
		nm.setBounds(910, 100, 60, 40);
		nm.setHorizontalAlignment(JTextField.CENTER);	
		this.add(nm);
		
		minefield = new JPanel();
		minefield.setBounds(20,150,950,400);
		minefield.setLayout(new GridLayout(16,38));
		minefield.setBackground(new Color(170,170,170));		
		this.add(minefield);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		

	}
	public JTextField getSw() {
		return sw;
	}
	public JLabel getStatus() {
		return status;
	}
	public JButton getReset() {
		return reset;
	}
	public JButton getFlag() {
		return flag;
	}
	public JPanel getMinefield() {
		return minefield;
	}
	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
	}
	public JTextField getNm() {
		return nm;
	}
}	
