## Spark SQL cheat sheet for command line operations
### all the examples use SCALA language and spark-shell to perform command.

Need to import sqlContext in to spark session before using<br>
`import spark.sqlContext`

Import implicits from sqlContext<br>
`import sqlContext.implicits._`

Define your class<br>
`case class Company(name: String, employeeCount: Int16Array, isPublic: Boolean)`

Create list of companies<br>
```scala
val companies = List(
    Company("ABC Corp", 25, false),
    Company("XYZ Inc", 5000, true),
    Company("Sparky", 400, true),
    Company("Tech Retail", 1000, false),
    Company("Some Place", 75, false)
)
```

Convert list to dataframe<br>
`val companiesDF = companies.toDF`

Alternative way for create dataframe<br>
`val companiesDF = sqlContext.createDataFrame(companies)`

Show data frame<br>
`companiesDF.show`

Loading data from a source. JSON in this case<br>
`val companiesJsonDF = sqlContext.read.json("{file_path_of_json_file}")`

Alternative way to load data from a source<br>
`val companiesJsonDF = sqlContext.read.format("json").load("{file_path_of_json_file}")`

Get dataframe schema<br>
`companiesJsonDF.printSchema`

Casting data types in schema, because data type for employeeCount doesn't match<br>
`val companiesJsonIntDF = companiesJsonDF.select($"name", $"employeeCount".cast("int").as("employeeCount"), $"isPublic")`

Union dataframes<br>
`val allCompaniesDF = companiesDF.union(companiesJsonIntDF)`

Perform queries and access column values<br>
`allCompaniesDF.groupBy(allCompaniesDF.col("isPublic")).agg(avg("employeeCount")).show`

Filtering data<br>
`allCompaniesDF.where($"employeeCount" > 1000).show`

Alternative way to filter data<br>
`allCompaniesDF.where(allCompaniesDF.col("employeeCount").gt(1000)).show`

Write data frame<br>
`allCompaniesDF.write.json("{file_path_of_json_file}")`

Import sql row<br>
`import org.apache.spark.sql.Row`

Dataframe to company name -> dataset[string]<br>
`allCompaniesDF.map(company => company(0).asInstanceOf[String])`

Print out all<br>
`.foreach(x => println(x))`

Create a temporary view<br>
`allCompaniesDF.createOrReplaceTempView("Companies")`

Perfrom sql queries<br>
`sql("SELECT * FROM Companies")`

Show result<br>
`.show`

Sql cache table (eagerly)<br>
`sql("CACHE TABLE Companies")`

Sql lazy cache table__
`sql("CACHE LAZY TABLE Companies")`

