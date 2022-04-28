package tn.uae.nawanera.spring.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import tn.uae.nawanera.spring.batch.RoleProcessor;
import tn.uae.nawanera.spring.batch.RoleWriter;
import tn.uae.nawanera.spring.entities.Role;
import tn.uae.nawanera.spring.entities.RoleType;



@Configuration

 
@EnableBatchProcessing
@EnableScheduling
public class RoleBatchConfig {


	private static final String FILE_NAME = "roles.csv";
	private static final String JOB_NAME = "listRolesJob";
	private static final String STEP_NAME = "processingStep";
	private static final String READER_NAME = "roleItemReader";

	
	private String names = "id,role_type" ;
	private String delimiter = ",";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	
	

	@Bean
	public Job listRolesJob(Step step1) {
		return jobBuilderFactory.get(JOB_NAME).start(step1).build();
	}

	@Bean
	public Step roleStep() {
		return stepBuilderFactory.get(STEP_NAME).<Role, Role>chunk(5).reader(roleItemReader())
				.processor(roleItemProcessor()).writer(roleItemWriter()).build();
	}
	

	@Bean
	public ItemReader<Role> roleItemReader() {
		FlatFileItemReader<Role> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource(FILE_NAME));
		reader.setName(READER_NAME);
		reader.setLinesToSkip(1);
		reader.setLineMapper(roleLineMapper());
		return reader;

	}

	

	@Bean
	public LineMapper<Role> roleLineMapper() {

		final DefaultLineMapper<Role> defaultLineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(delimiter);
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(names.split(delimiter));
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSet -> {
			Role data = new Role();
			data.setId(fieldSet.readInt(0));
			data.setRoleType(RoleType.valueOf(fieldSet.readString(1)));
			 
			 
			return data;
		});
		return defaultLineMapper;
	}

	
	@Bean
	public ItemProcessor<Role, Role> roleItemProcessor() {
		return new RoleProcessor();
	}

	
	
	@Bean
	public ItemWriter<Role> roleItemWriter() {
		return new RoleWriter();
	}
}
