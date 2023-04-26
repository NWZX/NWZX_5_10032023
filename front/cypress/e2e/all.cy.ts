describe('All spec', () => {
    beforeEach(() => {
        cy.login('yoga@studio.com', 'test!1234');
    });
    it('Not Found successfully', () => {
        cy.visit('/random');
        cy.url().should('include', '/404');
    });
    it('Register successfully', () => {
        cy.visit('/register');

        cy.intercept('POST', '/api/auth/register', {
        body: {
            id: 1,
            email: 'userName',
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

        cy.get('input[formControlName=firstName]').type('yoga');
        cy.get('input[formControlName=lastName]').type('studio');
        cy.get('input[formControlName=email]').type('yoga@studio.com');
        cy.get('input[formControlName=password]').type(
        `${'test!1234'}{enter}{enter}`
        );

        cy.url().should('include', '/login');
    });
    it('Login successfully', () => {
        cy.url().should('include', '/sessions');
    });
    it('Logout successfully', () => {
      cy.get('#logout').click();

      cy.url().should('include', '/');
    });
    it('Show sessions successfully', () => {
      cy.url().should('include', '/sessions');
      cy.get('.mat-card-title', { multiple: true }).should('have.length', 3); // 2 sessions from the mock + 1 from the "Create new session" card
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
        }
      ).as('session');
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
    it('Show edit successfully', () => {
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
        }
      ).as('session');
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
      cy.intercept('PUT', '/api/session/1', {
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
      cy.get('button.mat-focus-indicator').eq(2).click();
      cy.url().should('include', '/update/1');
      cy.get('h1').contains('Update session');
      cy.get('#mat-input-2').should('have.value', 'Session 1');
      cy.get('#mat-input-2').clear().type('Session 5');
      cy.get('button[type=submit]').click();
      cy.url().should('include', '/sessions');
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
