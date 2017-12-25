package interfaces;

public interface Observer {
	
	//method to update the observer, used by subject
	public void update(String updateReason);
	
}
