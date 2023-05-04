import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

public class SparkHome {
    public static void main(String[] args) {
        List<Double> inputData = new ArrayList<>();
        inputData.add(10.20);
        inputData.add(56.20);
        inputData.add(156.20);
        inputData.add(190.20);

        SparkConf conf = new SparkConf().setAppName("SparkLearning").setMaster("local[*]");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        JavaRDD<Double> javaRDD = sparkContext.parallelize(inputData);

        sparkContext.close();
    }
}
