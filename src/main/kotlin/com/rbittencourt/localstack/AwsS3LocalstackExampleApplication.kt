package com.rbittencourt.localstack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AwsS3LocalstackExampleApplication

fun main(args: Array<String>) {
	runApplication<AwsS3LocalstackExampleApplication>(*args)
}
