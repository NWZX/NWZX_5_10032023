describe('Session spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });
  it('Show sessions successfully', () => {
      cy.url().should('include', '/sessions');
      cy.get('.mat-card-title', { multiple: true }).should('have.length', 3); // 2 sessions from the mock + 1 from the "Create new session" card
  });
});
