// Funcionalidades de Garçom
describe("Funcionalidade de Login (Garçom)", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
    cy.contains("Faça login");
  })
  it("Deve ser possível para o Garçom realizar o login", () => {
    cy.get("div[class='login__type-selector__item']").click();
    cy.get("input[type='text']").type("11111111111");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
  })
})

describe("Funcionalidade de seleção de itens (Garçom)", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
    cy.get("div[class='login__type-selector__item']").click();
    cy.get("input[type='text']").type("11111111111");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
  })
  it('NÃO deve ser possível para o garçom selecionar itens', () => {
    cy.get('html > body > div > div > div > div > div > div:nth-of-type(1)')
        .should('have.css', 'cursor','not-allowed')
  })
  it('Deve ser possível para o Garçom realizar o logout', () => {
    cy.get("div[class$='logout-button']").click();
    cy.contains("Faça login");
  })
})

describe("Funcionalidade de cadastro (Garçom)", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
    cy.contains("Faça login");
  })
  it('Deve ser possível logar com um garçom já existente', () => {
    cy.get("div[id$='waiter']").click();
    cy.get("input[type='text']").type("11111111111");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
  })
  it('Deve ser possível para um garçom já existente cadastrar um novo colega de trabalho', () => {
    cy.get("div[class='header__menu'] a").click();
    cy.contains("Cadastre um novo garçom");
    cy.get("html > body > div > div > div > form > fieldset:nth-of-type(1) > input")
        .type("João Paulo");
    cy.get("html > body > div > div > div > form > fieldset:nth-of-type(2) > input")
        .type("yago@gmail.com");
    cy.get("html > body > div > div > div > form > fieldset:nth-of-type(3) > input")
        .type("44045252061");
    cy.get("input[type='password']")
        .type("87654321")
    cy.get("input[type='submit']").click();
  })

  it('Deve ser possível para o garçom atual realizar o logout', () => {
    cy.get("div[class$='logout-button']").click();
  })

  it('Deve ser possível logar com o usuário novo', () => {
    cy.get("div[id$='waiter']").click();
    cy.get("input[type='text']").type("44045252061");
    cy.get("input[type='password']").type("87654321");
    cy.get("input[type='submit']").click();
    cy.contains("João Paulo")
  })

  it("NÃO deve ser possível para o novo garçom selecionar pedidos", () => {
    cy.get('html > body > div > div > div > div > div > div:nth-of-type(1)')
        .should('have.css', 'cursor','not-allowed');
  })

  it("Deve ser possível para o novo garçom realizar o logout", () => {
    cy.get("div[class$='logout-button']").click();
    cy.contains("Faça login");
  })
});

// Funcionalidades de Cliente
describe("Funcionalidade de Login (Cliente)", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
  })
  it("Deve ser possível para o Cliente realizar o login", () => {
    cy.get("div[id$='login']").click();
    cy.get("input[type='text']").type("51756949018");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
  })
  it("Deve ser possível para o Cliente ver os detalhes dos itens", () => {
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(1)").click();
    cy.contains("Coca Cola");
    cy.contains("Quantidade");
    cy.contains("R$ 15,00");
    cy.get("p[class$='close-button']").click();
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(1)").click();
    cy.get("input[class^='product-modal']").clear().type("5")
    cy.get("input[class*='primary']").click();
    cy.get("p[class$='close-button']").click();
    cy.contains("R$ 75,00");
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(2)").click();
    cy.contains("Guarana Antartica");
    cy.contains("Quantidade")
    cy.get("input[class^='product-modal']").clear().type("3")
    cy.get("input[class*='primary']").click();
    cy.get("p[class$='close-button']").click();
    cy.contains("R$ 120,00");
    cy.get("select[class$='selector']").select("2");
    cy.contains("Bobó de camarão");
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(2)").click()
    cy.contains("Quantidade:");
    cy.contains("R$ 39,99");
    cy.get("input[class^='product-modal']").clear().type("3");
    cy.get("input[class*='primary']").click();
    cy.contains("R$ 239,97");
    cy.get("p[class$='close-button']").click();
  })
  it('Deve ser possível para o Cliente realizar o logout', () => {
    cy.get("div[class$='logout-button']").click();
    cy.contains("Faça login");
  })
})

describe("Funcionalidade de cadastro (Cliente)", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
  })
  it("Deve ser possível para o NOVO Cliente realizar o cadastro", () => {
    cy.get("html > body > div > div > div > form > fieldset:nth-of-type(4) > a").click();
    cy.get("html > body > div > div > div > form > fieldset:nth-of-type(1) > input").type("José Paulo");
    cy.get("html > body > div > div > div > form > fieldset:nth-of-type(2) > input").type("yag@gmail.com");
    cy.get("html > body > div > div > div > form > fieldset:nth-of-type(3) > input").type("64617975055");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
    cy.contains("Faça login");
  })
  it("Deve ser possível para o NOVO Cliente realizar o login", () => {
    cy.get("div[id$='login']").click();
    cy.get("input[type='text']").type("64617975055");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
    cy.contains("Menus");
  })
  it("Deve ser possível para o NOVO Cliente ver os detalhes dos itens", () => {
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(1)").click();
    cy.contains("Coca Cola");
    cy.contains("Quantidade");
    cy.contains("R$ 15,00");
    cy.get("p[class$='close-button']").click();
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(1)").click();
    cy.get("input[class^='product-modal']").clear().type("5")
    cy.get("input[class*='primary']").click();
    cy.get("p[class$='close-button']").click();
    cy.contains("R$ 75,00");
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(2)").click();
    cy.contains("Guarana Antartica");
    cy.contains("Quantidade")
    cy.get("input[class^='product-modal']").clear().type("3")
    cy.get("input[class*='primary']").click();
    cy.get("p[class$='close-button']").click();
    cy.contains("R$ 120,00");
    cy.get("select[class$='selector']").select("2");
    cy.contains("Bobó de camarão");
    cy.get("html > body > div > div > div > div > div > div:nth-of-type(2)").click()
    cy.contains("Quantidade:");
    cy.contains("R$ 39,99");
    cy.get("input[class^='product-modal']").clear().type("3");
    cy.get("input[class*='primary']").click();
    cy.contains("R$ 239,97");
    cy.get("p[class$='close-button']").click();
  })
  it('Deve ser possível para o NOVO Cliente realizar o logout da sua nova conta', () => {
    cy.get("div[class$='logout-button']").click();
    cy.contains("Faça login");
  })
})
