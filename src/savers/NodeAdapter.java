package savers;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import dataModel.Node;

public class NodeAdapter extends TypeAdapter<Node> {

	public NodeAdapter() {

	}

	@Override
	public Node read(JsonReader jsonReader) throws IOException {
		JsonReader reader = jsonReader;
		Node answer = null;
		try {
			answer = readNodeTree(reader);
			return answer;
		} finally {
			// reader.close();
		}
	}

	public Node readNodeTree(JsonReader jsonReader) throws IOException {
		JsonReader reader = jsonReader;
		String parent = "";
		Node node = new Node();
		String name = "";
		int picStatus = -1;
		boolean isLeaf = false;
		String address = "";
		String city = "";
		int result = -1;
		String photoFileName = "";
		ArrayList<Node> children = new ArrayList<>();

		String fieldname = "";
		reader.beginObject();
		while (reader.hasNext()) {
			JsonToken token = reader.peek();
			if (token.equals(JsonToken.NAME)) {
				// get the current token
				fieldname = reader.nextName();
			} else if (fieldname.equals("parent")) {
				token = reader.peek();
				parent = reader.nextString();
			} else if ("name".equals(fieldname)) {
				token = reader.peek();
				name = reader.nextString();
			} else if ("picStatus".equals(fieldname)) {
				token = reader.peek();
				picStatus = reader.nextInt();
			} else if ("isLeaf".equals(fieldname)) {
				token = reader.peek();
				isLeaf = reader.nextBoolean();
			} else if ("address".equals(fieldname)) {
				token = reader.peek();
				address = reader.nextString();
			} else if ("city".equals(fieldname)) {
				token = reader.peek();
				city = reader.nextString();
			} else if ("result".equals(fieldname)) {
				token = reader.peek();
				result = reader.nextInt();
			} else if ("photoFileName".equals(fieldname)) {
				token = reader.peek();
				photoFileName = reader.nextString();
			} else {

				node = new Node(null, null, name, isLeaf, address, city, result, photoFileName);
				if ("children".equals(fieldname) && reader.peek() != JsonToken.NULL) {
					children = readChildren(reader);
					for (Node child : children) {
						child.setParent(node);
					}
					node.setChildren(children);
					node.setPicStatus(picStatus);
				}
			}

		}
		reader.endObject();
		return node;
	}

	private ArrayList<Node> readChildren(JsonReader reader) throws IOException {
		ArrayList<Node> children = new ArrayList<>();
		reader.beginArray();
		while (reader.hasNext()) {
			children.add(readNodeTree(reader));
		}
		reader.endArray();
		return children;
	}

	@Override
	public void write(JsonWriter writer, Node node) throws IOException {
		if (node == null) {
			return;
		}
		writer.beginObject();
		if (node.getParent() == null) {
			writer.name("parent");
			writer.nullValue();
		} else {
			writer.name("parent");
			writer.value(node.getParent().getName());
		}
		writer.name("name");
		writer.value(node.getName());
		writer.name("picStatus");
		writer.value(node.getPicStatus());
		writer.name("isLeaf");
		writer.value(node.isLeaf());
		writer.name("address");
		writer.value(node.getAddress());
		writer.name("city");
		writer.value(node.getCity());
		writer.name("result");
		writer.value(node.getResult());
		writer.name("photoFileName");
		writer.value(node.getPhoto());
		writer.name("children");
		writer.beginArray();
		if (node.hasChildren()) {
			for (Node childNode : node.getChildren()) {
				this.write(writer, childNode);
			}
		}
		writer.endArray();
		writer.endObject();
	}

}
