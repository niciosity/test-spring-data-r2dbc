package com.example.demo

import com.example.demo.model.Customer
import com.example.demo.repository.CustomerRepository
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
class DemoApplication {
	@Bean
	fun demo(customerRepository: CustomerRepository): CommandLineRunner {
		return CommandLineRunner { args ->
			val customer = Customer(name = "Name Longer than 10 Characters")
			customerRepository.save(customer).doOnError {
				println("Got an error >> ${it.message}")
			}.map { saved ->
				println("Saved >> $saved")
			}.block()
		}
	}
}

@EnableR2dbcRepositories
@Configuration
class ReactiveMsSqlConfig : AbstractR2dbcConfiguration() {
	@Bean
	override fun connectionFactory(): ConnectionFactory {
		return ConnectionFactories.get("r2dbc:sqlserver://sa:sa123@localhost:1433/selfinquiry")
		// return ConnectionFactories.get("r2dbc:mysql://root:root@127.0.0.1:3306/selfinquiry")
	}
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
