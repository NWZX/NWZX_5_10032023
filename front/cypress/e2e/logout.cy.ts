describe('Logout spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });
  it('Logout successfully', () => {
    cy.get('#logout').click();

    cy.url().should('include', '/');
  });
});
