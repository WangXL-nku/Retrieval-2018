# Elastic-Search Spring-boot 配置步骤

[spring-data-elatsicsesarch官方文档](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.repositories)

[ElasticSearch官方文档](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high.html)

[TOC]

## Elastic Search的安装与配置

1. 在ElasticSearch官网下载ElasticSearch压缩包
2. 将压缩包解压到指定目录
3. 运行`bin/elastic-search.bat`启动elasticsearch
4. 浏览器中输入`http://localhost:9200`看到elasticsearch相关信息
5. 完成

## 在Spring-Boot中添加ElasticSearch

1. 在`pom.xml`中添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency>
    ```

2. 在`application.properties`中添加配置项
    ```yaml
    spring.data.elasticsearch.repositories.enabled=true
    spring.data.elasticsearch.cluster-nodes=127.0.0.1:9300
    spring.data.elasticsearch.cluster-name=elasticsearch
    ```

    nodes的端口是**9300**

3. 创建Model与Dao层

    - Model层

        ```java
        @Document(indexName = "literature_news", type = "news")
        public class NkLiteratureNews {

            @Field(analyzer = "index_ansj", searchAnalyzer = "query_ansj", type = FieldType.Text)
            private String title;

            @Field(type = FieldType.Date)
            private Date releaseTime;

            @Field(analyzer = "index_ansj", searchAnalyzer = "query_ansj", type = FieldType.Text)
            private String content;

            @Id
            @Field(index = false, type = FieldType.Keyword)
            private String url;

            @Field(type = FieldType.Keyword)
            private String tag;
        }
        ```

        实体类需要加上`@Document`注解指定索引名（数据库名）`indexName`，类型名（表名）`type`

        每一个实体类**可以**加上`@Field`注解用来指定这一个域（列）需要什么索引分词工具、搜索分词工具、类型等
        但是必须要有一个来自`spring-data`的`@Id`注解来表明主键，来用于实现JPA接口

    - Dao层

        ```java
        @Component
        public interface NkLiteratureNewsEsDao extends ElasticsearchRepository<NkLiteratureNews, String> {
        }
        ```

        这样就能够在其他地方注入这个接口来使用简单的save、search、等方法

## 使用ElasticSearch

- save

    new 一个Model对象 save

    好像不能够像数据库一样有自增Id，如果需要，需要在Java中自己做一个Id生成器

- search

    各种各样的QueryBuilder按需使用
