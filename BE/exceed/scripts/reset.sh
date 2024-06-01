cd ../resources/gaebaljip-local-environment
docker-compose -f setting.yml down
docker-compose -f setting.yml up -d
sleep 10