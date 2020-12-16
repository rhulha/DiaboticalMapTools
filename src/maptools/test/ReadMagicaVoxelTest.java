package maptools.test;

import java.io.IOException;

import maptools.MagicaVoxel;

public class ReadMagicaVoxelTest {
	public static void main(String[] args) throws IOException {
		MagicaVoxel mv = new MagicaVoxel();
		mv.readVoxFile("castle.vox");
		System.out.println(mv.size_x);
		System.out.println(mv.size_y);
		System.out.println(mv.size_z);
		System.out.println(mv.voxels.length);
	}
}
