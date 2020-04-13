package hadoop_project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class my_hadoop_gui {

	private JFrame frame;
	private JLabel lblMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					my_hadoop_gui window = new my_hadoop_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public my_hadoop_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Construct Inverted Indeces");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Hi, YouTube.......");
				lblMessage.setText("Hi, YouTube.......");
				//frame.dispose();
				//SecondFrame nextFrame = new SecondFrame();
				//nextFrame.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(370, 483, 270, 29);
		frame.getContentPane().add(btnNewButton);
		
		lblMessage = new JLabel("Load My Engine");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblMessage.setBounds(317, 153, 351, 78);
		frame.getContentPane().add(lblMessage);
		
		JButton btnNewButton_1 = new JButton("Choose Files");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_1.setBounds(414, 247, 148, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("David's Search Engine");
		textPane.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		textPane.setBackground(SystemColor.menu);
		textPane.setBounds(15, 0, 198, 26);
		frame.getContentPane().add(textPane);
	}
}