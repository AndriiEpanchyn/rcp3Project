package savers;

import dataModel.Node;

public interface Savable {
	boolean saveToFile(Node unsavedRecords, String fileName);

	Node readFromFile(String fileName);
}
