package dataModel;

public class SessionManager {
	
	private static Node session;
	private static Node currentRefrence;
    private static String fileName;
    
	public static synchronized Node getSession() {
		if (session == null) {
			session = new Node();
			currentRefrence = session;
		}
		return session;
	}

	private SessionManager() {
		session = new Node();
		currentRefrence = session;
	}

	public static synchronized Node getCurrentRefrence() {
		if (session == null) {
			session = SessionManager.getSession();
		}
		return currentRefrence;
	}

	public static synchronized void setCurrentRefrence(Node newCurrent) {
			currentRefrence = newCurrent;
	}
	
	public static synchronized String getFileName() {
		return fileName;
	}
	
	public static synchronized void setFileName(String file) {
		fileName = file;
	}

	public static synchronized void clearSession() {
		session = null;
		session = SessionManager.getSession();
		fileName="";
	}

}
