package Project;

import java.awt.BorderLayout;


import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.JPasswordField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;



public class join extends JFrame {
	
	

	private JPanel contentPane;
	private JTextField txtjid;
	private JTextField txtjname;
	private JTextField txtjnum;
	private JTextField txtjmail;
	private JPasswordField txtjpw;
	private JTextField textField;
	
    Connection con = null;
    
	
	/**
	 * Launch the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					join frame = new join();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public join() throws ClassNotFoundException, SQLException {
		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","hr","hr");
		
		
		// ³¯Â¥¹è¿­ ¼±¾ð
		ArrayList<String> yeararray; // ³â
		ArrayList<String> montharray; // ¿ù
		ArrayList<String> dayarray; // ÀÏ

		Calendar oCalendar = Calendar.getInstance( );  // ÇöÀç ³¯Â¥/½Ã°£ µîÀÇ °¢Á¾ Á¤º¸ ¾ò±â

		// ÇöÀç ³¯Â¥
		 int toyear = oCalendar.get(Calendar.YEAR);
		 int tomonth = oCalendar.get(Calendar.MONTH) + 1;
		 int today = oCalendar.get(Calendar.DAY_OF_MONTH);
		
		 ButtonGroup groupRd = new ButtonGroup();
		
		 setTitle("È¸¿ø°¡ÀÔ");
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 598, 497);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("\uD68C\uC6D0\uAC00\uC785");
		lblName.setForeground(Color.BLUE);
		lblName.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(172, 12, 226, 45);
		contentPane.add(lblName);
		
		JLabel lbljid = new JLabel("ID");
		lbljid.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		lbljid.setBounds(14, 78, 88, 33);
		contentPane.add(lbljid);
		
		JLabel lbljpwd = new JLabel("Password");
		lbljpwd.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		lbljpwd.setBounds(14, 123, 88, 33);
		contentPane.add(lbljpwd);
		
		JLabel labelname = new JLabel("\uC774\uB984");
		labelname.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labelname.setBounds(14, 162, 88, 33);
		contentPane.add(labelname);
		
		JLabel labeljnum = new JLabel("\uC5F0\uB77D\uCC98");
		labeljnum.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labeljnum.setBounds(14, 207, 88, 33);
		contentPane.add(labeljnum);
		
		JLabel labeljbirth = new JLabel("\uC0DD\uB144\uC6D4\uC77C");
		labeljbirth.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labeljbirth.setBounds(14, 252, 88, 33);
		contentPane.add(labeljbirth);
		
		JLabel labeljemail = new JLabel("\uC774\uBA54\uC77C");
		labeljemail.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labeljemail.setBounds(14, 297, 88, 33);
		contentPane.add(labeljemail);
		
		JLabel labeljgen = new JLabel("\uC131\uBCC4");
		labeljgen.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labeljgen.setBounds(14, 349, 88, 33);
		contentPane.add(labeljgen);
		
		JRadioButton boxm = new JRadioButton("\uB0A8");
		boxm.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		boxm.setBackground(Color.WHITE);
		boxm.setBounds(148, 352, 139, 27);
		contentPane.add(boxm);
		groupRd.add(boxm);
		
		JRadioButton boxfm = new JRadioButton("\uC5EC");
		boxfm.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		boxfm.setBackground(Color.WHITE);
		boxfm.setBounds(293, 352, 139, 27);
		contentPane.add(boxfm);
		groupRd.add(boxfm);
		
		txtjid = new JTextField();
		txtjid.setBounds(94, 82, 355, 27);
		contentPane.add(txtjid);
		txtjid.setColumns(10);
		
		txtjname = new JTextField();
		txtjname.setColumns(10);
		txtjname.setBounds(95, 168, 355, 27);
		contentPane.add(txtjname);
		
		txtjnum = new JTextField();
		txtjnum.setColumns(10);
		txtjnum.setBounds(94, 211, 355, 27);
		contentPane.add(txtjnum);
		
		txtjmail = new JTextField();
		txtjmail.setColumns(10);
		txtjmail.setBounds(94, 302, 355, 27);
		contentPane.add(txtjmail);
		
		JButton confirmbtn = new JButton("ID\uC911\uBCF5\uD655\uC778");
		confirmbtn.setBackground(SystemColor.inactiveCaptionBorder);
		confirmbtn.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		confirmbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String sql1 = "select id from chatting where id = ?";
				try {
					PreparedStatement ps = con.prepareStatement(sql1);
					ps.setString(1, txtjid.getText());
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "Áßº¹µÈ ¾ÆÀÌµðÀÔ´Ï´Ù.");
					}else{
						JOptionPane.showMessageDialog(null, "»ç¿ë°¡´ÉÇÑ ¾ÆÀÌµðÀÔ´Ï´Ù.");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
			}
		});
		confirmbtn.setBounds(462, 81, 104, 27);
		contentPane.add(confirmbtn);
		
		JButton joinbtn = new JButton("\uC644\uB8CC");
		joinbtn.setBackground(SystemColor.inactiveCaptionBorder);
		joinbtn.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		joinbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sql = "insert into chatting values(?,?,?,?,?,?,?,?)";
				String id = txtjid.getText();
				String pw = txtjpw.getText();
				String name = txtjname.getText();
				String phone = txtjnum.getText();
				String birthdate = textField.getText();
				String email = txtjmail.getText();
				String gender = null;
				int admin = 0;
				
				if(boxm.isSelected()) {
					gender = "³²";
					
					JOptionPane.showMessageDialog(null, "È¸¿øµî·Ï ¿Ï·á");
	                dispose();
				}else if(boxfm.isSelected()) {
					gender = "¿©";
					
					JOptionPane.showMessageDialog(null, "È¸¿øµî·Ï ¿Ï·á");
	                dispose();
				}else{
					JOptionPane.showMessageDialog(null, "¼ºº°À» ¼±ÅÃÇÏ¼¼¿ä.");
				}
				
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, id);
					ps.setString(2, pw);
					ps.setString(3, name);
					ps.setString(4, phone);
					ps.setString(5, birthdate);
					ps.setString(6, email);
					ps.setString(7, gender);
					ps.setInt(8, admin);
					
					ps.executeUpdate();
					
					con.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				
			}
		});
		joinbtn.setBounds(121, 401, 105, 27);
		contentPane.add(joinbtn);
		
		JButton cancelbtn = new JButton("\uCDE8\uC18C");
		cancelbtn.setBackground(SystemColor.inactiveCaptionBorder);
		cancelbtn.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		cancelbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "È¸¿øµ¿·Ï ½ÇÆÐ");
                dispose();
			}
		});
		cancelbtn.setBounds(364, 401, 105, 27);
		contentPane.add(cancelbtn);
		yeararray = new ArrayList<String>();

		  for(int i = toyear + 10; i>= toyear -40; i--){
		   yeararray.add(String.valueOf(i));
		   //System.out.println(i);
		  }
		montharray = new ArrayList<String>();

		  for(int i = 1; i <= 12; i++){
		   montharray.add(String.valueOf(i));
		   //System.out.println(i);
		  }
		  String mcom = tomonth >= 10?String.valueOf(tomonth):"0"+tomonth;
		dayarray = new ArrayList<String>();

		  for(int i = 1; i <= 31; i++){
		   dayarray.add(String.valueOf(i));
		   //System.out.println(i);
		  }
		  String dcom = today >= 10?String.valueOf(today):"0"+today;
		
		txtjpw = new JPasswordField();
		txtjpw.setBounds(94, 127, 355, 29);
		contentPane.add(txtjpw);
		
		textField = new JTextField();
		textField.setBounds(94, 256, 355, 29);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
