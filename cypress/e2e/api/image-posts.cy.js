describe("Image Posts API", () => {
  const apiUrl = Cypress.env("apiUrl") || "http://localhost:8080";

  it("logs in and fetches the first page of image posts", () => {
    cy.request("POST", `${apiUrl}/user/login`, {
      email: "admin@example.com",
      password: "1234",
    }).then((loginResponse) => {
      expect(loginResponse.status).to.eq(200);
      const authHeader = loginResponse.headers["authorization"];
      expect(authHeader).to.match(/^Bearer\s.+/);

      cy.request({
        method: "GET",
        url: `${apiUrl}/api/image-posts?page=0&size=5`,
        headers: {
          Authorization: authHeader,
        },
      }).then((postsResponse) => {
        expect(postsResponse.status).to.eq(200);
        expect(postsResponse.body).to.have.property("content");
        expect(postsResponse.body.content).to.be.an("array");
        expect(postsResponse.body.content.length).to.be.greaterThan(0);
        const firstPost = postsResponse.body.content[0];
        expect(firstPost).to.have.property("imageUrl");
        expect(firstPost).to.have.property("description");
        expect(firstPost).to.have.property("authorId");
      });
    });
  });
});
