describe('Login spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });
  it('Login successfully', () => {
    cy.url().should('include', '/sessions')
  })
});