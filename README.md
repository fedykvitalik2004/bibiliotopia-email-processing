## AWS SQS Configuration
> ⚠ **Important:** To enable AWS SQS integration, you **must provide your AWS credentials**.  
> Without them, the application **cannot connect to SQS**.

Add the following to `application.yml`:
```yaml
aws:
  sqs:
    region: YOUR_AWS_REGION
    access-key: YOUR_ACCESS_KEY
    secret-key: YOUR_SECRET_KEY
    queue-url: https://sqs.YOUR_AWS_REGION.amazonaws.com/ACCOUNT_ID/YOUR_QUEUE_NAME