# AWS S3 Localstack Example

## Using AWS S3 with Localstack and aws-cli

```
// Run Localstack with docker-compose
docker-compose up -d

// Create a bucket
aws --endpoint-url=http://localhost:4566 s3 mb s3://to-process

// Create another bucket
aws --endpoint-url=http://localhost:4566 s3 mb s3://processed

// Upload test file
aws --endpoint-url=http://localhost:4566 s3 cp text.txt s3://to-process

// List all buckets
aws --endpoint-url=http://localhost:4566 s3 ls

// List all files from a bucket
aws --endpoint-url=http://localhost:4566 s3 ls s3://to-process

// Read file content
aws --endpoint-url=http://localhost:4566 s3 cp --quiet s3://to-process/text.txt /dev/stdout

// Move file from a bucket to another
aws --endpoint-url=http://localhost:4566 s3 mv s3://to-process/text.txt s3://processed

// Copy a file to another bucket
aws --endpoint-url=http://localhost:4566 s3 cp s3://processed/text.txt s3://to-process

// Delete file from a bucket
aws --endpoint-url=http://localhost:4566 s3 rm s3://processed/text.txt 

// Delete a bucket
aws --endpoint-url=http://localhost:4566 s3 rb s3://processed 
```