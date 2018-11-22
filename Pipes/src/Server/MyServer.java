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
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
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
	private priorityJobScheduler scheduler;

	
	// CTOR
	public MyServer(int port,int M){
		this.port =port;
		this.M = M;
	}

	private void startServer() throws Exception {
		Socket socket = null;
		ServerSocket serverSocket = null;
		this.scheduler = new priorityJobScheduler();
		try {
			serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
				}
		while(!stop) {
			try {
				 socket = serverSocket.accept();
				} catch (IOException e) {
					System.out.println("I/O error: " + e);
					}
			// new thread for a client
			new reciveThread(socket).start();
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
			scheduler.executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			//System.out.println("done");
	     	serverSocket.close();
		}catch (Exception e){
			//System.out.println("Problem to wait to all Threads to finish");
		}
	}
	
	
	//class to open thread for each client
	public class solveThread extends Thread {
	    protected Socket socket;
	    public MyCHandler<String> ch;
	    public solveThread(MyCHandler<String> ch ) {
	        this.socket = ch.getClient();
	        this.ch=ch;
	        
	    }
	    public void run() {
		try {
			//System.out.println("He arrived to run");
			ch.send(socket.getOutputStream());
			//the ch is responsible for closing the streams
			socket.close();
			//System.out.println("Server sockect closed");
			}catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				//System.out.println("safe exit");
			}
	    }
	}
	
	public class reciveThread extends Thread {
	    protected Socket socket;

	    public reciveThread(Socket clientSocket) {
	        this.socket = clientSocket;
	    }

	    public void run() {
	    	try {
				//System.out.println("wait for cliant");
				MyCHandler<String> ch = new MyCHandler<String>(this.socket);
				ch.recive(this.socket.getInputStream());
				scheduler.scheduleSolve(new solveThread(ch));
			}catch(SocketException se){
				//System.out.println("No one connected to the socket");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public class priorityJobScheduler{
		private ExecutorService priorityJobScheduler;
		private PriorityBlockingQueue<solveThread> queue;
		private ExecutorService executor;
		
		public priorityJobScheduler() {
		//this.queue = new PriorityBlockingQueue<MyCHandler<String>>(10,new BoardComparator<String>());
		this.queue = new PriorityBlockingQueue<solveThread>(10,new BoardComparator<String>());
		this.executor = Executors.newFixedThreadPool(M); 
		this.priorityJobScheduler = Executors.newSingleThreadExecutor();
		this.priorityJobScheduler.execute(()->{
					while(true)
						try {
							executor.execute(queue.take());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
		}
		
		public void scheduleSolve(solveThread st) {
			this.queue.add(st);
		}
	}
	}