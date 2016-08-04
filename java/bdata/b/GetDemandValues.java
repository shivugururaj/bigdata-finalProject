package bdata.b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class GetDemandValues {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[] routefileArray = { "routeMaps/rutaMap0", "routeMaps/rutaMap1", "routeMaps/rutaMap2",
				"routeMaps/rutaMap3" };
		String[] canalFileArray = { "canalMaps/canalFile1", "canalMaps/canalFile2", "canalMaps/canalFile3",
				"canalMaps/canalFile4" };
		String[] agencyFileArray = { "agencyMaps/agencyFile1", "agencyMaps/agencyFile2", "agencyMaps/agencyFile3",
				"agencyMaps/agencyFile4" };

		String[] cliFileArray = { "clientMaps/clientFile1", "clientMaps/clientFile2", "clientMaps/clientFile3",
				"clientMaps/clientFile4" };
		String[] prodFileArray = { "productMaps/productFile1", "productMaps/productFile2", "productMaps/productFile3",
				"productMaps/productFile4" };

		String[] trainFileArray = { "trainFiles/trainFile00", "trainFiles/trainFile01", "trainFiles/trainFile02",
				"trainFiles/trainFile03", "trainFiles/trainFile04", "trainFiles/trainFile05", "trainFiles/trainFile06",
				"trainFiles/trainFile07", "trainFiles/trainFile08", "trainFiles/trainFile09", "trainFiles/trainFile10",
				"trainFiles/trainFile11", "trainFiles/trainFile12", "trainFiles/trainFile13", "trainFiles/trainFile14",
				"trainFiles/trainFile15", "trainFiles/trainFile16", "trainFiles/trainFile17", "trainFiles/trainFile18",
				"trainFiles/trainFile19", "trainFiles/trainFile20", "trainFiles/trainFile21", "trainFiles/trainFile22",
				"trainFiles/trainFile23", "python/test.csv" };

		String[] testFileArray = { "finalTest/testFile0", "finalTest/testFile1" };

		for (String file : trainFileArray)

		{

			System.out.println("file: " + file);
			String csvFile = file;
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			File oldFile = new File(file);
			File newTrainFile = new File("DemandMapFiles/" + oldFile.getName().trim());
			if (newTrainFile.exists()) {
				newTrainFile.delete();
				newTrainFile.createNewFile();
			}

			StringBuilder sb;
			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					sb = new StringBuilder();
					// use comma as separator
					String[] lineSplit = line.split(cvsSplitBy);
					String semana = lineSplit[0];
					String Agencia_ID = lineSplit[1];
					String Canal_ID = lineSplit[2];
					String Ruta_SAK = lineSplit[3];
					String Cliente_ID = lineSplit[4];
					String Producto_ID = lineSplit[5];
					String Demanda_uni_equil = lineSplit[6];
					sb.append(semana).append(cvsSplitBy).append(Ruta_SAK).append(cvsSplitBy).append(Canal_ID)
							.append(cvsSplitBy).append(Agencia_ID).append(cvsSplitBy).append(Cliente_ID)
							.append(cvsSplitBy).append(Producto_ID).append("\r\n");

					FileWriter writer = new FileWriter("finalTest/new" + oldFile.getName().trim(), true);
					writer.append(sb.toString());
					writer.flush();
					writer.close();

				}

				/*
				 * fw.write(sb.toString().trim()); fw.flush(); fw.close();
				 */
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		System.out.println("Test Complete");
	}

}
