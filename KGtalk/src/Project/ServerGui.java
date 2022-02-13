package Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class ServerGui extends JFrame{
    JPanel contentPane;
    JTextField iip;
    JTextField pport;
    JTextArea jta;
    JScrollPane scrollPane;
   
   ServerBack back = new ServerBack();
   
   public static void main(String[] args) {
      ServerGui frame = new ServerGui();
      frame.setVisible(true);
   }

   public ServerGui() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 436, 606);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      iip = new JTextField();
      iip.setHorizontalAlignment(SwingConstants.CENTER);
      iip.setText(" 127.0.0.1");
      iip.setBounds(17, 16, 135, 28);
      contentPane.add(iip);
      iip.setColumns(10);

      pport = new JTextField();
      pport.setHorizontalAlignment(SwingConstants.CENTER);
      pport.setText("8080");
      pport.setColumns(10);
      pport.setBounds(157, 16, 115, 28);
      contentPane.add(pport);

      JButton start = new JButton("Open");
      start.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (start.getText().equals("Open")) {
               back.connet();
               start.setText("Close");
            } else {
               start.setText("Open");
               jta.append("[서버를 종료합니다]");
               back.stop();
               dispose();
               System.exit(1);
            }

         }
      });
      start.setBounds(278, 15, 108, 31);
      contentPane.add(start);

      scrollPane = new JScrollPane();
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollPane.setBounds(17, 61, 369, 461);
      contentPane.add(scrollPane);

      jta = new JTextArea();
      scrollPane.setViewportView(jta);
      
      back.setGui(this);

   }
   
   public void append(String msg) {
      jta.append(msg+"\n");
   }

}