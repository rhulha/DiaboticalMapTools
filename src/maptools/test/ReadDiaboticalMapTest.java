package maptools.test;
import java.io.IOException;

import maptools.DiaboticalMap;

public class ReadDiaboticalMapTest {
	public static void main(String[] args) throws IOException {
		DiaboticalMap map = DiaboticalMap.readMap("template.rbe");
		System.out.println(new String(map.header));
		System.out.println(map.version);
		System.out.println(map.material_names);
		System.out.println(map.blocks);
	}
}
