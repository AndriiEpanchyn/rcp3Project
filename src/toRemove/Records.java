package toRemove;

public class Records {
	String name;
	int value;
	Records[] list;

	Records() {
		this.name = "";
		this.value = 0;
		this.list = new Records[10];
	}

	Records(String name, int value) {
		this.name = name;
		this.value = value;
		this.list = new Records[10];
	}

	void addRecord(Records point, int index) {
		this.list[index] = point;
	}

	@Override
	public String toString() {
		return "Records [" + (name != null ? "name=" + name + ", " : "") + "value=" + value + ", "
				+ (list != null ? "list=" + list : "") + "]";
	}

}