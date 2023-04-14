describe('Session spec', () => {
    beforeEach(() => {
        cy.login('yoga@studio.com', 'test!1234');
    });
    it('Show detail successfully', () => {

        cy.intercept(
          {
            method: 'GET',
            url: '/api/session/1',
          },
          {
            id: 1,
            name: 'Session 1',
            date: '2023-04-01T09:00:00Z',
            teacher_id: 1234,
            description: 'This is a test session.',
            users: [5678, 9012],
            createdAt: '2023-03-20T12:34:56.789Z',
            updatedAt: '2023-03-20T12:34:56.789Z',
        }).as('session');
        cy.intercept(
          {
            method: 'GET',
            url: '/api/teacher/1234',
          },
          {
            id: 1,
            lastName: 'Tatcher',
            firstName: 'Maggy',
            createdAt: '2023-03-20T12:34:56.789Z',
            updatedAt: '2023-03-20T12:34:56.789Z',
          }
        ).as('teacher');
        
        cy.get('.mat-card-title', { multiple: true }).should('have.length', 3);
        cy.get('button.mat-focus-indicator').eq(1).click();
        cy.url().should('include', '/detail/1');
        cy.get('h1').contains('Session 1');
    });
});
