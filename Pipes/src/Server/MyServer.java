/*
 * This class will handle one or more client every time
 * until it will get "stop" order.
 * The user handling will be via the client handler
 * that was define for the client. 
 * We will be using threads to create shortest job
 * first queue.
 * We will handle no more then M clients at once by using ThreadPool.
 * In the stop order we will stop letting new clients in and
 * finish dealing with the remaining clients.
 * */

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ClientHandler.BoardComparator;
import ClientHandler.ClientHandler;
import ClientHandler.MyCHandler;

public class MyServer implements Server {

	// Data members
	private ServerSocket serverSocket;
	private int port;
	private boolean stop = false;
	private int M;
	private PriorityQueue<MyCHandler<String>> queue;
	private ExecutorService executor;
	
	// CTOR
	public MyServer(int port,int M){
		this.port =port;
		this.queue = new PriorityQueue<MyCHandler<String>>(new BoardComparator<String>());
		this.executor = Executors.newFixedThreadPool(M); 
	}

/*
 * private void startServer(ClientHandler ch) throws Exception {
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

*/

	private void startServer() throws Exception {
		serverSocket = new ServerSocket(port);
		//serverSocket.setSoTimeout(1000);
		while(!stop) {
				Socket aClient = serverSocket.accept();
				MyCHandler<String> ch = new MyCHandler<String>();
				queue.add(ch);
				executor.execute(			
						new Runnable()  {
						    public void run() {
							try {
							MyCHandler<String> ch = queue.poll();
							ch.handle(aClient.getInputStream(),aClient.getOutputStream());
							//the ch is responsible for closing the streams
							aClient.close();
							serverSocket.close();
							System.out.println("Server sockect closed");
							}catch (IOException e) {
								e.printStackTrace();
							}catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}finally {
								System.out.println("safe exit");
							}}});
				}
		}

	@Override
	public void start() {
		try {
			startServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	@Override
	public void stop() {
		try {
			stop = true;
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			System.out.println("done");
		}catch (InterruptedException e){
			System.out.println("Problem to wait to all Threads to finish");
		}
	} 
	
}