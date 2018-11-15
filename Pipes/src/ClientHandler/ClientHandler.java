/*
 * Responsible to communicate with a single client
 * on an agreed protocol 
 * */


package ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	void handle(InputStream in, OutputStream out) throws Exception;
}



