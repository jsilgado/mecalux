# Mecalux - Warehouses and Racks (Query Filter JPA)

## :hammer_and_wrench: Query Filter JPA

> The QueryFilterJPA Library adds the possibility of creating custom filters with RHS Colon and LHS Brackets with Spring JPA easily. This library is useful for allowing the user to obtain data according to their requirements in an easy way for the programmer. With just a few small configuration classes, users will have the ability to create filters with infinite possibilities.

> The library to be used is published on Github, where full documentation is provided.
https://github.com/acoboh/query-filter-jpa/tree/main

## Installation

You can install the library by adding the following dependency to your project's `pom.xml` file:

#### Spring Boot 2.7.X

```xml
<dependency>
    <groupId>io.github.acoboh</groupId>
    <artifactId>query-filter-jpa</artifactId>
    <version>0.0.4</version>
</dependency>
``` 

#### Spring Boot 3.1.X

```xml
<dependency>
    <groupId>io.github.acoboh</groupId>
    <artifactId>query-filter-jpa-3</artifactId>
    <version>0.0.4</version>
</dependency>
```

This library includes the previous one, it is an extension that adds the possibility to improve the view of the filters from swagger thanks to Openapi.

#### Spring Boot 2.7.X - With OpenApi

```xml
<dependency>
    <groupId>io.github.acoboh</groupId>
    <artifactId>query-filter-jpa-openapi</artifactId>
    <version>0.0.4</version>
</dependency>
``` 

#### Spring Boot 3.1.X - With OpenApi

```xml
<dependency>
    <groupId>io.github.acoboh</groupId>
    <artifactId>query-filter-jpa-openapi-3</artifactId>
    <version>0.0.4</version>
</dependency>
```

## Configuration application.yml

#### Spring Boot 2.7.X

If you are running the Query Filter version for Spring Boot 2.7.X, you need to configure Hibernate to use the extended functions for PostgreSQL Arrays. To do that, you need to add the following property to hibernate:

```properties
hibernate.metadata_builder_contributor="io.github.acoboh.query.filter.jpa.contributor.QfMetadataBuilderContributor"
```

> **_NOTE_**: This is not necessary for Spring Boot 3.1.X version

## Getting Started

#### Entity filtering configuration

The entity must be created as usual. Once created, a filtering configuration class must be specified for each entity.
entity. This class is the one that will determine the behaviour and limitations of filtering, such as controlling in general all the behaviour of
adding or removing elements, selecting columns to sort, etc.

```java
import io.github.acoboh.query.filter.jpa.annotations.QFDefinitionClass;
import io.github.acoboh.query.filter.jpa.annotations.QFElement;

@QFDefinitionClass(Warehouse.class)
public class WarehouseFilterDef {

	@QFElement("client")
	private String client;

	@QFElement("warehouseFamily")
	private String warehouseFamily;

	@QFElement("capacity")
	private Integer capacity;
	
	@QFElement("cca3")
	private String cca3;
}
```


With the class annotation @QFDefinitionClass, you specify the entity model on which you want to apply the filters. Additionally, you have other annotations to indicate each of the available fields for filtering:

- `@QFElement`: Specifies the field name on which filtering operations can be performed. The field name indicates the text to be used on the RHS or LHS of the filter. _(The name used on RHS or LHS can be overridden with the annotation properties.)_
- `@QFDate`: Specifies that the selected field is a date. You can select the format of the text to be parsed. _(The default format is **yyyy-MM-dd'T'HH:mm:ss'Z'** and the timezone is **UTC**)_
- `@QFSortable`: Specifies that the field is only sortable and cannot be filtered. This is useful when you only want to enable sorting by a field but do not want it to be filterable. _(If you already used the `QFElement` annotation, the field will be sortable by default, and you do not need to use this annotation)_
- `@QFBlockParsing`: Specifies that this field is blocked during the stage of parsing from the *String* filter to the *QueryFilter* object. If the field is present in the *String*, an exception will be thrown. This is useful when you need to ensure that some fields cannot be filtered by a user but need to be filtered manually in the code. _(For example, usernames, roles, and other sensitive data.)_

#### Enabling the Query Filter Bean Processor

To enable the **Query Filter** bean processors. You can do that with the following annotation on the main class:

```java
@EnableQueryFilter(basePackageClasses = PostFilterDef.class)
```

