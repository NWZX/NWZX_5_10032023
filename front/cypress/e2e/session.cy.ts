describe('Session spec', () => {
  it('Show sessions successfull', () => {
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
      [
        {
          id: 1,
          name: 'Session 1',
          date: '2023-04-01T09:00:00Z',
          teacher_id: 1234,
          description: 'This is a test session.',
          users: [5678, 9012],
          createdAt: '2023-03-20T12:34:56.789Z',
          updatedAt: '2023-03-20T12:34:56.789Z',
        },
        {
          id: 2,
          name: 'Session 2',
          date: '2023-04-02T10:00:00Z',
          teacher_id: 5678,
          description: 'This is another test session.',
          users: [1234],
          createdAt: '2023-03-21T13:45:00.123Z',
          updatedAt: '2023-03-22T14:56:00.234Z',
        },
      ]
    ).as('session');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

      cy.url().should('include', '/sessions');
      cy.get('.mat-card-title', { multiple: true }).should('have.length', 3); // 2 sessions from the mock + 1 from the "Create new session" card
  });
});
