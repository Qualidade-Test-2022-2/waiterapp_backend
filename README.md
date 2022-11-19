# waiterapp
Criação de uma api de agendamento de pedidos para um restaurante.

## Trabalho desenvolvido na matéria TCC00340 - Desenvolvimento de Aplicações Corporativas - 2022/1
Documentação: https://trabalho-dev-corp.herokuapp.com/swagger-ui.html


## How to run tests

### Locust load tests
The load tests for this project are present inside the `locust` folder, to execute them you must have `docker` and `docker-compose` installed on your local machine.

```
make run-load-tests # docker-compose -f ./locust/docker-compose-load-tests.yml up
```

The command above should open the locust web interface on the following urk: http://localhost:8089