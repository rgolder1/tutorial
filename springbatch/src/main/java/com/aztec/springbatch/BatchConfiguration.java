package com.aztec.springbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Put together a batch job.
 * 
 * Now you put together the actual batch job. Spring Batch provides many utility classes that 
 * reduce the need to write custom code. Instead, you can focus on the business logic.
 * 
 * Detail:
 * 
 * For starters, the @EnableBatchProcessing annotation adds many critical beans that support jobs 
 * and saves you a lot of leg work. This example uses a memory-based database (provided by 
 * @EnableBatchProcessing), meaning that when it's done, the data is gone.
 * 
 * The first chunk of code defines the input, processor, and output. - reader() creates an ItemReader. 
 * It looks for a file called sample-data.csv and parses each line item with enough information to 
 * turn it into a Person. - processor() creates an instance of our PersonItemProcessor you defined 
 * earlier, meant to uppercase the data. - write(DataSource) creates an ItemWriter. This one is 
 * aimed at a JDBC destination and automatically gets a copy of the dataSource created by 
 * @EnableBatchProcessing. It includes the SQL statement needed to insert a single Person driven by 
 * Java bean properties.
 * 
 * The next chunk focuses on the actual job configuration.  The inputUserJob() defines the job and the 
 * step1() defines a single step. Jobs are built from steps, where each step can involve a reader, 
 * a processor, and a writer.  In this job definition, you need an incrementer because jobs use a 
 * database to maintain execution state. You then list each step, of which this job has only one step. 
 * The job ends, and the Java API produces a perfectly configured job.
 *
 * In the step definition, you define how much data to write at a time. In this case, it writes up to 
 * ten records at a time. Next, you configure the reader, processor, and writer using the injected 
 * bits from earlier.
 * 
 * chunk() is prefixed <Person,Person> because it's a generic method. This represents the input and 
 * output types of each "chunk" of processing, and lines up with ItemReader<Person> and 
 * ItemWriter<Person>.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<Person> reader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public ItemWriter<Person> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
            ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    // end::jobstep[]

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
