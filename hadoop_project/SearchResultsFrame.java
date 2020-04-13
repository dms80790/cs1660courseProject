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
import javax.swing.JTable;

public class SearchResultsFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchResultsFrame frame = new SearchResultsFrame();
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
	public SearchResultsFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		textPane.setText("David's Search Engine");
		textPane.setBackground(SystemColor.menu);
		textPane.setBounds(15, 0, 198, 26);
		contentPane.add(textPane);
		
		JLabel lblNewLabel = new JLabel("You searched for the term:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(31, 61, 318, 35);
		contentPane.add(lblNewLabel);
		
		JLabel lblYourSearchWas = new JLabel("Your search was executed in ms");
		lblYourSearchWas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYourSearchWas.setBounds(31, 111, 302, 35);
		contentPane.add(lblYourSearchWas);
		
		table = new JTable();
		table.setBounds(157, 274, 581, 232);
		contentPane.add(table);
	}
}