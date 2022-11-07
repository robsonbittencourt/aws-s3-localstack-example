package com.rbittencourt.localstack.aws

import aws.sdk.kotlin.runtime.endpoint.AwsEndpoint
import aws.sdk.kotlin.runtime.endpoint.AwsEndpointResolver
import aws.sdk.kotlin.runtime.endpoint.CredentialScope
import aws.sdk.kotlin.services.s3.S3Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3ClientConfig {

    @Bean
    fun s3Client() = S3Client {
        region = "us-east-1"
        endpointResolver = AwsEndpointResolver { _, _ ->
            AwsEndpoint("http://localhost:4566", CredentialScope(region = "us-east-1"))
        }
    }

}