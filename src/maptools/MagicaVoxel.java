package maptools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import maptools.io.LittleEndianDataInputStream;
import maptools.io.LittleEndianDataOutputStream;

public class MagicaVoxel {
	
	public int size_x;
	public int size_y;
	public int size_z;
	public byte voxels[][];

	public MagicaVoxel() {
	}

	private void assert_bytes(byte a[], byte b[])
	{
		if(a.length != b.length)
			throw new RuntimeException("assert failed: a.length != b.length");
		for (int i = 0; i < a.length; i++) {
			if(a[i] != b[i])
				throw new RuntimeException("assert failed: a[i] != b[i] at i=" + i);
		}
	}
	
	public void readVoxFile(String name) throws IOException {
		LittleEndianDataInputStream ledis = new LittleEndianDataInputStream(new FileInputStream(name));
		
		assert_bytes(ledis.readBytes(4), "VOX ".getBytes());
		
		int version = ledis.readInt(); // usually 150
		
		assert_bytes(ledis.readBytes(4), "MAIN".getBytes());
		
		int main_num_bytes = ledis.readInt(); // usually 0;
		int main_num_bytes_children = ledis.readInt();
		
		assert_bytes(ledis.readBytes(4), "SIZE".getBytes());
		
		int size_num_bytes = ledis.readInt();
		int size_num_bytes_children = ledis.readInt(); // usually 0;

		size_x = ledis.readInt();
		size_y = ledis.readInt();
		size_z = ledis.readInt();
		
		assert_bytes(ledis.readBytes(4), "XYZI".getBytes());
		int xyzi_num_bytes = ledis.readInt();
		int xyzi_num_bytes_children = ledis.readInt(); // usually 0;
		
		int num_voxels = ledis.readInt();

		voxels = new byte[num_voxels][3];
		
		for (int n = 0; n < num_voxels; n++) {
			voxels[n][0] = ledis.readByte();
			voxels[n][2] = ledis.readByte(); // remember y and z is swapped in MagicaVoxel;
			voxels[n][1] = ledis.readByte();
			byte i = ledis.readByte();
		}
	}
	

	public static void writeMAIN(LittleEndianDataOutputStream ledos, byte[] size, byte[] xyzi) throws IOException {
		ledos.writeBytes("MAIN");
		ledos.writeInt32(0);
		ledos.writeInt32(size.length + xyzi.length);
		ledos.write(size);
		ledos.write(xyzi);
	}
	
	public static ByteArrayOutputStream writeSIZE(int x, int y, int z) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LittleEndianDataOutputStream ledos = new LittleEndianDataOutputStream(baos);
		
		ledos.writeBytes("SIZE");
		ledos.writeInt32(12); // num_bytes
		ledos.writeInt32(0);  // num_bytes_children
		ledos.writeInt32(x);
		ledos.writeInt32(y);
		ledos.writeInt32(z);
		
		ledos.flush();
		return baos;
	}

	public static ByteArrayOutputStream writeXYZI(byte voxels[][]) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LittleEndianDataOutputStream ledos = new LittleEndianDataOutputStream(baos);
		
		ledos.writeBytes("XYZI");
		ledos.writeInt32(voxels.length * 4 + 4); // num_bytes
		ledos.writeInt32(0);  // num_bytes_children
		ledos.writeInt32(voxels.length); // num_voxels
				
		for (int i = 0; i < voxels.length; i++) {
			byte[] xyz = voxels[i];
			ledos.writeByte(xyz[0]);
			ledos.writeByte(xyz[1]);
			ledos.writeByte(xyz[2]);
			ledos.writeByte((byte)55);
		}
		
		ledos.flush();
		return baos;
	}

}
