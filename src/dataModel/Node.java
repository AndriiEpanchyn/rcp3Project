package dataModel;

import java.util.ArrayList;

public class Node {
	private Node parent;
	private String name;
	private int picStatus; // 0 - unchecked, 1 - checked, 2 - declined

	private boolean isLeaf;
	private String address; // Set only if isLeaf=true;
	private String city; // Set only if isLeaf=true;
	private int result; // Set only if isLeaf=true;
	private String photoFileName;
	private ArrayList<Node> children;

	public Node() {
		this.parent = null;
		this.children = new ArrayList<Node>();
		this.isLeaf = false;
		this.picStatus = 0;
		this.name = "";
		this.address = "";
		this.city = "";
		this.result = 0;
		this.photoFileName = null;
	}

	public Node(String name) {
		this.parent = null;
		this.children = new ArrayList<Node>();
		this.isLeaf = false;
		this.picStatus = 0;
		this.name = name;
		this.address = "";
		this.city = "";
		this.result = 0;
		this.photoFileName = null;
	}

	public Node(Node parent, ArrayList<Node> children, String name, boolean isLeaf, String address, String city,
			int result, String photo) {
		this.parent = parent;
		this.children = children;
		this.isLeaf = isLeaf;
		this.name = name;
		this.picStatus = 0;
		this.address = address;
		this.city = city;
		this.result = result;
		this.photoFileName = photo;
	}

	public Node createSubFolder(String name) {
		if (!this.isLeaf) {
			Node node = new Node();
			node.parent = this;
			this.children.add(node);
			node.isLeaf = false;
			node.name = name;
			node.picStatus = 0;
			return node;
		} else
			return null;
	}

	public void createSubFolder(Node parent, Node subFolder) {
		if (isAdditionAdmissible(parent)) {
			subFolder.parent = parent;
			parent.children.add(subFolder);
		}
	}

	public Node createLeaf(String name) {
		if (isAdditionAdmissible(this)) {
			Node node = new Node(name);
			node.parent = this;
			node.picStatus = 0;
			this.children.add(node);
			node.isLeaf = true;
			return node;
		} else
			return null;
	}

	public void createLeaf(Node leaf) {
		if (!this.isLeaf) {
			leaf.parent = this;
			this.children.add(leaf);
		}
	}

	public static Node makeDummyTree() {
		Node node = new Node();
		Node root = node.createSubFolder("Folder");
		Node simpsons = root.createSubFolder("Simpsons");
		Node simpsonHomer = new Node(simpsons, null, "Homer Simpson", true, "building 1", "Simpsons town", 3,
				"src/photos/SimpsonHomer256x256.png");
		Node simpsonMarge = new Node(simpsons, null, "Marge Simpson", true, "building 1", "Simpsons town", 3,
				"src/photos/SimpsonMarge256x256.png");
		Node simpsonBarth = new Node(simpsons, null, "Barth Simpson", true, "building 1", "Simpsons town", 3,
				"src/photos/SimpsonBarth256x256.png");
		Node simpsonElisabeth = new Node(simpsons, null, "Elisabeth Simpson", true, "building 1", "Simpsons town", 3,
				"src/photos/SimpsonLisa256x256.png");
		Node simpsonMaggy = new Node(simpsons, null, "Maggy Simpson", true, "building 1", "Simpsons town", 3,
				"src/photos/SimpsonMaggie256x256.png");
		simpsonMaggy.setPicStatus(1);

		simpsons.createLeaf(simpsonHomer);
		simpsons.createLeaf(simpsonMarge);
		simpsons.createLeaf(simpsonBarth);
		simpsons.createLeaf(simpsonElisabeth);
		simpsons.createLeaf(simpsonMaggy);
		simpsons.setPicStatus(2);

		Node flingstones = root.createSubFolder("Flingstones");
		Node flingstoneFred = new Node(flingstones, null, "Fred Flingstone", true, "building 1", "Flingstone town", 3,
				"src/photos/FlinstoneFred306x434.png");
		Node flingstoneWilma = new Node(flingstones, null, "Wilma Flingstone", true, "building 1", "Flingstone town", 3,
				"src/photos/FlinstoneWilma256x256.png");
		Node flingstoneBarney = new Node(flingstones, null, "Barni Flingstone", true, "building 1", "Flingstone town",
				3, "src/photos/FlinstoneBarney256x256.png");
		Node flingstoneBetty = new Node(flingstones, null, "Betty Flingstone", true, "building 1", "Flingstone town", 3,
				"src/photos/FlinstoneBetty256x256.png");
		Node flingstoneBamBam = new Node(flingstones, null, "BamBam Flingstone", true, "building 1", "Flingstone town",
				3, "src/photos/FlinstoneBamBam256x256.png");

		flingstones.createLeaf(flingstoneFred);
		flingstones.createLeaf(flingstoneWilma);
		flingstones.createLeaf(flingstoneBarney);
		flingstones.createLeaf(flingstoneBetty);
		flingstones.createLeaf(flingstoneBamBam);
		flingstones.setPicStatus(1);

		Node additionalPersonage = new Node(root, null, "Additional personage", true, "building 1", "Flingstone town",
				3, null);
		root.createLeaf(additionalPersonage);
		additionalPersonage.setPicStatus(2);
		return node;
	}

