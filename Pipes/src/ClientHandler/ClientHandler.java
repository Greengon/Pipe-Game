/*
 * Responsible to communicate with a single client
 * on an agreed protocol 
 * */


package ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	void recive(InputStream in) throws Exception;
	void send(OutputStream out)throws Exception;
}