describe('Account spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });
  it('See account info successfully', () => {  
    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 1,
        email: 'yoga@studio.com',
        firstName: 'yoga',
        lastName: 'studio',
        admin: true,
        createdAt: '2021-05-05T12:00:00.000Z',
        updatedAt: '2021-05-05T12:00:00.000Z',
      },
    }).as('me');
    
    cy.get('#account').click();

    cy.url().should('include', '/me');
    
    cy.get('p').contains('Name:').should('have.text', 'Name: yoga STUDIO');
  });
});
