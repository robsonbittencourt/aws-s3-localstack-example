package com.rbittencourt.localstack.aws

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.*
import aws.smithy.kotlin.runtime.content.asByteStream
import aws.smithy.kotlin.runtime.content.toByteArray
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets.UTF_8

@Service
class S3Service(
    private val s3Client: S3Client
) {

    suspend fun uploadFile(fileName: String, bucketName: String) {
        val request = PutObjectRequest {
            bucket = bucketName
            key = fileName
            body = File(ClassLoader.getSystemResource(fileName).file).asByteStream()
        }

        s3Client.putObject(request)
    }

    suspend fun listBucketObjects(bucketName: String): List<String> {
        val request = ListObjectsRequest {
            bucket = bucketName
        }

        val response = s3Client.listObjects(request)
        return response.contents?.mapNotNull { it.key } ?: emptyList()
    }

    suspend fun getObjectBytes(bucketName: String, keyName: String): BufferedReader {
        val request = GetObjectRequest {
            key = keyName
            bucket = bucketName
        }

        return s3Client.getObject(request) { resp ->
            ByteArrayInputStream(resp.body?.toByteArray()).bufferedReader()
        }
    }

    suspend fun moveBucketObject(fromBucket: String, objectKey: String, toBucket: String) {
        copyBucketObject(fromBucket, objectKey, toBucket)
        deleteBucketObjects(fromBucket, objectKey)
    }

    suspend fun copyBucketObject(fromBucket: String, objectKey: String, toBucket: String) {
        var encodedUrl = ""
        try {
            encodedUrl = URLEncoder.encode("$fromBucket/$objectKey", UTF_8.toString())
        } catch (e: UnsupportedEncodingException) {
            println("URL could not be encoded: " + e.message)
        }

        val request = CopyObjectRequest {
            copySource = encodedUrl
            bucket = toBucket
            key = objectKey
        }

        s3Client.copyObject(request)
    }


    suspend fun deleteBucketObjects(bucketName: String, objectName: String) {
        val objectId = ObjectIdentifier {
            key = objectName
        }

        val deleteObjects = Delete {
            objects = listOf(objectId)
        }

        val request = DeleteObjectsRequest {
            bucket = bucketName
            delete = deleteObjects
        }

        s3Client.deleteObjects(request)
    }
}