package dataAccess;

public class EccezioneConnessioneDatabase extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EccezioneConnessioneDatabase() {

	}
	
	public EccezioneConnessioneDatabase(String mess) {
		super(mess);
	}
}
