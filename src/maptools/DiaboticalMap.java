package maptools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import maptools.io.LittleEndianDataInputStream;
import maptools.io.LittleEndianDataOutputStream;

public class DiaboticalMap {

	public byte[] header = new byte[4];
	public int version;
	public byte[] u1;
	
	String material_defaults[] = {  "default", "floor_plates_heat02", "metalwall_heat01", "floor_plates_heat" }; 
	public ArrayList<String> material_names = new ArrayList<String>();

	public int u2;
	public ArrayList<DiaboticalBlock> blocks = new ArrayList<DiaboticalBlock>();
	public byte[] u3;
	public byte[] restOfMap;

	private DiaboticalMap() {
	}

	public static DiaboticalMap readMap(String name) throws IOException {
		DiaboticalMap map = new DiaboticalMap();
		LittleEndianDataInputStream ledis = new LittleEndianDataInputStream(new FileInputStream(name));

		ledis.read(map.header);
		map.version = ledis.readInt();
		map.u1 = ledis.readBytes(20);
		ledis = new LittleEndianDataInputStream(new GZIPInputStream(ledis));
		int material_count = ledis.readByte();
		for (int i = 0; i < material_count - 1; i++) {
			int material_strlen = ledis.readInt();
			map.material_names.add(new String(ledis.readBytes(material_strlen)));
		}
		map.u2 = ledis.readInt();

		int block_count = ledis.readInt();
		for (int i = 0; i < block_count; i++) {
			map.blocks.add(new DiaboticalBlock(ledis));
		}

		map.restOfMap = ledis.readBytesUntilEnd();

		// int u3_count = ledis.readInt();
		// map.u3 = ledis.readBytes(u3_count*16);

		// int entity_count = ledis.readInt();

		ledis.close();
		return map;
	}
	
	private static void writeBlocks(DiaboticalMap map, LittleEndianDataOutputStream ledos) throws IOException {
		ledos.writeInt32(map.blocks.size()); // block_count
		for (DiaboticalBlock block : map.blocks) {
			ledos.write(block.toByteArray());
		}
	}

	private static void writeMaterials(LittleEndianDataOutputStream ledos, String[] material) throws IOException {
		ledos.writeByte((byte) (material.length + 1));
		for (String m : material) {
			ledos.writeLengthAndString(m);
		}
	}

	
	public void writeMap(String name, boolean createUnzippedCopy) throws IOException {
		LittleEndianDataOutputStream ledos = new LittleEndianDataOutputStream(new FileOutputStream(name));
		
		LittleEndianDataOutputStream unzipped=null;
		
		if(createUnzippedCopy)
			unzipped = new LittleEndianDataOutputStream(new FileOutputStream(name+".unz"));
		
		ledos.writeBytes("REBM");
		ledos.writeInt32(25); // version
		ledos.writeBytes(u1); // u1
		ledos.activateGzip();
		
		
		writeMaterials(ledos, material_names.toArray(new String[0]));
		if(createUnzippedCopy)
			writeMaterials(unzipped, material_names.toArray(new String[0]));
		
		ledos.writeInt32(0); // u2
		if(createUnzippedCopy)
			unzipped.writeInt32(0); // u2
		
		writeBlocks(this, ledos);
		if(createUnzippedCopy)
			writeBlocks(this, unzipped);
		
		ledos.write(this.restOfMap);
		if(createUnzippedCopy)
			unzipped.write(this.restOfMap);
		
		ledos.close();
		if(createUnzippedCopy)
			unzipped.close();

	}


}
