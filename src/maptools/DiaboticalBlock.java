package maptools;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import maptools.io.LittleEndianDataInputStream;
import maptools.io.LittleEndianDataOutputStream;

public class DiaboticalBlock {

	int x;
	int y;
	int z;
	byte type=1;
	byte u1[] = new byte[12];

	byte mats_front=2;
	byte mats_left=2;
	byte mats_back=2;
	byte mats_right=2;
	byte mats_top=1;
	byte mats_bottom=3;
	byte u2;

	byte mat_offs_front_x;
	byte mat_offs_front_y;
	byte mat_offs_front_r;
	byte mat_offs_left_x;
	byte mat_offs_left_y;
	byte mat_offs_left_r;
	byte mat_offs_back_x;
	byte mat_offs_back_y;
	byte mat_offs_back_r;
	byte mat_offs_right_x;
	byte mat_offs_right_y;
	byte mat_offs_right_r;
	byte mat_offs_top_x;
	byte mat_offs_top_y;
	byte mat_offs_top_r;
	byte mat_offs_bottom_x;
	byte mat_offs_bottom_y;
	byte mat_offs_bottom_r;
	byte orient=1;
	byte u3;

	public DiaboticalBlock(byte x, byte y, byte z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	DiaboticalBlock(LittleEndianDataInputStream ledis) throws IOException {
		x = ledis.readInt();
		y = ledis.readInt();
		z = ledis.readInt();
		type = ledis.readByte();
		u1 = ledis.readBytes(12);

		mats_front = ledis.readByte();
		mats_left = ledis.readByte();
		mats_back = ledis.readByte();
		mats_right = ledis.readByte();
		mats_top = ledis.readByte();
		mats_bottom = ledis.readByte();
		u2 = ledis.readByte();

		mat_offs_front_x = ledis.readByte();
		mat_offs_front_y = ledis.readByte();
		mat_offs_front_r = ledis.readByte();
		mat_offs_left_x = ledis.readByte();
		mat_offs_left_y = ledis.readByte();
		mat_offs_left_r = ledis.readByte();
		mat_offs_back_x = ledis.readByte();
		mat_offs_back_y = ledis.readByte();
		mat_offs_back_r = ledis.readByte();
		mat_offs_right_x = ledis.readByte();
		mat_offs_right_y = ledis.readByte();
		mat_offs_right_r = ledis.readByte();
		mat_offs_top_x = ledis.readByte();
		mat_offs_top_y = ledis.readByte();
		mat_offs_top_r = ledis.readByte();
		mat_offs_bottom_x = ledis.readByte();
		mat_offs_bottom_y = ledis.readByte();
		mat_offs_bottom_r = ledis.readByte();
		orient = ledis.readByte();
		u3 = ledis.readByte();
	}

	@Override
	public String toString() {
		return "("+x+","+y+","+z+") "+type;
	}

	public byte[] toByteArray() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LittleEndianDataOutputStream ledos = new LittleEndianDataOutputStream(baos);
		ledos.writeInt32(x);
		ledos.writeInt32(y);
		ledos.writeInt32(z);
		
		ledos.writeByte(type);
		ledos.write(u1);

		ledos.writeByte(mats_front);
		ledos.writeByte(mats_left);
		ledos.writeByte(mats_back);
		ledos.writeByte(mats_right);
		ledos.writeByte(mats_top);
		ledos.writeByte(mats_bottom);
		ledos.writeByte(u2);

		ledos.writeByte(mat_offs_front_x);
		ledos.writeByte(mat_offs_front_y);
		ledos.writeByte(mat_offs_front_r);
		ledos.writeByte(mat_offs_left_x);
		ledos.writeByte(mat_offs_left_y);
		ledos.writeByte(mat_offs_left_r);
		ledos.writeByte(mat_offs_back_x);
		ledos.writeByte(mat_offs_back_y);
		ledos.writeByte(mat_offs_back_r);
		ledos.writeByte(mat_offs_right_x);
		ledos.writeByte(mat_offs_right_y);
		ledos.writeByte(mat_offs_right_r);
		ledos.writeByte(mat_offs_top_x);
		ledos.writeByte(mat_offs_top_y);
		ledos.writeByte(mat_offs_top_r);
		ledos.writeByte(mat_offs_bottom_x);
		ledos.writeByte(mat_offs_bottom_y);
		ledos.writeByte(mat_offs_bottom_r);
		ledos.writeByte(orient);
		ledos.writeByte(u3);
		ledos.close();
		return baos.toByteArray();
	}

}
