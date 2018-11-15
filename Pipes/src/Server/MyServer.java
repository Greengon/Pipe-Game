/*
 * This class will handle one client every time
 * until it will get "stop" order.
 * The user handling will be via the client handler
 * that was define for the client. 
 * */

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import ClientHandler.ClientHandler;

public class MyServer implements Server {

	// Data members
	private ServerSocket serverSocket;
	private int port;
	private boolean stop = false;
	
	// CTOR
	public MyServer(int port){
		this.port =port;
	}

	
	private void startServer(ClientHandler ch) throws Exception {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(1000);
		//System.out.println("Server connected - waiting");
		
		while(!stop) {
			try {
				Socket aClient = serverSocket.accept();
				//System.out.println("client connected");
				
				ch.handle(aClient.getInputStream(),aClient.getOutputStream());
				// the ch is responsible for closing the streams

				aClient.close();
			} catch(SocketTimeoutException e) {
			//	System.out.println("Client did not connect...");
			} finally {
				//System.out.println("safe exit");
			}
		}
		serverSocket.close();
		//System.out.println("Server sockect closed");
	}

	@Override
	public void start(ClientHandler ch) {
		new Thread(() -> {
			try {
				startServer(ch);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}).start();
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		stop = true;
		System.out.println("done");
	}

}
