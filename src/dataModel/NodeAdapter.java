package dataModel;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class NodeAdapter extends TypeAdapter<Node> {

	public NodeAdapter() {

	}

	@Override
	public Node read(JsonReader reader) throws IOException {
		Node node = new Node();
		String name;
		String picStatus;
		String isLeaf;
		String address;
		String city;
		String result;
		String photoFileName;

		String fieldname = "";
		reader.beginObject();
		while (reader.hasNext()) {
			JsonToken token = reader.peek();
			if (token.equals(JsonToken.NAME)) {
				// get the current token
				fieldname = reader.nextName();
			}
//			if ("parent".equals(fieldname)) {
//				token = reader.peek();
//				String parent = reader.nextString();
//			}

			if ("name".equals(fieldname)) {
				token = reader.peek();
				name = reader.nextString();
			}
			if ("picStatus".equals(fieldname)) {
				token = reader.peek();
				picStatus = reader.nextString();
			}
			if ("isLeaf".equals(fieldname)) {
				token = reader.peek();
				isLeaf = reader.nextString();
			}
			if ("address".equals(fieldname)) {
				token = reader.peek();
				address = reader.nextString();
			}
			if ("city".equals(fieldname)) {
				token = reader.peek();
				city = reader.nextString();
			}
			if ("result".equals(fieldname)) {
				token = reader.peek();
				result = reader.nextString();
			}
			if ("photoFileName".equals(fieldname)) {
				token = reader.peek();
				photoFileName = reader.nextString();
			}
		}
		reader.endObject();
		return node;
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
