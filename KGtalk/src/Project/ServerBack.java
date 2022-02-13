package Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class ServerBack {
   // client의 소켓을 저장할 List
   private Vector<Socket> vectorList;
   private Vector<String> namelist;
   private ServerSocket server;
   
   ServerGui gui;
   
   public final void setGui(ServerGui gui) {
      this.gui = gui;

   }
   
   
   public void connet() {
      try {

         server = new ServerSocket(Integer.parseInt(gui.pport.getText()));
         vectorList = new Vector<>();
         namelist = new Vector<String>();// 닉네임 받을 백터리스트
         System.out.println("[서버를 시작합니다]");
         gui.append("[서버를 시작합니다]");
         
         // 스크롤 따라 내려가기
         gui.jta.setCaretPosition(gui.jta.getDocument().getLength()); 

         startServer();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
   public void startServer() {
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {

            BufferedReader br = null;

            boolean flg = true;

            while (flg) {
               try {

                  Socket client = server.accept();

                  // client소켓을 차곡차곡 저장
                  vectorList.add(client);

                  // 닉네임만 받을 스트림
                  br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                  String name = br.readLine();
                  namelist.add(name);

                  System.out.println("================================");
                  System.out.println("서버알림이 : 접속인원[" + vectorList.size() + "명]");// 소켓에 들어있는 숫자가 입장인원
                  System.out.println("서버알림이 : 입장인" + namelist);
                  System.out.println("================================");

                  gui.append("================================");
                  gui.append("서버알림이 : 접속인원[" + vectorList.size() + "명]"); // 접속인원수 서버 gui에 올리기
                  
                  // 스크롤 따라 내려가기
                  gui.jta.setCaretPosition(gui.jta.getDocument().getLength()); 
                  entrance(client);
                  /* rule(client); */
                  read(client);

               }

               catch (SocketException e) {
                  flg = false; // 서버를 받았을때 while문을 멈춤
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

         }

      });
      thread.start();

   }

   // 클라이언트로 부터 들어오는 메시지를 읽어주는 메서드
   public void read(Socket socket) {

      new Thread(new Runnable() {

         @Override
         public void run() {

            BufferedReader br = null;
            try { // 클라이언트와 연결되어있는 소켓
               br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               while (true) {
                  String msg = br.readLine();

                  if (msg != null) {
                     if (msg.contains("/w")) { // 귓속말 여부 파악
                        String name = msg.substring(3, msg.indexOf(":")); // 귓속말 보낼 상대 이름
                        String whisperMsg = msg.substring(msg.indexOf(":") + 1); // 귓속말 메세지
                        whisper(whisperMsg, name, socket); // 귓속말 메서드로 보냄
                     } else {

                        int idx = vectorList.indexOf(socket);// 클라이언트 소켓이 몇번째 인덱스인지 파악
                        allClientWrite(msg, namelist.get(idx)); // 보내는 클라이언트 닉네임 파악 모든 클라이언트에게 메세지 전송

                        System.out.println(namelist.get(idx) + ":" + msg); // 서버에 콘솔에 올리기
                        gui.append(namelist.get(idx) + ":" + msg); // 서버 gui에 올리기

                        // 스크롤 따라 내려가기
                        gui.jta.setCaretPosition(gui.jta.getDocument().getLength()); 

                     }
                  } else {
                     removeSocket(socket);
                     break;
                  }

               }
            } catch (SocketException e) {
            } catch (IOException e) {
               removeSocket(socket);
            }

         }
      }).start();

   }

   // 귓속말 메서드
   public void whisper(String msg, String name, Socket socket) {
      PrintWriter writer = null;
      int idx = vectorList.indexOf(socket); // 들어온 소켓의 정보 파악

      try {
         writer = new PrintWriter(socket.getOutputStream());
         writer.println("내가 보낸 <귓속말> : " + msg);
         writer.flush();

      } catch (IOException e) {
         e.printStackTrace();
      }

      for (int i = 0; i < namelist.size(); i++) {
         try {
            if (namelist.get(i).equals(name)) { // 입장자 리스트에 귓속말 상대가 있다면
               writer = new PrintWriter(vectorList.get(i).getOutputStream()); // 그 상대에게만 메세지를 전송
               writer.println(namelist.get(idx) + "에게 온 <귓속말> : " + msg);
               writer.flush();

            }
         } catch (IOException e) {
            removeSocket(socket);
         }

      }
   }

   // 모든 클라이언트에게 메세지를 전송하는 메서드
   public void allClientWrite(String msg, String name) {

      Socket socket = null;
      PrintWriter writer = null;

      for (int i = 0; i < vectorList.size(); i++) {
         try {
            socket = vectorList.get(i);
            writer = new PrintWriter(socket.getOutputStream());
            writer.println(name + ":" + msg);
            writer.flush();

         } catch (IOException e) {
            writer.close();
            removeSocket(socket);
         }

      }

   }

   // 알림 메서드
   public void rule(Socket socket) {
      PrintWriter writer = null;
      int idx = vectorList.indexOf(socket);
      socket = vectorList.get(idx);

      try {
         writer = new PrintWriter(socket.getOutputStream());
         writer.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // 입장 알림 메서드
   public void entrance(Socket socket) {
      int idx = vectorList.indexOf(socket);
      gui.append("서버알림이 : 입장[" + namelist.get(idx) + "]"); // 입장한 클라이언트의 닉네임 서버 gui에 올리기
      gui.append("================================");
      allClientWrite(" [입장] " + namelist.get(idx), "서버알림이 "); // 입장한 클라이언트의 닉네임 다른 클라이언트 gui에 올리기
   }

   // vectorList에 닫힌 Socket 인스턴스를 제거
   public void removeSocket(Socket socket) {

      int idx = vectorList.indexOf(socket);// 나간 소켓이 몇번째 인덱스인지 알고 namelist에 대입해 이름을 알아낸다

      System.out.println("================================"); // 클라이언트의 퇴장 서버 콘솔에 올리기
      System.out.println("서버알림이 : 접속인원[" + (vectorList.size() - 1) + "명]");
      System.out.println("서버알림이 : 퇴장[" + namelist.get(idx) + "]");
      System.out.println("================================");

      gui.append("================================"); // 클라이언트의 퇴장 서버 gui에 올리기
      gui.append("서버알림이 : 접속인원[" + (vectorList.size() - 1) + "명]");
      gui.append("서버알림이 : 퇴장[" + namelist.get(idx) + "]");
      gui.append("================================");

      allClientWrite(" [퇴장] " + namelist.get(idx), "서버알림이 "); // 같은 서버에 있는 클라이언트에게 퇴장 알리기
      
      // 스크롤 따라 내려가기
      gui.jta.setCaretPosition(gui.jta.getDocument().getLength()); 
      for (int i = 0; i < vectorList.size(); i++) {
         if (vectorList.get(i) == socket) {
            try {
               vectorList.get(i).close();
               vectorList.remove(i);
               namelist.remove(i);
            } catch (IOException e) {
               e.printStackTrace();
            }

         }
      }
   }

   // 서버를 정지 시키는 메서드
   public void stop() {
      for (int i = 0; i < vectorList.size(); i++) {
         try {
            allClientWrite(" 서버가 종료되었습니다.", "서버알림이 ");
            vectorList.remove(i);
            namelist.remove(i);
            vectorList.get(i).close();
            server.close();
         } catch (IOException e) {
            e.printStackTrace();
         }

      }
   }
}