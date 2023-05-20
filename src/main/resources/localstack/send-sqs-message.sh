#!/usr/bin/env

set -euo pipefail

AWS_REGION=us-east-1
QUEUE_URL="http://localhost:4566/000000000000/sqs-cadastro-corte-cabelo"
MESSAGE_BODY_PATH=file://message.json

send_message() {
    echo "sending sqs message"
    awslocal sqs send-message --queue-url ${QUEUE_URL}  --message-body ${MESSAGE_BODY_PATH} --region ${AWS_REGION}
    echo "sqs message sent"
    echo "==================="
}

send_message
