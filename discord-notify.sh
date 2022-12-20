#!/bin/bash

discord_url="https://discord.com/api/webhooks/1052064972210978859/HeAxsPsmMz48s-W-ILTCdnRVYPkuNzTsnMEonOIWbw1HrDZNP0hBHL7EOP78VGWwJwDH"

generate_post_data() {
  cat <<EOF
{
  "content": "Hello! World!"
}
EOF
}

curl -H "Content-Type: application/json" -X POST -d "$(generate_post_data)" $discord_url
