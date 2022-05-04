package dataModel;

import java.util.ArrayList;

public class Node {
	private Node parent;
	private ArrayList<Node> children;
	private String name;

	private boolean isLeaf;
	private String address; // Set only if isLeaf=true;
	private String city; // Set only if isLeaf=true;
	private int result; // Set only if isLeaf=true;
	private String photoFileName;

	public Node() {
		this.parent = null;
		this.children = new ArrayList<Node>();
		this.isLeaf = false;
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
		this.name = name;
		this.address = "";
		this.city = "";
		this.result = 0;
		this.photoFileName = null;
	}

	Node(Node parent, ArrayList<Node> children, String name, boolean isLeaf, String address, String city, int result,
			String photo) {
		this.parent = parent;
		this.children = children;
		this.isLeaf = isLeaf;
		this.name = name;
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
			return node;
		} else return null;
	}

	public void createSubFolder(Node parent, Node subFolder) {
		if (isAdditionAdmissible(parent)) {
		subFolder.parent=parent;
		parent.children.add(subFolder);
		} 
	}
	
	public Node createLeaf(Node parent, String name) {
		if (isAdditionAdmissible(parent)) {	
		Node node = new Node();
		node.parent = parent;
		parent.children.add(node);
		node.children = new ArrayList<Node>();
		node.isLeaf = true;
		node.name = name;
		node.address = "";
		node.city = "";
		node.result = 0;
		return node;
		} else return null;
	}

	public void createLeaf( Node leaf) {
		if (!this.isLeaf) {
		leaf.parent=this;
		this.children.add(leaf);
		} 
	}
	
	public static Node makeDummyTree() {	
		Node root = new Node("root");
				
		Node simpsons = root.createSubFolder("Simpsons");
		Node simpsonHomer = new Node(simpsons, null, "Homer Simpson",
				true, "building 1","Simpsons town", 3, "src/photos/SimpsonHomer256x256.png");
		Node simpsonMarge = new Node(simpsons, null, "Marge Simpson",
				true, "building 1","Simpsons town", 3, "src/photos/SimpsonMarge256x256.png");
		Node simpsonBarth = new Node(simpsons, null, "Barth Simpson",
				true, "building 1","Simpsons town", 3, "src/photos/SimpsonBarth256x256.png");
		Node simpsonElisabeth = new Node(simpsons, null, "Elisabeth Simpson", true, "building 1","Simpsons town", 3, null);
		Node simpsonMaggy = new Node(simpsons, null, "Maggy Simpson", true, "building 1","Simpsons town", 3, null);
		
		simpsons.createLeaf(simpsonHomer);
		simpsons.createLeaf(simpsonMarge);
		simpsons.createLeaf(simpsonBarth);
		simpsons.createLeaf(simpsonElisabeth);
		simpsons.createLeaf(simpsonMaggy);
		
		Node flingstones = root.createSubFolder("Flingstones");	
		Node flingstoneFred = new Node(flingstones, null, "Fred Flingstone", true, "building 1","Flingstone town", 3, null);
		Node flingstoneWilma = new Node(flingstones, null, "Wilma Flingstone", true, "building 1","Flingstone town", 3, null);
		Node flingstoneBarni = new Node(flingstones, null, "Barni Flingstone", true, "building 1","Flingstone town", 3, null);
		Node flingstoneBetty = new Node(flingstones, null, "Betty Flingstone", true, "building 1","Flingstone town", 3, null);
		Node flingstoneBamBam = new Node(flingstones, null, "BamBam Flingstone", true, "building 1","Flingstone town", 3, null);
		
		flingstones.createLeaf(flingstoneFred);
		flingstones.createLeaf(flingstoneWilma);
		flingstones.createLeaf(flingstoneBarni);
		flingstones.createLeaf(flingstoneBetty);
		flingstones.createLeaf(flingstoneBamBam);
				
		Node additionalPersonage = new Node(root, null, "Additional personage", true, "building 1","Flingstone town", 3, null);
		root.createLeaf(additionalPersonage);
		return root;
	}
	
	private boolean isAdditionAdmissible(Node parent) {
		if (parent == null) {
			System.out.println("Class Node - trying to create child undeer the root folder");
			return false;
		}
		if (parent.isLeaf) {
			System.out.println("Class Node - trying to create child undeer the leaf");
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
		if(this.isLeaf) 
		this.children = children;
	}
	
	public void removeLeaf(Node child) { //Remove leaf only in case if leaf is really leaf
		if(child.isLeaf)
		this.children.remove(child);
	}
	
	public void removeEmptySubFolder(Node child){
		if(child.children.isEmpty())
			this.children.remove(child);
	}
	
	public void removeFullSubfolder(Node child) {
		this.children.remove(child);
	}

	public boolean hasChildren() {
		if(this.children==null) {
			return false;
		} else
		return this.children.size()>0;
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
	
	public void setLeaf(boolean isLeaf) { //TODO Should erase entire data if leaf becomes folder???
		this.isLeaf = isLeaf;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) { // Shouldn't set value if object is folder
		if(this.isLeaf) 
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {// Shouldn't set value if object is folder
		if(this.isLeaf) 
		this.city = city;
	}
	
	public int getResult() {
		return result;
	}
	
	public void setResult(int result) { // Shouldn't set value if object is folder
		if(this.isLeaf) 
		this.result = result;
	}
	
	public String getPhoto() {
		return photoFileName;
	}
	
	public void setPhoto(String photo) { // Shouldn't set value if object is folder
		if(this.isLeaf) 
		this.photoFileName = photo;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
