package Project;

import java.awt.Frame;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import Project.login.LoginVO;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractListModel;

public class ClientGui extends JFrame {

   JTextArea jta = new JTextArea();
   JTextField jtf;
   JTextField nname;
   JTextField iip;
   JTextField pport;
   JButton start;
   JScrollPane scrollPane;


   ClientBack back = new ClientBack();

   public static void main(String[] args) throws ClassNotFoundException, SQLException {
      
   }

   public ClientGui(LoginVO loginvo1) throws ClassNotFoundException, SQLException {
   	getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
	   
//	   Class.forName("oracle.jdbc.driver.OracleDriver");
//		con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","hr","hr");
   	
   	
   	  setTitle("Ã¤ÆÃÇÏ±â");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 535, 527);
      getContentPane().setLayout(null);
      

      scrollPane = new JScrollPane();
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollPane.setBounds(17, 14, 394, 399);
      getContentPane().add(scrollPane);
      scrollPane.setViewportView(jta);
      
      JButton btnSend = new JButton("");
      btnSend.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseClicked(MouseEvent e) {
      		
      		String msg = jtf.getText();
            back.chatStart(msg);
            jtf.setText("");
            
      	}
      });
      btnSend.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      	}
      });
      
      jtf = new JTextField();
      jtf.setBounds(17, 428, 363, 28);
      getContentPane().add(jtf);
      jtf.setColumns(10);
      
      jtf.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            String msg = jtf.getText();
            back.chatStart(msg);
            jtf.setText("");

         }
      });
      btnSend.setBackground(SystemColor.inactiveCaptionBorder);
      btnSend.setIcon(new ImageIcon(ClientGui.class.getResource("/Project/iconmonstr-paper-plane-1-24 (1).png")));
      btnSend.setBounds(379, 428, 32, 28);
      btnSend.setBorderPainted(false);
      getContentPane().add(btnSend);      

      //db 
      
//      String sql = "select name from chatting where id = ?";
//      String id = null;
//      PreparedStatement ps = con.prepareStatement(sql);
//      ResultSet rs = ps.executeQuery();
      
//      id = rs.getString("id");
      nname = new JTextField();
      nname.setText(loginvo1.getTxtID());
      nname.setHorizontalAlignment(SwingConstants.CENTER);
      nname.setBounds(17, 14, 86, 28);
      nname.setVisible(false);
      getContentPane().add(nname);
      nname.setColumns(10);

      iip = new JTextField();
      iip.setHorizontalAlignment(SwingConstants.CENTER);
      iip.setColumns(10);
      iip.setBounds(109, 14, 134, 28);
      iip.setVisible(false);
      getContentPane().add(iip);
      iip.setText("127.0.0.1");

      pport = new JTextField();
      pport.setHorizontalAlignment(SwingConstants.CENTER);
      pport.setColumns(10);
      pport.setBounds(248, 14, 86, 28);
      pport.setVisible(false);
      getContentPane().add(pport);
      pport.setText("8080");
      start = new JButton("Âü¿©");
      start.setBackground(SystemColor.window);
      start.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));

      start.setBounds(425, 14, 78, 42);
      getContentPane().add(start);
      
      setVisible(true);
      
      
      back.setGui(this);
      
      start.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (start.getText().equals("Âü¿©")) {
               start.setText("ÅðÀå");
               back.connect();

            } else {
               start.setText("ÅðÀå");
               jta.append("[Ã¤ÆÃ ÅðÀå]");
               jta.append("\n");
               start.setText("Âü¿©");
               back.stop();
               dispose();
            }

         }
         
         
      });

      addWindowListener(new WindowListener() {
		
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			start.doClick();
			
		}
		
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	});

   }


public void append(String msg) {
      jta.append(msg+"\n");
   }
}