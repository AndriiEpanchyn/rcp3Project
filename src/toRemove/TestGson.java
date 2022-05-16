package toRemove;

import com.google.gson.Gson;

public class TestGson {
	public static void main(String[] args) {

		Records r1 = new Records("1-2", 1);
		Records r12 = new Records("1-2", 1);
		Records r0 = new Records("0", 1);
		Records r13 = new Records("1-3", 1);
		Records r11 = new Records("1", 1);
		Records r21 = new Records("2-1", 1);
		Records r22 = new Records("2-2", 1);
		Records r23 = new Records("2-3", 1);
		Records r2 = new Records("2", 1);

		r1.addRecord(r11, 0);
		r1.addRecord(r12, 1);
		r1.addRecord(r11, 2);

		r2.addRecord(r21, 0);
		r2.addRecord(r22, 1);
		r2.addRecord(r23, 2);

		r0.addRecord(r1, 0);
		r0.addRecord(r2, 1);

		Gson toGson = new Gson();

		String a = toGson.toJson(r0);

		Records arr2 = toGson.fromJson(a, Records.class);
		System.out.println(arr2);
	}
}
