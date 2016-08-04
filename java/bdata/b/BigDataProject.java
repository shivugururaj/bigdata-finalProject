package bdata.b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class BigDataProject {

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

		//String[] testFileArray = { "finalTest/testFile0", "finalTest/testFile1" };
		HashMap<String, String> semanaMap = new HashMap<String, String>();
		HashMap<String, String> routeMap = new HashMap<String, String>();
		HashMap<String, String> canalMap = new HashMap<String, String>();
		HashMap<String, String> agencyMap = new HashMap<String, String>();
		HashMap<String, String> clientMap = new HashMap<String, String>();
		HashMap<String, String> prodMap = new HashMap<String, String>();

		semanaMap = getSemanaMap();
		routeMap = getAssociatedMap(routefileArray);
		canalMap = getAssociatedMap(canalFileArray);
		agencyMap = getAssociatedMap(agencyFileArray);
		clientMap = getAssociatedMap(cliFileArray);
		prodMap = getAssociatedMap(prodFileArray);

		for (String file : trainFileArray) {
			System.out.println("file: " + file);
			String csvFile = file;
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			File oldFile = new File(file);
			File newTrainFile = new File("newTrainFiles/" + oldFile.getName().trim());
			if (newTrainFile.exists()) {
				newTrainFile.delete();
				newTrainFile.createNewFile();
			}

			// FileWriter fw = new FileWriter(newTrainFile);
			StringBuilder sb;
			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					sb = new StringBuilder(); // use comma as separator
					String[] lineSplit = line.split(cvsSplitBy);
					String semana = lineSplit[0];
					String Agencia_ID = lineSplit[1];
					String Canal_ID = lineSplit[2];
					String Ruta_SAK = lineSplit[3];
					String Cliente_ID = lineSplit[4];
					String Producto_ID = lineSplit[5];
					String Demanda_uni_equil = lineSplit[6];

					if (semanaMap.containsKey(semana)) {
						semana = semanaMap.get(semana);
					} else {
						semana = "5";
					}

					if (routeMap.containsKey(Ruta_SAK)) {
						Ruta_SAK = routeMap.get(Ruta_SAK);
					} else {
						Ruta_SAK = "5";
					}

					if (canalMap.containsKey(Canal_ID)) {
						Canal_ID = canalMap.get(Canal_ID);
					} else {
						Canal_ID = "5";
					}

					if (agencyMap.containsKey(Agencia_ID)) {
						Agencia_ID = agencyMap.get(Agencia_ID);
					} else {
						Agencia_ID = "5";
					}

					if (clientMap.containsKey(Cliente_ID)) {
						Cliente_ID = clientMap.get(Cliente_ID);
					} else {
						Cliente_ID = "5";
					}
					if (prodMap.containsKey(Producto_ID)) {
						Producto_ID = prodMap.get(Producto_ID);
					} else {
						Producto_ID = "5";
					}

					sb.append(semana).append(cvsSplitBy).append(Ruta_SAK).append(cvsSplitBy).append(Canal_ID)
							.append(cvsSplitBy).append(Agencia_ID).append(cvsSplitBy).append(Cliente_ID)
							.append(cvsSplitBy).append(Producto_ID).append(cvsSplitBy).append(Demanda_uni_equil)
							.append("\r\n");

					FileWriter writer = new FileWriter("newTrainFiles/" + oldFile.getName().trim(), true);
					writer.append(sb.toString());
					writer.flush();
					writer.close();

				}

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

		System.out.println("Train complete!!");

		/*for (String file : testFileArray)

		{

			System.out.println("file: " + file);
			String csvFile = file;
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			File oldFile = new File(file);
			File newTrainFile = new File("finalTest/new" + oldFile.getName().trim());
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

					if (semanaMap.containsKey(semana)) {
						semana = semanaMap.get(semana);
					} else {
						semana = "5";
					}

					if (routeMap.containsKey(Ruta_SAK)) {
						Ruta_SAK = routeMap.get(Ruta_SAK);
					} else {
						Ruta_SAK = "5";
					}

					if (canalMap.containsKey(Canal_ID)) {
						Canal_ID = canalMap.get(Canal_ID);
					} else {
						Canal_ID = "5";
					}

					if (agencyMap.containsKey(Agencia_ID)) {
						Agencia_ID = agencyMap.get(Agencia_ID);
					} else {
						Agencia_ID = "5";
					}

					if (clientMap.containsKey(Cliente_ID)) {
						Cliente_ID = clientMap.get(Cliente_ID);
					} else {
						Cliente_ID = "5";
					}
					if (prodMap.containsKey(Producto_ID)) {
						Producto_ID = prodMap.get(Producto_ID);
					} else {
						Producto_ID = "5";
					}

					sb.append(semana).append(cvsSplitBy).append(Ruta_SAK).append(cvsSplitBy).append(Canal_ID)
							.append(cvsSplitBy).append(Agencia_ID).append(cvsSplitBy).append(Cliente_ID)
							.append(cvsSplitBy).append(Producto_ID).append("\r\n");

					FileWriter writer = new FileWriter("finalTest/new" + oldFile.getName().trim(), true);
					writer.append(sb.toString());
					writer.flush();
					writer.close();

				}

				
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

		}*/
		//System.out.println("Test Complete");
	}

	private static HashMap<String, String> getAssociatedMap(String[] fileArray) {
		// TODO Auto-generated method stub
		HashMap<String, String> tempMap = new HashMap<String, String>();

		for (String file : fileArray) {

			String csvFile = file;
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {

					// use comma as separator
					String[] lineSplit = line.split(cvsSplitBy);
					tempMap.put(lineSplit[0].trim(), lineSplit[1].trim());

				}

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

		return tempMap;
	}

	private static HashMap<String, String> getSemanaMap() {
		// TODO Auto-generated method stub
		HashMap<String, String> semanaMap = new HashMap<String, String>();
		semanaMap.put("6", "7");
		semanaMap.put("10", "7");
		semanaMap.put("3", "7");
		semanaMap.put("7", "7");
		semanaMap.put("11", "7");
		semanaMap.put("4", "7");
		semanaMap.put("8", "7");
		semanaMap.put("5", "7");
		semanaMap.put("9", "7");

		return semanaMap;
	}

}