	private boolean isAdditionAdmissible(Node parent) {
		if (parent == null) {
			return false;
		}
		if (parent.isLeaf) {
			return false;
		}
		return true;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {// Shouldn't set value if object is folder
		this.children = children;
	}

	public void removeLeaf() { // Remove leaf only in case if leaf is really leaf
		if (this.isLeaf)
			this.parent.children.remove(this);
	}

	public void removeEmptySubFolder() {
		if (this.children.isEmpty())
			this.parent.children.remove(this);
	}

	public void removeFullSubfolder() {
		this.parent.children.remove(this);
	}

	public boolean hasChildren() {
		return this.children == null ? false : this.children.size() > 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) { // Shouldn't set value if object is folder
		if (this.isLeaf)
			this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {// Shouldn't set value if object is folder
		if (this.isLeaf)
			this.city = city;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) { // Shouldn't set value if object is folder
		if (this.isLeaf)
			this.result = result;
	}

	public String getPhoto() {
		return photoFileName;
	}

	public void setPhoto(String photo) { // Shouldn't set value if object is folder
		if (this.isLeaf)
			this.photoFileName = photo;
	}

	public void setPicStatus(int value) {
		if (value == 0 || value == 1 || value == 2) {
			this.picStatus = value;
		} else {
			this.picStatus = 0;
		}
	}

	public int getPicStatus() {
		return this.picStatus;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String toPrint() {
		StringBuilder stringifiedChildren = new StringBuilder();
		ArrayList<Node> childrenList = this.children;
		if (childrenList != null) {
			for (Node child : childrenList) {
				stringifiedChildren.append(child.toPrint() + "\n");
			}
		}

		String answer = "[" + (name != null ? "name=" + name + ", " : "")
				+ (parent != null ? "parent=" + parent + ", " : "") + ", isLeaf=" + isLeaf + ", picStatus=" + picStatus
				+ (address != null ? "address=" + address + ", " : "") + (city != null ? "city=" + city + ", " : "")
				+ "result=" + result + ", " + (photoFileName != null ? "photoFileName=" + photoFileName : "\n")
				+ (children != null ? "children=" + stringifiedChildren : "") + "]";
		return answer;
	}

	public void moveTo(Node target, boolean insertAfter) {
		if (target == null) {
			moveTo(SessionManager.getSession(), insertAfter);
			return;
		}
		if (Node.contains(this, target)) {
			return;
		}
		int deltaIndex = insertAfter ? 1 : 0;
		if (target.isLeaf) {
			int targetIndex = target.parent.children.indexOf(target) + deltaIndex;
			// move in the same folder
			if (this.parent == target.parent) {
				this.parent.children.remove(this);
				this.setParent(target.parent);
				if (targetIndex < target.parent.children.size() && targetIndex >= 0) {
					target.parent.children.add(targetIndex, this);
				} else {
					target.parent.children.add(this);
				}
			} else {
				// move to another folder
				this.parent.getChildren().remove(this);
				this.setParent(target.parent);
				if (targetIndex < target.parent.children.size() - 1 && targetIndex >= 0) {
					target.parent.children.add(targetIndex, this);
				} else {
					target.parent.children.add(this);
				}
			}

		} else {

			// if target is folder
			this.parent.getChildren().remove(this);
			this.setParent(target);
			target.children.add(0, this);
		}

	}

	public static boolean contains(Node source, Node target) {
		if (!source.hasChildren()) {
			return false;
		}
		for (Node child : source.children) {
			if (child == target) {
				return true;
			}
			if (child.isLeaf() == false) {
				if (Node.contains(child, target)) {
					return true;
				}
			}
		}
		return false;
	}

}
