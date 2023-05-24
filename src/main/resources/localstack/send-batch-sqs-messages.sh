#!/usr/bin/env

set -euo pipefail

AWS_REGION=us-east-1
QUEUE_URL="http://localhost:4566/000000000000/sqs-cadastro-corte-cabelo"
MESSAGE_BODY_PATH=file://message.json
NUMBER_OF_MESSAGES=10

send_message() {
    echo "sending ${NUMBER_OF_MESSAGES} sqs message"
    for i in $(seq ${NUMBER_OF_MESSAGES});
    do
    awslocal sqs send-message --queue-url ${QUEUE_URL}  --message-body ${MESSAGE_BODY_PATH} --region ${AWS_REGION}
    done
    echo "messages sent"
    echo "==================="
}

send_message
