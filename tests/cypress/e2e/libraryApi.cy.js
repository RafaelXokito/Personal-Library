describe('Library API Endpoints', () => {
  let writerToken = '';
  let readerToken = '';

  let refreshToken = '';

  let bookId = '';

  let boilerplateEmail = `johndoe${Date.now()}@example.com`;
  let boilerplatePassword = 'password123';

  before(() => {
    // Assuming you have some predefined writer and reader credentials
    const writerCredentials = {
      email: 'leslie@luv2code.com',
      password: 'ThisIsMyFirstPassword'
    };
    const readerCredentials = {
      email: 'leslieReader2@luv2code.com',
      password: 'ThisIsMyFirstPassword'
    };

    // Login as writer and save the token
    cy.request('POST', 'http://localhost:8080/api/auth/login', writerCredentials)
        .its('body')
        .then((body) => {
          writerToken = body.accessToken;

          refreshToken = body.refreshToken;
        });

    // Login as reader and save the token
    cy.request('POST', 'http://localhost:8080/api/auth/login', readerCredentials)
        .its('body')
        .then((body) => {
          readerToken = body.accessToken;
        });
  });

  it('Writer can create a book', () => {
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/api/books',
      headers: {
        'Authorization': `Bearer ${writerToken}`
      },
      body: {
        title: 'The Enchanted Forest2',
        description: 'A captivating tale of mystery and magic set in a dense, enchanted forest where every tree has a story to tell.',
        content: 'Once upon a time, in a land far, far away, ...'
      }
    })
        .then((response) => {
          // Check status
          expect(response.status).to.equal(201);  // Assuming 201 is the status for created

          // Extract bookId
          bookId = response.body.id;
        }); 
  });

  it('Reader cannot create a book', () => {
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/api/books',
      headers: {
        'Authorization': `Bearer ${readerToken}`
      },
      body: {
        title: 'The Enchanted Forest2',
        description: 'A captivating tale of mystery and magic set in a dense, enchanted forest where every tree has a story to tell.',
        content: 'Once upon a time, in a land far, far away, ...'
      },
      failOnStatusCode: false // Important to not fail the test when 4xx/5xx response is received
    })
        .its('status')
        .should('not.equal', 201); // Assuming any status other than 201 means not created
  });

  it('Anyone can get books', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/books'
    })
        .its('status')
        .should('equal', 200);
  });

  it('Anyone can search books', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/books/search?title=Forest2'
    })
        .its('status')
        .should('equal', 200);
  });

  it('Authenticated Writer user can retrieve their books', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/books/my',
      headers: {
        'Authorization': `Bearer ${writerToken}`
      }
    })
    .then((response) => {
      // Check status
      expect(response.status).to.equal(200);  // Assuming 200 is the status for success

      // Check that the returned data is an array (optional)
      expect(response.body).to.be.an('array');

      // Further assertions can be added here if necessary to check the content of the returned books
    });
  });

  it('Authenticated Reader user can retrieve their books', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/books/my',
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    })
    .then((response) => {
      // Check status
      expect(response.status).to.equal(200);  // Assuming 200 is the status for success

      // Check that the returned data is an array (optional)
      expect(response.body).to.be.an('array');

      // Further assertions can be added here if necessary to check the content of the returned books
    });
  });

  it('Endpoint should return 401 if no token is provided', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/books/my',
      failOnStatusCode: false // this ensures Cypress does not fail on a non-2xx HTTP status
    })
    .then((response) => {
      expect(response.status).to.equal(401);
    });
  });

  it('Endpoint should return 401 if an invalid token is provided', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/books/my',
      headers: {
        'Authorization': `Bearer invalidToken1234567890`
      },
      failOnStatusCode: false
    })
    .then((response) => {
      expect(response.status).to.equal(401);
    });
  });

  it('Reader can add a book', () => {
    cy.request({
      method: 'PATCH',
      url: `http://localhost:8080/api/books/${bookId}/add`,
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Reader can mark a book as current reading book', () => {
    cy.request({
      method: 'PATCH',
      url: `http://localhost:8080/api/books/${bookId}/read`,
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Writer cannot mark a book as read', () => {
    cy.request({
      method: 'PATCH',
      url: `http://localhost:8080/api/books/${bookId}/read`,
      headers: {
        'Authorization': `Bearer ${writerToken}`
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('not.equal', 200); // Assuming any status other than 200 means failure
  });

  it('Writer can see current readers of a book', () => {
    cy.request({
      method: 'GET',
      url: `http://localhost:8080/api/books/${bookId}/currentreaders`,
      headers: {
        'Authorization': `Bearer ${writerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Writer can see all readers of a book', () => {
    cy.request({
      method: 'GET',
      url: `http://localhost:8080/api/books/${bookId}/readers`,
      headers: {
        'Authorization': `Bearer ${writerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Reader can navigate to next page of book listings', () => {
    cy.request({
      method: 'PATCH',
      url: 'http://localhost:8080/api/books/nextpage',
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Reader can navigate to previous page of book listings', () => {
    cy.request({
      method: 'PATCH',
      url: 'http://localhost:8080/api/books/previouspage',
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Reader can remove a book', () => {
    cy.request({
      method: 'DELETE',
      url: `http://localhost:8080/api/books/${bookId}/remove`,
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Anyone can register', () => {
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/api/auth/register',
      body: {
        firstName: 'John',
        lastName: 'Doe',
        email: boilerplateEmail,
        password: boilerplatePassword,
        isWriter: false
      }
    })
        .its('status')
        .should('equal', 201);  // Assuming 201 is the status for successfully registered
  });

  it('Anyone can login', () => {
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/api/auth/login',
      body: {
        email: boilerplateEmail,
        password: boilerplatePassword
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for successful login
  });

  it('Token can be refreshed', () => {
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/api/auth/token',
      body: {
        refreshToken: refreshToken
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for successful token refresh
  });

  it('Logged in user can get their details using a bearer token', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/auth/me',
      headers: {
        'Authorization': `Bearer ${writerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for success
  });

  it('Unauthorized user cannot get user details using a fake bearer token', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/auth/me',
      headers: {
        'Authorization': 'Bearer InvalidToken'  // Invalid Token to test unauthorized access
      },
      failOnStatusCode: false // This prevents Cypress from failing when it encounters a non-2xx status code
    })
        .its('status')
        .should('equal', 401);  // Assuming 401 is the status for forbidden access
  });

  it('Only Writers can view all readers of a book', () => {
    cy.request({
      method: 'GET',
      url: `http://localhost:8080/api/books/${bookId}/readers`,
      headers: {
        'Authorization': `Bearer ${readerToken}`
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('equal', 403); // Assuming 403 for forbidden access
  });

  it('Readers can remove a book from the system', () => {
    // First, a reader adds a book
    cy.request({
      method: 'PATCH',
      url: `http://localhost:8080/api/books/${bookId}/add`,
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    });

    // Then the reader removes it
    cy.request({
      method: 'DELETE',
      url: `http://localhost:8080/api/books/${bookId}/remove`,
      headers: {
        'Authorization': `Bearer ${readerToken}`
      }
    })
        .its('status')
        .should('equal', 200); // Assuming 200 for successful removal
  });

  it('Unauthorized user cannot add a book', () => {
    cy.request({
      method: 'PATCH',
      url: `http://localhost:8080/api/books/DELETE/add`,
      headers: {
        'Authorization': 'Bearer InvalidToken'  // Using Invalid Token
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('equal', 401);  // Assuming 403 is the status for forbidden access
  });

  it('Unauthorized user cannot mark a book as read', () => {
    cy.request({
      method: 'PATCH',
      url: `http://localhost:8080/api/books/${bookId}/read`,
      headers: {
        'Authorization': 'Bearer InvalidToken'  // Using Invalid Token
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('equal', 401);  // Assuming 403 is the status for forbidden access
  });

  it('Unauthorized user cannot navigate to next or previous book pages', () => {
    cy.request({
      method: 'PATCH',
      url: 'http://localhost:8080/api/books/nextpage',
      headers: {
        'Authorization': 'Bearer InvalidToken'  // Using Invalid Token
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('equal', 401);  // Assuming 403 is the status for forbidden access

    cy.request({
      method: 'PATCH',
      url: 'http://localhost:8080/api/books/previouspage',
      headers: {
        'Authorization': 'Bearer InvalidToken'  // Using Invalid Token
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('equal', 401);  // Assuming 403 is the status for forbidden access
  });

  it('Writer can retrieve current readers of a book', () => {
    cy.request({
      method: 'GET',
      url: `http://localhost:8080/api/books/${bookId}/currentreaders`,
      headers: {
        'Authorization': `Bearer ${writerToken}`
      }
    })
        .its('status')
        .should('equal', 200);  // Assuming 200 is the status for successful retrieval
  });

  it('Reader cannot retrieve current readers of a book', () => {
    cy.request({
      method: 'GET',
      url: `http://localhost:8080/api/books/${bookId}/currentreaders`,
      headers: {
        'Authorization': `Bearer ${readerToken}`
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('equal', 403);  // Assuming 403 is the status for forbidden access
  });

  it('Unauthorized user cannot refresh token', () => {
    let invalidRefreshToken = 'InvalidRefreshToken'; // Invalid refresh token
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/api/auth/token',
      body: {
        refreshToken: invalidRefreshToken
      },
      failOnStatusCode: false
    })
        .its('status')
        .should('equal', 401); // Assuming 403 for forbidden access
  });

});