> **_NOTE_**: The `basePackageClasses` and `basePackages` are not required by default

#### Configure the repository

The **Query Filter** object is an implementation of the `Specification` interface from JPA. To utilize it, your repository needs to extend the `JpaSpecificationExecutor` interface. This integration enables the Query Filter to work seamlessly with Spring Data JPA and perform dynamic filtering based on the user's input. By combining the Query Filter's custom filtering capabilities with the power of JPA's `Specification` and `JpaSpecificationExecutor`, you can efficiently retrieve data that meets the specified criteria.

```java
public interface PostBlogRepository extends JpaSpecificationExecutor<PostBlog>, JpaRepository<PostBlog, Long> {

}

### Adding the call to the filter
To enable the filter call on endpoints, two steps must be followed:

#### Controller
The first step is to add the @QFParam annotation to the endpoint where you want to add the filter, together with the class that corresponds to the filtering and, optionally, the type of filter (default is RHS Colon).

```java
@Operation(summary = "Search warehouses", description = "Search with paging of warehouses", tags = {
"WarehouseController" })
@GetMapping("/search")
public Page<WarehouseDTO> search(@RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @RequestParam(name = "sort", defaultValue = "client") String sort,
    @RequestParam(required = false)  @QFParam(WarehouseFilterDef.class) QueryFilter<Warehouse> filter) {
  
  PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));
  
  return warehouseService.search(pageRequest, filter);
}	
```

With  LHS Brackets, an additional parameter needs to be added to @QFParam, as RHS Colon is selected by default. The only change would be the following
```java
@QFParam(value = PostFilterDef.class, type = QFParamType.LHS_Brackets)
```

By using the QueryFilter<PostBlogEntity> object, you can automatically create the final query filter object. Once you have the filter,
object, you have the flexibility to perform operations directly on it or use it directly in the repository.

The @QFParam annotation allows you to define a parameter in your controller's method that will be used to receive the filter provided by the client. The Query Filter library will take care of converting the client's filter into the QueryFilter<PostBlogEntity> object, which can then be used to query your data.

Once you have the QueryFilter<PostBlogEntity> object, you have several options for using it:

- Perform operations on the filter object: You can manually operate on the filter object to further customise the filtering behaviour or perform additional actions.filtering behaviour or perform additional actions.
- Use the filter object with the repository: You can pass the filter object directly to the repository's query method that extends JpaSpecificationExecutor. The filter will automatically apply the specified filter criteria to the query.

Both approaches offer a straightforward and efficient way to work with filtering and retrieve data according to user requirements.

#### Service implementation
Finally, the filter object obtained from the controller must be used to make the call to the repository, this call is the one that takes care of applying the filtering specified in the HTTP request.

```java
@Override
public Page<WarehouseDTO> search(PageRequest pageRequest, QueryFilter<Warehouse> filter) {
  
  Page<Warehouse> search = warehouseRepository.findAll(filter, pageRequest);
  Page<WarehouseDTO> searchDTO = search.map(warehouseMapper::warehouseToWarehouseDTO);
  this.setCountryCommonName(searchDTO.getContent());
  
  return searchDTO;
}
```

## How to write *String Filters*

Once you have your service with the **Query Filter** enabled, you can start using **RHS Colon** and **LHS Brackets** standards to filter data effectively.

Following the OpenAPI documentation, you have several options to filter on each field. 

### Allowed operations

- **eq**: Equals
- **ne**: Not equals
- **gt**: Greater than
- **gte**: Greater or equal than
- **lt**: Less than
- **lte**: Less or equal than
- **like**: Like _(for string operations)_
- **starts**: Starts with _(for string operations)_
- **ends**: Ends with _(for string opertions)_
- **in**: IN (in operator of SQL)
- **nin**: Not IN (not it operator of SQL)
- **null**: Is null (is null or is not null. The value must be `false` or `true`)
- **ovlp**: Overlap _(for PostgreSQL arrays)_
- **containedBy**: Contained by _(for PostgreSQL arrays)_

### RHS Colon

The syntax of this standard is the following one:

```log
<field>=<operation>:<value>
```

An example could be:

```log
author=eq:acobo
```

The filter will produce an SQL query like:

```sql
SELECT * FROM posts WHERE author = 'acobo'
```

You can use other operations. Examples:

- `avgNote=gte:5`
- `postType=ne:VIDEO`

### LHS Brackets

The syntax of this standard is the following one:

```log
<field>[<operation>]=<value>
```

An example could be:

```log
author[eq]=acobo
```

The filter will produce an SQL query like:

```sql
SELECT * FROM posts WHERE author = 'acobo'
```

You can use other operations. Examples:

- `avgNote[gte]=5`
- `postType[ne]=VIDEO`

### Sort results

If you want to sort, you can do it with the following syntax:

```log
sort=<direction><field>
```

The direction can be:

- **+**: For ascending direction
- **-**: For descending direction

An example could be:

```log
sort=+author
```

### Concatenate multiple filters

If you want to concatenate multiple filters, you can easily do it with the `&` operator.

And example with **RHS Colon** could be:

```log
author=eq:acobo&avgNote=gte:5&sort=-avgNote
```

The same example with **LHS Brakets**:

```log
author[eq]=acobo&avgNote[gte]=5&sort=-avgNote
```

You can concatenate multiple sort operations. If you do that, the order is important

```log
sort=-avgNote&sort=+likes
```
```sql
order by avgNote desc, likes asc
```

If you change the order:

```log
sort=+likes&sort=-avgNote
```
```sql
order by likes asc, avgNote desc
```

## Filtering from within the service (by code)

By having the QueryFilter<> class, we can also manually modify its behaviour, for example we can add filters that were blocked but from the code we want to be able to filter.
If you don't want to expose the filter from the GET, so that an external user can filter freely. Using as an example the posts that have as `author` Brandom Sanderson:

```java
QueryFilter<PostBlogEntity> qf = queryFilterProcessor.newQueryFilter("author=eq:Brandom Sanderson",
QFParamType.RHS_COLON);
List<PostBlogEntity> blogList = repository.findAll(qf);
```

If you have a filter and you want to add a new one, you only have to modify the filter by code. Assuming that you want to obtain the ones that are published (which in the PostFilterDef filter we have defined as blocked) we could add it from the code:
```java
public List<PostBlogEntity> getPosts(QueryFilter<PostBlogEntity> filter) {
  filter.addNewField("published",QFOperationEnum.EQUAL,"true");
  return repository.findAll(filter);
}
```
The available operators are the listed QFOperationEnum.

## Filtering from within the service (by code)
There are other resources offered by the library, such as custom predicates, which serve for scenarios that do not conform to those indicated, filtering using SpEL (Spring Expression Language), which is a powerful expression language that supports query and filtering. SpEL (Spring Expression Language) is a powerful expression language that supports querying and manipulating an object graph at runtime. manipulation of a graph of objects at runtime.
By default the library performs an AND of all filters, however, there is the possibility to create custom predicates for all kinds of needs. type of needs. If you want to introduce a filter with an OR condition:

Define a new filter (or use the previous one) in which the conditions are added, in this case we are going to filter the posts that have 100000 likes or author Brandom Sanderson.

```java
@QFDefinitionClass(PostBlogEntity.class)
@QFPredicate(name = FilterBlogPredicatesDef.OR_LIKES, expression = "likes OR author")
@QFPredicate(name = FilterBlogPredicatesDef.OR_AUTHORS, expression = "author OR likes", includeMissing =
true)
public class FilterBlogPredicatesDef {

  public static final String OR_LIKES = "or-likes";
  public static final String OR_AUTHORS = "or-authors";

  @QFElement("likes")
  private int likes;
  @QFElement("author")
  private String author;
  @QFElement("postType")
  private String postType;
}
```

```java
@Autowired
private QFProcessor<FilterBlogPredicatesDef, PostBlogEntity> queryFilterProcessor;

@Autowired
private PostBlogRepository repository;

public List<PostBlogEntity> getPostsCustom() {
  QueryFilter<PostBlog> qf = queryFilterProcessor.newQueryFilter("likes=eq:100000&author=eq:Brandom
  Sanderson",
  QFParamType.RHS_COLON);
  //// Post que tengan 5000 like o author Brandom Sanderson
  qf.setPredicate(FilterBlogPredicatesDef.OR_AUTHORS);
  return repository.findAll(qf);
```
The QFElement can have default values so that the user does not have to send them in the requests, for example it can be a fixed filter criteria.
fixed filtering criteria, and the library also offers the possibility to make them SpEL expressions.
