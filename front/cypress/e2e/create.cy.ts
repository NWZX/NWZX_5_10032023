describe('Session spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });
  it('Show create successfully', () => {
    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher',
      },
      [
        {
          id: 1234,
          lastName: 'Tatcher',
          firstName: 'Maggy',
          createdAt: '2023-03-20T12:34:56.789Z',
          updatedAt: '2023-03-20T12:34:56.789Z',
        },
        {
          id: 1235,
          lastName: 'Abyss',
          firstName: 'Aby',
          createdAt: '2023-03-20T12:34:56.789Z',
          updatedAt: '2023-03-20T12:34:56.789Z',
        },
      ]
    ).as('teacher');
    cy.intercept('POST', '/api/session', {
      body: {
        id: 1,
        name: 'Session 5',
        date: '2023-04-01T09:00:00Z',
        teacher_id: 1234,
        description: 'This is a test session.',
        users: [5678, 9012],
        createdAt: '2023-03-20T12:34:56.789Z',
        updatedAt: '2023-03-20T12:34:56.789Z',
      },
    });

    cy.get('.mat-card-title', { multiple: true }).should('have.length', 3);
    cy.get('button.mat-focus-indicator').eq(0).click();
    cy.url().should('include', '/sessions/create');
    cy.get('h1').contains('Create session');
    cy.get('#mat-input-2').clear().type('Session 5');
    cy.get('#mat-input-3').clear().type('2023-04-01');
    cy.get('#mat-select-0').click();
    cy.get('#mat-option-1').click();
    cy.get('#mat-input-4').clear().type('This is a test session.');
    cy.get('button[type=submit]').click();
    cy.url().should('include', '/sessions');
  });
});
