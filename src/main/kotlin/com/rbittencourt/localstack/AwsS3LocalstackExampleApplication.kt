package com.rbittencourt.localstack

import com.rbittencourt.localstack.processor.FileProcessor
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class AwsS3LocalstackExampleApplication(
	private val fileProcessor: FileProcessor
) {

	@PostConstruct
	fun start() {
		runBlocking {
			fileProcessor.execute()
		}
	}

}

fun main(args: Array<String>) {
	runApplication<AwsS3LocalstackExampleApplication>(*args)
}
