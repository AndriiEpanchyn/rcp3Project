package dataModel;

public class SessionManager {

	private static Node session;
	private static Node currentRefrence;
	private static String fileName;
	public static boolean isNewFile;

	public static synchronized Node getSession() {
		if (session == null) {
			Node node = new Node();
			Node root = node.createSubFolder("Folder");
			session = node;
			currentRefrence = session;
		}
		return session;
	}

	public static void setSession(Node newSession) {
		session = newSession;
	}

	private SessionManager() {
		Node node = new Node();
		Node root = node.createSubFolder("Folder");
		session = node;
		currentRefrence = session;
	}

	public static synchronized Node getCurrentRefrence() {
		if (session == null) {
			session = SessionManager.getSession();
		}
		if (currentRefrence == null) {
			currentRefrence = session;
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
		fileName = "";
		isNewFile = true;
	}

}
