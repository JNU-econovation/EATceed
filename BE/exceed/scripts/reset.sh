cd ../resources/gaebaljip-local-develop-environment
docker-compose -f setting.yml down
docker-compose -f setting.yml up -d
sleep 10