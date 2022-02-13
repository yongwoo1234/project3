package Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ClientBack {

   private Socket socket;
   ClientGui gui;

   public final void setGui(ClientGui gui) {
      this.gui = gui;

   }

   // ��ư�� �������� �����ϴ� �޼���
   public void stop() {
      try {
    	 //JOptionPane.showMessageDialog(null, "ä���� �ٽ��Ͻ÷��� ������ư�� �����ּ���.");
         socket.close();
      } catch (IOException e) {
         //e.printStackTrace();
      }

   }

   // ������ �����ϴ� �޼���
   public void connect() {
      PrintWriter writer = null;
      try {
    	  //JOptionPane.showMessageDialog(null, "ä�ÿ� �����մϴ�.");
         // ������ ������ IP �Է�
         socket = new Socket(gui.iip.getText(), Integer.parseInt(gui.pport.getText()));
         // �ڽ��� ���� �г��� �Է�
         String name = gui.nname.getText();

         writer = new PrintWriter(socket.getOutputStream());
         writer.println(name);
         writer.flush();

         // ������ ������ �б� �޼��带 �θ�
         read();

      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();

      }

   }

   // �о��ִ� �޼���
   public void read() {
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
            BufferedReader br = null;
            // ������ ������ ��û�� ��� �о���
            while (true) {
               try {
                  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  String data = br.readLine();
                  if (data != null) {

                     System.out.println("\n" + data);
                     gui.append(data);
                     // ��ũ�� ���� ��������
                     gui.jta.setCaretPosition(gui.jta.getDocument().getLength()); 
                  }
               } catch (IOException e) {
                  try {
                     br.close();
                     socket.close();
                     break;
                  } catch (IOException e1) {
                     e1.printStackTrace();
                  }
               }
            }

         }

      });
      thread.start();

   }

   // �޼����� ������ ������ �޼���
   public void chatStart(String msg) {
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {

            PrintWriter writer = null;

            try {
               writer = new PrintWriter(socket.getOutputStream());
               writer.println(msg);
               writer.flush();
            } catch (IOException e) {
               try {
                  writer.close();
                  socket.close();
               } catch (IOException e1) {
                  e1.printStackTrace();
               }
            }

         }

      });
      thread.start();

   }
}