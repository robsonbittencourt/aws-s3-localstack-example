package com.rbittencourt.localstack.processor

import com.rbittencourt.localstack.aws.S3Service
import org.springframework.stereotype.Component

@Component
class FileProcessor(
    private val s3Service: S3Service
) {
    suspend fun execute() {
        val toProcessBucket = "to-process"
        val processedBucket = "processed"
        val fileOne = "text1.txt"
        val fileTwo = "dataset.csv"

        s3Service.uploadFile(fileOne, toProcessBucket)
        s3Service.uploadFile(fileTwo, toProcessBucket)

        val filesToProcess = s3Service.listBucketObjects(toProcessBucket)
        println("Files in the bucket: $filesToProcess")

        filesToProcess.forEach { fileName ->
            process(fileName, toProcessBucket, processedBucket)
        }
    }

    private suspend fun process(fileName: String, toProcessBucket: String, processedBucket: String) {
        println("Processing file $fileName")

        s3Service.getObjectBytes(toProcessBucket, fileName).forEachLine {
            println(it)
        }

        s3Service.moveBucketObject(toProcessBucket, fileName, processedBucket)

        println("End of processing file $fileName")
    }
}