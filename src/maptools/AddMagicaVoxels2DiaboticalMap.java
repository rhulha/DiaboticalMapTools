package maptools;

import java.io.IOException;

public class AddMagicaVoxels2DiaboticalMap {

	
	public static void main(String[] args) throws IOException {
		DiaboticalMap map = DiaboticalMap.readMap("template.rbe");	
		
		MagicaVoxel mv = new MagicaVoxel();
		mv.readVoxFile("castle.vox");
				
		for (int i = 0; i < mv.voxels.length; i++) {
			byte v[] = mv.voxels[i];
			map.blocks.add(new DiaboticalBlock(v[0], v[1], v[2]));
		}
		
		map.writeMap("castle.rbe", true);
		
		
		
	}
	
}
