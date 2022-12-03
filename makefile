run-load-tests:
	docker-compose -f locust/docker-compose-load-tests.yml up

run-load-headless-tests:
	docker-compose -f locust/docker-compose-load-headless-tests.yml run locust_master

run-integration-tests:
	docker-compose -f integration/docker-compose-integration-tests.yml run e2e

run-postman-tests:
	docker-compose -f postman/docker-compose-postman-tests.yml run postman

build-frontend:
	cd frontend && ng build
	rm -rf ./src/main/resources/static/*
	mv ./frontend/dist/waiter-app/* ./src/main/static