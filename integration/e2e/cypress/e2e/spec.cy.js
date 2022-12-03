describe("Funcionalidade de Login", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/#/')
    cy.contains('COMEÇAR')
    cy.get('#init-btn').click();
  })
  it('Deve ser possível preencher os dados de login', () => {
    const confirmBtn = cy.get('#formBtnSubmit');
    confirmBtn.should('be.disabled');
    // Nome é o único tipo de necessário para logar
    cy.get('#mat-input-0').type('Juliana');
    // Botão deveria estar habilitado após a inserção do nome
    confirmBtn.should('not.be.disabled');
    // Deve ser possível logar com CPF
    cy.get('#mat-input-1').type('000.000.000-01');
    confirmBtn.click();
  })
  it('Deve ser possível ver a página a página logada', () => {
    cy.get('#formBtnSubmit').click();
    cy.wait(500)
    cy.contains('Nome: Juliana');
    cy.contains('CPF: 000.000.000-01');
  })
})

describe("Seleção de item para o carrinho", () => {
  it('Deve ser possível carregar a página inicial', () => {
    cy.visit('/#/')
    cy.contains('COMEÇAR')
    cy.get('#init-btn').click();
  })
  it('Deve ser possível preencher os dados de login', () => {
    const confirmBtn = cy.get('#formBtnSubmit');
    confirmBtn.should('be.disabled');
    // Nome é o único tipo de necessário para logar
    cy.get('#mat-input-0').type('Juliana');
    // Botão deveria estar habilitado após a inserção do nome
    confirmBtn.should('not.be.disabled');
    // Deve ser possível logar com CPF
    cy.get('#mat-input-1').type('000.000.000-01');
    confirmBtn.click();
  })
  it('Deve ser possível carregar a página logada', () => {
    cy.get('#formBtnSubmit').click();
    cy.wait(500)
    cy.contains('Nome: Juliana');
    cy.contains('CPF: 000.000.000-01');
  })
  it('Deve ser possível selecionar um item', () => {
    cy.get('.mat-card-title').contains('Frango com quiabo').click()
    cy.get('.mat-button-wrapper').contains('Adicionar ao carrinho').click()
    cy.contains('Valor total: R$ 59,99');
  })
})

describe("Seleção de multiplos itens para o carrinho", () => {
  it('Deve carregar a página inicial e ir para a tela de login', () => {
    cy.visit('/#/')
    cy.contains('COMEÇAR')
    cy.get('#init-btn').click();
  })
  it('Deve ser possível preencher os dados', () => {
    const confirmBtn = cy.get('#formBtnSubmit');
    confirmBtn.should('be.disabled');
    // Nome é o único tipo de necessário para logar
    cy.get('#mat-input-0').type('Juliana');
    // Botão deveria estar habilitado após a inserção do nome
    confirmBtn.should('not.be.disabled');
    // Deve ser possível logar com CPF
    cy.get('#mat-input-1').type('000.000.000-01');
    confirmBtn.click();
  })
  it('Deve ser possível carregar a página logada', () => {
    cy.get('#formBtnSubmit').click();
    cy.wait(500)
    cy.contains('Nome: Juliana');
    cy.contains('CPF: 000.000.000-01');
  })
  it('Deve ser possível selecionar um item', () => {
    cy.get('.mat-card-title').contains('Frango com quiabo').click()
    cy.get('.mat-button-wrapper').contains('Adicionar ao carrinho').click()
    cy.get('.mat-card-title').contains('Coca Cola').click()
    cy.get('.mat-button-wrapper').contains('Adicionar ao carrinho').click()
    cy.contains('Valor total: R$ 74,99');
  })
})

describe("Deve ser possível ver a lista de pedidos no carrinho", () => {
  it('Deve carregar a página inicial e ir para a tela de login', () => {
    cy.visit('/#/')
    cy.contains('COMEÇAR')
    cy.get('#init-btn').click();
  })
  it('Deve ser possível preencher os dados', () => {
    const confirmBtn = cy.get('#formBtnSubmit');
    confirmBtn.should('be.disabled');
    // Nome é o único tipo de necessário para logar
    cy.get('#mat-input-0').type('Juliana');
    // Botão deveria estar habilitado após a inserção do nome
    confirmBtn.should('not.be.disabled');
    // Deve ser possível logar com CPF
    cy.get('#mat-input-1').type('000.000.000-01');
    confirmBtn.click();
  })
  it('Deve ser possível carregar a página logada', () => {
    cy.get('#formBtnSubmit').click();
    cy.wait(500)
    cy.contains('Nome: Juliana');
    cy.contains('CPF: 000.000.000-01');
  })
  it('Deve ser possível selecionar um item', () => {
    cy.get('.mat-card-title').contains('Frango com quiabo').click()
    cy.get('.mat-button-wrapper').contains('Adicionar ao carrinho').click()

    cy.get('.mat-card-title').contains('Coca Cola').click()
    cy.get('.mat-button-wrapper').contains('Adicionar ao carrinho').click()

    cy.get('.mat-button-wrapper').contains('shopping_cart').click()
    cy.contains('Frango com quiabo');
    cy.contains('Coca Cola');

    cy.contains('Nome');
    cy.contains('Quantidade');
    cy.contains('Ações');
    cy.contains('remover');

    cy.get('.mat-button-wrapper').contains('Fechar').click()
    cy.contains('Items do pedido').should('not.exist')
  })
})