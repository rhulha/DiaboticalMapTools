package maptools;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import maptools.io.LittleEndianDataOutputStream;

public class ConvertDiaboticalMap2MagicaVoxel {

	public static void convert(String diaMapName, String voxMapName) throws IOException, FileNotFoundException {
		DiaboticalMap map = DiaboticalMap.readMap(diaMapName);
		LittleEndianDataOutputStream ledos = new LittleEndianDataOutputStream(new FileOutputStream(voxMapName));

		ledos.writeBytes("VOX ");
		ledos.writeInt32(150);

		ByteArrayOutputStream size = MagicaVoxel.writeSIZE(100, 100, 100);

		byte voxels[][] = new byte[map.blocks.size()][3];

		for (int i = 0; i < map.blocks.size(); i++) {
			voxels[i][0] = (byte) (map.blocks.get(i).x + 50);
			voxels[i][2] = (byte) (map.blocks.get(i).y + 50); // swap y and z
			voxels[i][1] = (byte) (map.blocks.get(i).z + 50);
		}

		ByteArrayOutputStream xyzi = MagicaVoxel.writeXYZI(voxels);

		MagicaVoxel.writeMAIN(ledos, size.toByteArray(), xyzi.toByteArray());
	}

}
