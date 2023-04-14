describe('Not Found spec', () => {
    it('Not Found successfully', () => {
      cy.visit('/random');
      cy.url().should('include', '/404');
    });
});
