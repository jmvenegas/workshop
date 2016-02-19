package application.model;

public abstract class AbstractServer {
	
	/*
	 * Class Variables
	 */
	
	
	/*
	 * Abstract Methods
	 */
	public abstract void notifyClient(AbstractClient client);
	
	public abstract void notifyAllClients();

}
