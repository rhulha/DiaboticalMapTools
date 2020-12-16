package maptools.test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import maptools.MagicaVoxel;
import maptools.io.LittleEndianDataOutputStream;

public class GenerateMagicaVoxelTest {
	public static void main(String[] args) throws IOException {
		LittleEndianDataOutputStream ledos = new LittleEndianDataOutputStream(new FileOutputStream("test.vox"));
		ledos.writeBytes("VOX ");
		ledos.writeInt32(150);
		ByteArrayOutputStream size = MagicaVoxel.writeSIZE(3,3,3);
		byte voxels[][] = { {0,0,0}, {1,0,0}};
		ByteArrayOutputStream xyzi = MagicaVoxel.writeXYZI(voxels);
		MagicaVoxel.writeMAIN(ledos, size.toByteArray(), xyzi.toByteArray());
	}
}
