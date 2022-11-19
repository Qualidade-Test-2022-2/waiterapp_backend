import json
from locust import HttpUser, between, task


class WebsiteUser(HttpUser):
    wait_time = between(5, 15)

    def on_start(self):
        self.client.post(
            "/api/clientes/",
            data=json.dumps({
                "cpf": "16213487760",
                "nome": "yago"
            }),
            headers={
                'Content-type': 'application/json',
                'Accept': 'application/json'
            },
        )

    @task
    def static_css_files(self):
        self.client.get("/styles.1473e1d9a0c7960d.css")

    @task
    def static_js_files(self):
        self.client.get("/runtime.30d3abde38193756.js")
        self.client.get("/polyfills.20a1820151dac3ff.js")
        self.client.get("/main.4f84963720bcbb31.js")
        self.client.get("/786.060bc2b94c417f61.js")
    #
    # @task
    # def api_itens(self):
    #     self.client.get("/api/itens")
    #
    # @task
    # def api_itens(self):
    #     current_date = "2022-11-15T23:18:01.234Z"
    #     self.client.post("/api/itens", {
    #         "cliente": {
    #             "id": 3,
    #             "dataCriacao": "2022-11-15T23:21:37.598Z"
    #         },
    #         "items": [
    #             {
    #                 "item": {
    #                     "id": 3,
    #                     "nome": "Frango com quiabo",
    #                     "descricao": "A clássica receita com todos aqueles detalhes que fazem a "
    #                                  "diferença. As coxas e sobrecoxas são braseadas, cozinham com "
    #                                  "pouco líquido, para que a carne fique úmida e a pele dourada.",
    #                     "dataCriacao": current_date,
    #                     "preco": 59.99,
    #                     "ingredientes": [
    #                         {
    #                             "id": 2,
    #                             "nome": "4 coxas de frango com pele e osso",
    #                             "descricao": "Sem descrição",
    #                             "dataCriacao": current_date,
    #                             "caloria": 500
    #                         },
    #                         {
    #                             "id": 1,
    #                             "nome": "quiabo (cerca de 20 unidades)",
    #                             "descricao": "Sem descrição",
    #                             "dataCriacao": current_date,
    #                             "caloria": 200
    #                         }
    #                     ],
    #                     "caloriaTotal": 700
    #                 },
    #                 "quantidade": 1
    #             }
    #         ]
    #     })
