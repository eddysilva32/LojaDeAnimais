package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Pet {
    public String tokenGeral; // Vari�vel para receber o Token
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordemDaExecucao() throws IOException {
        incluirPet();
        consultarPet();
        alterarPet();
        excluirPet();
    }


    // Create / Incluir / POST
    public void incluirPet() throws IOException {
        // Ler o conte�do do arquivo pet.json
        String jsonBody = lerJson("data/pet.json");

        given()                                 // Dado que
                .contentType("application/json")    // Tipo de conte�do da requisi��o
                // "text/xml" para web services comuns
                // "application/json" para APIs REST
                .log().all()                        // Gerar um log completo da requisi��o
                .body(jsonBody)                     // Conte�do do corpo da requisi��o
        .when()                                 // Quando
                .post("https://petstore.swagger.io/v2/pet") // Opera��o e endpoint
        .then()                                 // Ent�o
                .log().all()                        // Gerar um log completo da resposta
                .statusCode(200)                    // Validou o c�digo de status da requisi��o como 200
                // .body("code", is(200))  // Valida o code como 200
                .body("id", is(4220))    // Validou a tag id com o conte�do esperado
                .body("name", is("Espanca")) // Validou a tag nome como Garfield
                .body("tags.name", contains("Adoption")) // Validou a tag Name filha da tag Tags
        ;
        System.out.print("Executou o servi�o");
    }

    // Reach or Research / Consultar / Get
    public void consultarPet(){
        String petId = "4220";

        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conte�do da requisi��o
                .log().all()                                            // Mostrar tudo que foi enviado
                .when()                                             // Quando
                .get("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
                .then()                                             // Ent�o
                .log().all()                                            // Mostrar tudo que foi recebido
                .statusCode(200)                                        // Validou que a opera��o foi realizada
                .body("name", is("Espanca"))                // Validou o nome do pet
                .body("category.name", is("Dog"))            // Validou a esp�cie
        ;
    }

    // Update / Alterar / PUT
    @Test
    public void alterarPet() throws IOException {
        // Ler o conte�do do arquivo petput.json
        String jsonBody = lerJson("data/petput.json");

        given()                                 // Dado que
            .contentType("application/json")    // Tipo de conte�do da requisi��o
            // "text/xml" para web services comuns
            // "application/json" para APIs REST
            .log().all()                        // Gerar um log completo da requisi��o
            .body(jsonBody)                     // Conte�do do corpo da requisi��o
        .when()
                .put("https://petstore.swagger.io/v2/pet/")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Espanca"))
                .body("status", is("Adopted"))
        ;

    }

    // Delete / Excluir / DEL
    @Test
    public void excluirPet(){
        String petId = "4220";

        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conte�do da requisi��o
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
                .delete("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message", is(petId))
        ;


    }

    // Login
    @Test
    public void loginUser(){
        // public String loginUser(){
        String token =
        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conte�do da requisi��o
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
            .get("https://petstore.swagger.io/v2/user/login?username=eddy&password=silva'")
        .then()
            .log().all()
            .statusCode(200)
            .body("message", containsString("logged in user session:"))
            .extract()
             .path("message")
    ;
    tokenGeral = token.substring(23); // Separa o Token da frase
    System.out.println("O Token v�lido � " + tokenGeral);
    //Return tokenGeral;

    }


}
