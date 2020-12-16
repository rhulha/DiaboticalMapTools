package maptools.test;

import java.io.IOException;

import maptools.ConvertDiaboticalMap2MagicaVoxel;

public class DiaboticalMap2MagicaVoxelTest {
	public static void main(String[] args) throws IOException {
		ConvertDiaboticalMap2MagicaVoxel.convert("template.rbe", "template.vox");
	}
}
