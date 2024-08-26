
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class ApiStoreStepsDefs {

    private Response response;
    private String orderId;

    @Given("Dado que creo un nuevo pedido con los siguientes detalles")
    public void dado_que_creo_un_nuevo_pedido_con_los_siguientes_detalles(io.cucumber.datatable.DataTable dataTable) {
        // Convertir DataTable a una lista de mapas de tipo String
        List<Map<String, String>> orderDetailsList = dataTable.asMaps(String.class, String.class);

        // veamos si la lista no esté vacía
        if (orderDetailsList.isEmpty()) {
            throw new RuntimeException("DataTable is empty");
        }

        // Obtengo el primer mapa de la lista
        Map<String, String> orderDetails = orderDetailsList.get(0);

        // Creo un nuevo pedido
        response = RestAssured.given()
                .contentType("application/json")
                .body(orderDetails)
                .post("https://petstore.swagger.io/store/order");

        // Verificar que el código de estado de la respuesta sea 200
        assertEquals(200, response.getStatusCode());

        // Extraer el orderId de la respuesta, suponiendo que 'id' es parte de la respuesta
        orderId = response.jsonPath().getString("id");

        // Validar que el orderId no sea nulo o vacío
        if (orderId == null || orderId.isEmpty()) {
            throw new RuntimeException("Order ID is missing in the response");
        }
    }
//COMO CORRIJO ESTO :c
    @When("Cuando envíe una solicitud POST a \"/store/order\"")
    public void cuando_envie_una_solicitud_post_a_store_order() {
        // El POST ya ha sido manejado en el paso anterior
    }
    @Then("el código de estado de la respuesta debería ser 200")
    public void el_codigo_de_estado_de_la_respuesta_deberia_ser_200() {
        assertEquals(200, response.getStatusCode());
    }

    @Then("el cuerpo de la respuesta debería contener los detalles del pedido")
    public void el_cuerpo_de_la_respuesta_deberia_contener_los_detalles_del_pedido(io.cucumber.datatable.DataTable dataTable) {
        // Convertir DataTable a una lista de mapas de tipo String
        List<Map<String, String>> expectedOrderDetailsList = dataTable.asMaps(String.class, String.class);


        if (expectedOrderDetailsList.isEmpty()) {
            throw new RuntimeException("DataTable is empty");
        }

        // Obtener el primer mapa de la lista
        Map<String, String> expectedOrderDetails = expectedOrderDetailsList.get(0);

        // Comparar los detalles esperados con los detalles en el cuerpo de la respuesta
        for (Map.Entry<String, String> entry : expectedOrderDetails.entrySet()) {
            assertEquals(entry.getValue(), response.jsonPath().getString(entry.getKey()));
        }
    }

    @Given("he creado un pedido con id {string}")
    public void he_creado_un_pedido_con_id(String orderId) {
        this.orderId = orderId;
    }

    @When("envíe una solicitud GET a \"/store/order/{orderId}\"")
    public void envie_una_solicitud_get_a_store_order(String orderId) {
        response = RestAssured.given()
                .get("https://petstore.swagger.io/store/order/" + orderId);
    }

    @Then("el cuerpo de la respuesta de la consulta debería contener los detalles del pedido")
    public void el_cuerpo_de_la_respuesta_de_la_consulta_deberia_contener_los_detalles_del_pedido(io.cucumber.datatable.DataTable dataTable) {
        // Convertir DataTable a una lista de mapas de tipo String
        List<Map<String, String>> expectedOrderDetailsList = dataTable.asMaps(String.class, String.class);

        // Asegúrate de que la lista no esté vacía
        if (expectedOrderDetailsList.isEmpty()) {
            throw new RuntimeException("DataTable is empty");
        }

        // Obtener el primer mapa de la lista
        Map<String, String> expectedOrderDetails = expectedOrderDetailsList.get(0);

        // Comparar los detalles esperados con los detalles en el cuerpo de la respuesta
        for (Map.Entry<String, String> entry : expectedOrderDetails.entrySet()) {
            assertEquals(entry.getValue(), response.jsonPath().getString(entry.getKey()));
        }
    }

}

