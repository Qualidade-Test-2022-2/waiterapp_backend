describe("Funcionalidade de Login", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/');
    cy.contains('Faça login');
  })
})