describe("Funcionalidade de Login (Cliente)", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
    cy.get("input[type='text']").type("12312312312");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
  })
})

describe("Funcionalidade de Login (Garçom)", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
    cy.get("div[class='login__type-selector__item']").click();
    cy.get("input[type='text']").type("11111111111");
    cy.get("input[type='password']").type("12345678");
    cy.get("input[type='submit']").click();
    cy.get('html > body > div > div > div > div > div > div:nth-of-type(1)')
        .should('have.css', 'cursor','not-allowed')
  })
})
