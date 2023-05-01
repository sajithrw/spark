## Spark SQL cheat sheet for command line operations
### all the examples use SCALA language and spark-shell to perform command.

Need to import sqlContext in to spark session before using__
`import spark.sqlContext`

Import implicits from sqlContext__
`import sqlContext.implicits._`

Define your class__
`case class Company(name: String, employeeCount: Int16Array, isPublic: Boolean)`

Create list of companies__
```scala
val companies = List(
    Company("ABC Corp", 25, false),
    Company("XYZ Inc", 5000, true),
    Company("Sparky", 400, true),
    Company("Tech Retail", 1000, false),
    Company("Some Place", 75, false)
)
```

Convert list to dataframe__
`val companiesDF = companies.toDF`

Alternative way for create dataframe__
`val companiesDF = sqlContext.createDataFrame(companies)`

Show data frame__
`companiesDF.show`

Loading data from a source. JSON in this case__
`val companiesJsonDF = sqlContext.read.json("{file_path_of_json_file}")`

Alternative way to load data from a source__
`val companiesJsonDF = sqlContext.read.format("json").load("{file_path_of_json_file}")`

Get dataframe schema__
`companiesJsonDF.printSchema`

Casting data types in schema, because data type for employeeCount doesn't match__
`val companiesJsonIntDF = companiesJsonDF.select($"name", $"employeeCount".cast("int").as("employeeCount"), $"isPublic")`

Union dataframes__
`val allCompaniesDF = companiesDF.union(companiesJsonIntDF)`

Perform queries and access column values__
`allCompaniesDF.groupBy(allCompaniesDF.col("isPublic")).agg(avg("employeeCount")).show`

Filtering data__
`allCompaniesDF.where($"employeeCount" > 1000).show`

Alternative way to filter data__
`allCompaniesDF.where(allCompaniesDF.col("employeeCount").gt(1000)).show`

Write data frame__
`allCompaniesDF.write.json("{file_path_of_json_file}")`

Import sql row__
`import org.apache.spark.sql.Row`

Dataframe to company name -> dataset[string]__
`allCompaniesDF.map(company => company(0).asInstanceOf[String])`

Print out all__
`.foreach(x => println(x))`

Create a temporary view__
`allCompaniesDF.createOrReplaceTempView("Companies")`

Perfrom sql queries__
`sql("SELECT * FROM Companies")`

Show result__
`.show`

Sql cache table (eagerly)__
`sql("CACHE TABLE Companies")`

Sql lazy cache table__
`sql("CACHE LAZY TABLE Companies")`

