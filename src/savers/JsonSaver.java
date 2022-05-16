package savers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dataModel.Node;
import dataModel.SessionManager;

public class JsonSaver implements Savable {

	@Override
	public boolean saveToFile(Node unsavedRecords, String fileName) {
		boolean answer = false;

		System.out.println("saving: " + unsavedRecords);
		Gson toGson = new Gson();
		System.out.println("toGson generated");
		String outputString = toGson.toJson(unsavedRecords.toPrint());

		File myFile = new File(fileName);
		try {
			@SuppressWarnings("resource")
			OutputStream fileStream = new FileOutputStream(myFile, false);
			@SuppressWarnings("resource")
			Writer writer = new BufferedWriter(new OutputStreamWriter(fileStream));
			writer.write(outputString);
			writer.close();
			answer = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}

	@Override
	public Node readFromFile(String fileName) {
		// Get string from file
		StringBuilder out = new StringBuilder();
		try {
			File file = new File(fileName);
			FileReader reader = new FileReader(file);
			Scanner scanner = new Scanner(reader);
			while (scanner.hasNextLine()) {
				out.append(scanner.nextLine());
			}
			reader.close();
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Start convert JSON
		Node answer;
		SessionManager.clearSession();
		answer = SessionManager.getSession();
		if (out != null && !out.toString().equals("")) {
			Gson gson = new Gson();
			Type type = new TypeToken<Node>() {
			}.getType();
			answer = gson.fromJson(out.toString(), type);
		}
		return answer;
	}

}
