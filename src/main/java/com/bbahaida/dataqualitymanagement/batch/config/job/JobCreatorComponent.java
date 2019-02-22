package com.bbahaida.dataqualitymanagement.batch.config.job;

import com.bbahaida.dataqualitymanagement.batch.items.QualityCheckerProcessor;
import com.bbahaida.dataqualitymanagement.entities.AppDBItem;
import com.bbahaida.dataqualitymanagement.repositories.AppDBItemRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class JobCreatorComponent {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private AppDBItemRepository appDBItemRepository;


    public Job createJob(final String jobName, final Step step, final JobExecutionListener listener){
        return jobBuilderFactory
                .get(jobName)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }


    public Step etlTable(DataSource dataSource, final String tableName) {
        return stepBuilderFactory
                .get("etlTable")
                .<Map<String, Object>, AppDBItem>chunk(100)
                .reader(pagingItemReader(dataSource, tableName))
                .processor(qualityCheckerProcessor())
                .writer(dbWriter())
                .build();
    }

    private JdbcPagingItemReader<Map<String, Object>> pagingItemReader(DataSource dataSource, String tableName) {
        try {
            String columnName = dataSource.getConnection().getMetaData().getColumns(null, null, tableName, null).getString(1);

            JdbcPagingItemReader<Map<String, Object>> itemReader = new JdbcPagingItemReader<>();
            itemReader.setDataSource(dataSource);
            itemReader.setQueryProvider(queryProvider(dataSource, tableName, columnName).getObject());
            itemReader.setPageSize(1000);

            itemReader.setRowMapper(JobCreatorComponent::rowMapper);

            return itemReader;
        }catch (Exception e){
            throw new RuntimeException("ffff");
        }

    }

    private SqlPagingQueryProviderFactoryBean queryProvider(DataSource dataSource, String tableName, String columnName){
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("SELECT *");
        queryProvider.setFromClause("FROM "+ tableName);

        Map<String, Order> sortKeys = new LinkedHashMap<>();
        sortKeys.put(columnName, Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider;
    }

    private static Map<String, Object> rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        Map<String, Object> map = new HashMap<>();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            map.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
        }

        return map;
    }


    @Bean
    public ItemProcessor<Map<String, Object>, AppDBItem> qualityCheckerProcessor(){
        return new QualityCheckerProcessor();
    }

    @Bean
    public RepositoryItemWriter<AppDBItem> dbWriter(){
        RepositoryItemWriter<AppDBItem> writer = new RepositoryItemWriter<>();
        writer.setRepository(appDBItemRepository);
        writer.setMethodName("save");
        return writer;
    }


}
