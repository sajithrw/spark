## Spark SQL cheat sheet for command line operations
### all the examples use SCALA language and spark-shell to perform command.

Need to import sqlContext in to spark session before using
`import spark.sqlContext`

Import implicits from sqlContext
`import sqlContext.implicits._`

Define your class
`case class Company(name: String, employeeCount: Int16Array, isPublic: Boolean)`

Create list of companies 
```scala
val companies = List(
    Company("ABC Corp", 25, false),
    Company("XYZ Inc", 5000, true),
    Company("Sparky", 400, true),
    Company("Tech Retail", 1000, false),
    Company("Some Place", 75, false)
)
```

Convert list to dataframe
`val companiesDF = companies.toDF`

Alternative way for create dataframe
`val companiesDF = sqlContext.createDataFrame(companies)`

Show data frame
`companiesDF.show`

Loading data from a source. JSON in this case
`val companiesJsonDF = sqlContext.read.json("{file_path_of_json_file}")`

Alternative way to load data from a source
`val companiesJsonDF = sqlContext.read.format("json").load("{file_path_of_json_file}")`

Get dataframe schema
`companiesJsonDF.printSchema`

Casting data types in schema, because data type for employeeCount doesn't match
`val companiesJsonIntDF = companiesJsonDF.select($"name", $"employeeCount".cast("int").as("employeeCount"), $"isPublic")`

Union dataframes
`val allCompaniesDF = companiesDF.union(companiesJsonIntDF)`

Perform queries and access column values
`allCompaniesDF.groupBy(allCompaniesDF.col("isPublic")).agg(avg("employeeCount")).show`

Filtering data
`allCompaniesDF.where($"employeeCount" > 1000).show`

Alternative way to filter data
`allCompaniesDF.where(allCompaniesDF.col("employeeCount").gt(1000)).show`

Write data frame
`allCompaniesDF.write.json("{file_path_of_json_file}")`

Import sql row
`import org.apache.spark.sql.Row`

Dataframe to company name -> dataset[string]
`allCompaniesDF.map(company => company(0).asInstanceOf[String])`

Print out all
`.foreach(x => println(x))`

Create a temporary view
`allCompaniesDF.createOrReplaceTempView("Companies")`

Perfrom sql queries
`sql("SELECT * FROM Companies")`

Show result
`.show`

Sql cache table (eagerly)
`sql("CACHE TABLE Companies")`

Sql lazy cache table
`sql("CACHE LAZY TABLE Companies")`

