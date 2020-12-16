package maptools.test;

import maptools.DiaboticalMap;

public class DiaboticalMapCreatorTest {

	public static void main(String[] args) throws Exception {
		DiaboticalMap map = DiaboticalMap.readMap("template.rbe");
		map.writeMap("template_write_test.rbe", true);
	}

}
