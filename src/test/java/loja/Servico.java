package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Servico {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Create / Incluir / POST
    @Test
    public void IncluirPet() throws IOException {
                                                            // Ler o conte�do do arquivo pet.json
        String jsonBody = lerJson("data/pet.json");

        given()                                             // Dado que
                .contentType("application/json")            // Tipo de conte�do da requisi��o
                                                            // "text/xml" para web services comuns
                                                            // "application/json" para APIs REST
                .log().all()                                // Gerar um log completo da requisi��o
                .body(jsonBody)                             // Conte�do do corpo da requisi��o
                .when()                                     // Quando
                .post("https://petstore.swagger.io/v2/pet") // Opera��o e endpoint
                .then()                                     // Ent�o
                .log().all()                                // Gerar um log completo da resposta
                .statusCode(200)                            // Validou o c�digo de status da requisi��o como 200
                // .body("code", is(200))                   // Valida o code como 200
                .body("id", is(4220))                       // Validou a tag id com o conte�do esperado
                .body("name", is("Espanca"))                // Validou a tag nome como Garfield
                .body("tags.name", contains("Adoption"))    // Validou a tag Name filha da tag Tags
        ;
        System.out.println("Executou o servi�o");


    }

}