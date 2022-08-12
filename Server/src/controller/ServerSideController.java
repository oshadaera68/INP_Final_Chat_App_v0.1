/**
 * @Owner - Oshada Eranga
 * @version - v0.1
 */

package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSideController {
    static ServerSocket serverSocket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static Socket socket;
    public TextField txtServerOne;
    public TextArea txtServerArea1;

    String messageIn = "";
    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                System.out.println("Server Started Waiting for client! .....");
                socket = serverSocket.accept();
                System.out.println("Client Accepted! .......");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!messageIn.equals("end")) {
                    messageIn = dataInputStream.readUTF();
                    txtServerArea1.appendText("\nClient:" + messageIn.trim() + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        String text = txtServerOne.getText();
//        System.out.println(text);
        txtServerArea1.appendText("\t\t\t\t\t\t\t\tServer:" +text.trim());
        dataOutputStream.writeUTF(text);
        txtServerOne.setText("");
    }
}
