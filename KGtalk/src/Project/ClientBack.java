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

   // 버튼을 눌렀을때 종료하는 메서드
   public void stop() {
      try {
    	 //JOptionPane.showMessageDialog(null, "채팅을 다시하시려면 참여버튼을 눌러주세요.");
         socket.close();
      } catch (IOException e) {
         //e.printStackTrace();
      }

   }

   // 서버에 연결하는 메서드
   public void connect() {
      PrintWriter writer = null;
      try {
    	  //JOptionPane.showMessageDialog(null, "채팅에 참여합니다.");
         // 연결할 서버의 IP 입력
         socket = new Socket(gui.iip.getText(), Integer.parseInt(gui.pport.getText()));
         // 자신이 정한 닉네임 입력
         String name = gui.nname.getText();

         writer = new PrintWriter(socket.getOutputStream());
         writer.println(name);
         writer.flush();

         // 서버에 보내고 읽기 메서드를 부름
         read();

      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();

      }

   }

   // 읽어주는 메서드
   public void read() {
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
            BufferedReader br = null;
            // 서버로 들어오는 요청을 계속 읽어줌
            while (true) {
               try {
                  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  String data = br.readLine();
                  if (data != null) {

                     System.out.println("\n" + data);
                     gui.append(data);
                     // 스크롤 따라 내려가기
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

   // 메세지를 서버로 보내는 메서드
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