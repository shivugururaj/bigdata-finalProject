package bdata.b;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RMSLE {

	public static void main(String[] args) throws IOException {
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader("prediction.txt"));
		Double sum = 0.0;
		Integer n = 0;
		Double RMSLE;
		while ((line = br.readLine()) != null) {
			if (line == null || line.length() <= 0) {
				break;
			} else {
				++n;
				String[] lineSplit = line.split(",");
				Double predictionVal = Double.parseDouble(lineSplit[0]);
				Double actualVal = Double.parseDouble(lineSplit[1]);
				predictionVal = predictionVal + 1;
				actualVal = actualVal + 1;
				Double subVal = Math.log(predictionVal) - Math.log(actualVal);
				Double sqValue = subVal * subVal;
				sum = sum + sqValue;

			}
		}
		RMSLE = sum / n;
		RMSLE = Math.sqrt(RMSLE);
		System.out.println("RMSLE= " + RMSLE);
	}

}
