describe('Account spec', () => {
  it('See account info successfull', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');
      
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

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/sessions');
    
    
    cy.get('#account').click();

    cy.url().should('include', '/me');
    
    cy.get('p').contains('Name:').should('have.text', 'Name: yoga STUDIO');
  });
});
