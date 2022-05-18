package savers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataModel.Node;

public class JsonSaver implements Savable {

	@Override
	public boolean saveToFile(Node unsavedRecords, String fileName) {
		boolean answer = false;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Node.class, new NodeAdapter());
		Gson gson = gsonBuilder.create();
		String outputString = gson.toJson(unsavedRecords, Node.class);
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
		Node answer = new Node();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Node.class, new NodeAdapter());
		Gson gson = gsonBuilder.create();
		if (out != null && !out.toString().equals("")) {
			answer = gson.fromJson(out.toString(), Node.class);
		}
		return answer;
	}

}
