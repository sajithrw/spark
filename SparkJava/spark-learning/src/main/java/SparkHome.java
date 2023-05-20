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

        /**
         * reduce function applies passed function to all nodes until it has a single value as the result.
         * Later it shuffles multiple nodes and take individual results into one node. Then applies the same function
         * to obtain a single result.
         *
         * Lambda function also accepts typed variables, though it is not necessary here as JavaRDD<Double> defines
         * variable type.
         */
        Double result = javaRDD.reduce(Double::sum);
        System.out.println("Reduced result: " + result);

        sparkContext.close();
    }
}
