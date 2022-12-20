#!/bin/bash

discord_url="https://discord.com/api/webhooks/1052064972210978859/HeAxsPsmMz48s-W-ILTCdnRVYPkuNzTsnMEonOIWbw1HrDZNP0hBHL7EOP78VGWwJwDH"

while read oldrev newrev ref
do
	if [[ $ref =~ .*/master$ ]];
	then
		git log --pretty=format:'%h - %an, %ar : %s' $oldrev..$newrev > git.log
		curl -H "Content-Type: application/json" -X POST -d "{\"content\":\"'catgit.log'\"}" $discord_url
	fi
done
