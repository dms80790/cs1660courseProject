package hadoop_project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class SelectActionFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectActionFrame frame = new SelectActionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SelectActionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Engine was loaded and inverted indecies were constructed successfully!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(87, 62, 886, 73);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please Select Action");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_1.setBounds(353, 189, 251, 45);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Search For Term");
		btnNewButton.setBounds(393, 251, 168, 53);
		contentPane.add(btnNewButton);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("David's Search Engine");
		textPane.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		textPane.setBackground(SystemColor.menu);
		textPane.setBounds(15, 0, 198, 26);
		contentPane.add(textPane);
	}
}