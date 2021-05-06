package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class User {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordenarExecucao() throws IOException {
        incluirUsuario();
        consultarUsuario();
        alterarUsuario();
        excluirUsuario();
    }


    @Test
    public void incluirUsuario() throws IOException {
        String jsonBody = lerJson("data/user.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post("https://petstore.swagger.io/v2/user")
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("5309"))
        ;
        System.out.print("Executou o serviço");
    }
    @Test
    public void consultarUsuario(){
        String username = "ejsilva";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(5309))
                .body("firstName", is("eddy"))
                .body("lastName", is("silva"))
                .body("email", is("eddy.silva32@gmail.com"))
        ;
    }

    @Test
    public void alterarUsuario() throws IOException {
        String jsonBody = lerJson("data/userput.json");
        String username = "jcorreia";

        given()
                .log().all()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .put("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("5309"))
        ;

    }

    @Test
    public void excluirUsuario(){
        String username = "ejsilva";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message", is(username))
        ;

    }

}

