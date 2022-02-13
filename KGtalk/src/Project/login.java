package Project;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField txtid;
	private JPasswordField passwordField;
	DataOutputStream outputStream;  
	DataInputStream inputStream;

	Connection con = null;
	/**
	 * Launch the application.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public class LoginVO {
	    private String txtID;
	    public LoginVO(String txtID) {
			super();
			this.txtID = txtID;
		}
		public LoginVO() {}
		
		public String getTxtID() {
			return txtID;
		}
		public void setTxtID(String txtID) {
			this.txtID = txtID;
		}
	    
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public login() throws ClassNotFoundException, SQLException {
		
		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","hr","hr");
		
		setTitle("·Î±×ÀÎ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 474);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblid = new JLabel("ID : ");
		lblid.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 25));
		lblid.setBounds(97, 171, 69, 43);
		contentPane.add(lblid);
		
		JLabel lblpw = new JLabel("PW : ");
		lblpw.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 25));
		lblpw.setBounds(97, 247, 69, 43);
		contentPane.add(lblpw);
		
		txtid = new JTextField();
		txtid.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 18));
		txtid.setBounds(179, 171, 279, 43);
		contentPane.add(txtid);
		txtid.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 18));
		passwordField.setBounds(179, 247, 279, 43);
		contentPane.add(passwordField);
		
		JButton btnlogin = new JButton("\uB85C\uADF8\uC778");
		btnlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sql = "select * from chatting where id = ?";
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, txtid.getText());
					ResultSet rs = ps.executeQuery();
					
					String id = null;
					String pwd = null;
					int admin = 0;
					while(rs.next()) {
						id = rs.getString("id");
						pwd = rs.getString("pwd");
						admin = rs.getInt("admin");
					}
					if(pwd.equals(passwordField.getText()) && id.equals(txtid.getText())) {
						if(admin == 1) {
							JOptionPane.showMessageDialog(null, "°ü¸®ÀÚ ·Î±×ÀÎ ¼º°ø");
							
							new ServerGui().setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "·Î±×ÀÎ ¼º°ø");
							
							String txtID=txtid.getText(); ///////
							LoginVO loginvo1 = new LoginVO(txtID);
							
							new ClientGui(loginvo1).setVisible(true);
							dispose();
							
						}
					} else {
						JOptionPane.showMessageDialog(null, "¾ÆÀÌµð È¤Àº ºñ¹Ð¹øÈ£¸¦ È®ÀÎÇØÁÖ¼¼¿ä.");
					}
					
						
				} catch (SQLException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}
		});
		btnlogin.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btnlogin.setBounds(501, 171, 129, 68);
		contentPane.add(btnlogin);
		
		JButton btnjoin = new JButton("\uD68C\uC6D0\uAC00\uC785");
		btnjoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				setVisible(true);
				try {
					new join().setVisible(true);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
	        
			}
		});
		btnjoin.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btnjoin.setBounds(501, 258, 129, 32);
		contentPane.add(btnjoin);
		
		JLabel lblKg = new JLabel("KG\uD1A1");
		lblKg.setForeground(Color.BLUE);
		lblKg.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 30));
		lblKg.setBounds(318, 81, 103, 43);
		contentPane.add(lblKg);
	}
}
