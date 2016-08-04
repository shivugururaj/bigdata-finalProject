package bdata.b;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.feature.VectorIndexer;
import org.apache.spark.ml.feature.VectorIndexerModel;
import org.apache.spark.ml.regression.RandomForestRegressionModel;
import org.apache.spark.ml.regression.RandomForestRegressor;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class RandomForestRegression {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/*
		 * File file = new File("result.txt"); FileWriter fw = new
		 * FileWriter(file);
		 * 
		 * File predFile = new File("predections.txt"); FileWriter predFw = new
		 * FileWriter(predFile);
		 */
		// StringBuilder sb = new StringBuilder();
		SparkConf conf = new SparkConf().setAppName("RandomForestRegression").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		DataFrame traindata = sqlContext.read().format("libsvm").load("finalTrainFiles/*");
		// DataFrame testdataFrame =
		// sqlContext.read().format("libsvm").load("finalTestFiles/*");
		// Dataset<Row> data =
		// spark.read().format("libsvm").load("data/mllib/sample_libsvm_data.txt");

		// Automatically identify categorical features, and index them.
		// Set maxCategories so features with > 4 distinct values are treated as
		// continuous.
		VectorIndexerModel featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures")
				.setMaxCategories(4).fit(traindata);

		// Split the data into training and test sets (30% held out for testing)
		DataFrame[] splits = traindata.randomSplit(new double[] { 0.8, 0.2 });
		DataFrame train = splits[0];
		DataFrame validate = splits[1];

		// Train a RandomForest model.
		RandomForestRegressor rf = new RandomForestRegressor().setLabelCol("label").setFeaturesCol("indexedFeatures");

		// Chain indexer and forest in a Pipeline
		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { featureIndexer, rf });

		// Train model. This also runs the indexer.
		PipelineModel model = pipeline.fit(train);

		// Make predictions.
		DataFrame predictions = model.transform(validate);

		// Select example rows to display.
		// predictions.select("prediction", "label", "features").show(4000);

		// predictions.select("prediction").write().format("csv").option("header",
		// "false").save("result.csv");

		List<Row> mylist = predictions.select("prediction", "label").collectAsList();
		StringBuilder sb;
		for (Row r : mylist) {
			sb = new StringBuilder();
			String entry = r.toString();
			sb.append(entry.substring(1, entry.length() - 1)).append("\r\n");
			FileWriter writer = new FileWriter("prediction.txt", true);

			writer.append(sb.toString());
			writer.flush();
			writer.close();

		}
		// predictions.select("prediction", "label");

		// sb.append(predictions.select("prediction", "label",
		// "features").toString());

		// Select (prediction, true label) and compute test error
		/*
		 * RegressionEvaluator evaluator = new
		 * RegressionEvaluator().setLabelCol("label").setPredictionCol(
		 * "prediction") .setMetricName("rmse");
		 */
		RegressionEvaluator evaluator = new RegressionEvaluator().setLabelCol("label").setPredictionCol("prediction")
				.setMetricName("rmse");
		double rmse = evaluator.evaluate(predictions);
		System.out.println("Root Mean Squared Error (RMSE) on test data = " + rmse);

		RandomForestRegressionModel rfModel = (RandomForestRegressionModel) (model.stages()[1]);
		/*
		 * fw.write(rfModel.toDebugString()); fw.flush(); fw.close();
		 */
		// System.out.println("Learned regression forest model:\n" +
		// rfModel.toDebugString());

	}

}
