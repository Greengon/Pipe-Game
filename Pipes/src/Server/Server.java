/*
 * Responsible to listen on clients
 * and generate handling element for them. 
 * */

package Server;

import ClientHandler.ClientHandler;

public interface Server {
	void start(ClientHandler ch);
	void stop();
}
