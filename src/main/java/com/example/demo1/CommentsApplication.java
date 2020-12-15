package com.example.demo1;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(CommentsApplication.MySqlConfiguration.class)
public class CommentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentsApplication.class, args);		
	}
	
	@Bean
	public DataSource dataSource(MySqlConfiguration mySqlConfig) {
		return DataSourceBuilder
				.create()
				.driverClassName(mySqlConfig.getDriverClassName())
				.password(mySqlConfig.getPassword())
				.username(mySqlConfig.getUsername())
				.url(mySqlConfig.getUrl())
				.build();
	}
		
	@ConfigurationProperties(prefix = "mysql")
	public static class MySqlConfiguration {
		private String driverClassName;
		private String password;
		private String username;
		private String url;

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

}
