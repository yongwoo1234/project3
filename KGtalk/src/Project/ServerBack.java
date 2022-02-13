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
   // client�� ������ ������ List
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
         namelist = new Vector<String>();// �г��� ���� ���͸���Ʈ
         System.out.println("[������ �����մϴ�]");
         gui.append("[������ �����մϴ�]");
         
         // ��ũ�� ���� ��������
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

                  // client������ �������� ����
                  vectorList.add(client);

                  // �г��Ӹ� ���� ��Ʈ��
                  br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                  String name = br.readLine();
                  namelist.add(name);

                  System.out.println("================================");
                  System.out.println("�����˸��� : �����ο�[" + vectorList.size() + "��]");// ���Ͽ� ����ִ� ���ڰ� �����ο�
                  System.out.println("�����˸��� : ������" + namelist);
                  System.out.println("================================");

                  gui.append("================================");
                  gui.append("�����˸��� : �����ο�[" + vectorList.size() + "��]"); // �����ο��� ���� gui�� �ø���
                  
                  // ��ũ�� ���� ��������
                  gui.jta.setCaretPosition(gui.jta.getDocument().getLength()); 
                  entrance(client);
                  /* rule(client); */
                  read(client);

               }

               catch (SocketException e) {
                  flg = false; // ������ �޾����� while���� ����
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

         }

      });
      thread.start();

   }

   // Ŭ���̾�Ʈ�� ���� ������ �޽����� �о��ִ� �޼���
   public void read(Socket socket) {

      new Thread(new Runnable() {

         @Override
         public void run() {

            BufferedReader br = null;
            try { // Ŭ���̾�Ʈ�� ����Ǿ��ִ� ����
               br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               while (true) {
                  String msg = br.readLine();

                  if (msg != null) {
                     if (msg.contains("/w")) { // �ӼӸ� ���� �ľ�
                        String name = msg.substring(3, msg.indexOf(":")); // �ӼӸ� ���� ��� �̸�
                        String whisperMsg = msg.substring(msg.indexOf(":") + 1); // �ӼӸ� �޼���
                        whisper(whisperMsg, name, socket); // �ӼӸ� �޼���� ����
                     } else {

                        int idx = vectorList.indexOf(socket);// Ŭ���̾�Ʈ ������ ���° �ε������� �ľ�
                        allClientWrite(msg, namelist.get(idx)); // ������ Ŭ���̾�Ʈ �г��� �ľ� ��� Ŭ���̾�Ʈ���� �޼��� ����

                        System.out.println(namelist.get(idx) + ":" + msg); // ������ �ֿܼ� �ø���
                        gui.append(namelist.get(idx) + ":" + msg); // ���� gui�� �ø���

                        // ��ũ�� ���� ��������
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

   // �ӼӸ� �޼���
   public void whisper(String msg, String name, Socket socket) {
      PrintWriter writer = null;
      int idx = vectorList.indexOf(socket); // ���� ������ ���� �ľ�

      try {
         writer = new PrintWriter(socket.getOutputStream());
         writer.println("���� ���� <�ӼӸ�> : " + msg);
         writer.flush();

      } catch (IOException e) {
         e.printStackTrace();
      }

      for (int i = 0; i < namelist.size(); i++) {
         try {
            if (namelist.get(i).equals(name)) { // ������ ����Ʈ�� �ӼӸ� ��밡 �ִٸ�
               writer = new PrintWriter(vectorList.get(i).getOutputStream()); // �� ��뿡�Ը� �޼����� ����
               writer.println(namelist.get(idx) + "���� �� <�ӼӸ�> : " + msg);
               writer.flush();

            }
         } catch (IOException e) {
            removeSocket(socket);
         }

      }
   }

   // ��� Ŭ���̾�Ʈ���� �޼����� �����ϴ� �޼���
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

   // �˸� �޼���
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

   // ���� �˸� �޼���
   public void entrance(Socket socket) {
      int idx = vectorList.indexOf(socket);
      gui.append("�����˸��� : ����[" + namelist.get(idx) + "]"); // ������ Ŭ���̾�Ʈ�� �г��� ���� gui�� �ø���
      gui.append("================================");
      allClientWrite(" [����] " + namelist.get(idx), "�����˸��� "); // ������ Ŭ���̾�Ʈ�� �г��� �ٸ� Ŭ���̾�Ʈ gui�� �ø���
   }

   // vectorList�� ���� Socket �ν��Ͻ��� ����
   public void removeSocket(Socket socket) {

      int idx = vectorList.indexOf(socket);// ���� ������ ���° �ε������� �˰� namelist�� ������ �̸��� �˾Ƴ���

      System.out.println("================================"); // Ŭ���̾�Ʈ�� ���� ���� �ֿܼ� �ø���
      System.out.println("�����˸��� : �����ο�[" + (vectorList.size() - 1) + "��]");
      System.out.println("�����˸��� : ����[" + namelist.get(idx) + "]");
      System.out.println("================================");

      gui.append("================================"); // Ŭ���̾�Ʈ�� ���� ���� gui�� �ø���
      gui.append("�����˸��� : �����ο�[" + (vectorList.size() - 1) + "��]");
      gui.append("�����˸��� : ����[" + namelist.get(idx) + "]");
      gui.append("================================");

      allClientWrite(" [����] " + namelist.get(idx), "�����˸��� "); // ���� ������ �ִ� Ŭ���̾�Ʈ���� ���� �˸���
      
      // ��ũ�� ���� ��������
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

   // ������ ���� ��Ű�� �޼���
   public void stop() {
      for (int i = 0; i < vectorList.size(); i++) {
         try {
            allClientWrite(" ������ ����Ǿ����ϴ�.", "�����˸��� ");
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