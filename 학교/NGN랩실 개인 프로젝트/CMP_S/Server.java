package CMP_S;

import java.io.*;
import java.net.*;

public class Server{
	public static void main(String[] args) {
		try {
			ServerSocket serversocket = new ServerSocket(5001);
			Socket socket = new Socket();
			int count = 0;
			while(true) {
				socket = serversocket.accept();
				count++;
				ServerThread server = new ServerThread();
				server.setSocket(socket);
				server.setCount(count);
				server.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}