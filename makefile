run-load-tests:
	docker-compose -f locust/docker-compose-load-tests.yml up

run-integration-tests:
	docker-compose -f integration/docker-compose-integration-tests.yml run e2e