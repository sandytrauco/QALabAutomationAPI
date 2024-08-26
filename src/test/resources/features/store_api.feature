
Feature: Gestión de Pedidos en la API Store

  Scenario Outline: Creación de Order
    Given Dado que creo un nuevo pedido con los siguientes detalles

      | petId  | quantity | shipDate          | status    | complete |
      | <petId> | <quantity> | <shipDate>        | <status>  | <complete> |

    When Cuando envíe una solicitud POST a "/store/order"
    Then el código de estado de la respuesta debería ser 200
    And el cuerpo de la respuesta debería contener los detalles del pedido

      | petId  | quantity | shipDate          | status    | complete |
      | <petId> | <quantity> | <shipDate>        | <status>  | <complete> |

    Examples:
      | petId | quantity | shipDate         | status  | complete |
      | 1     | 5        | 2024-08-25T00:00:00Z | placed  | true     |
      | 2     | 3        | 2024-08-26T00:00:00Z | shipped | false    |
      | 3     | 10       | 2024-08-27T00:00:00Z | delivered | true    |

  Scenario: Consulta de pedido
    Given he creado un pedido con id "<orderId>"
    When envíe una solicitud GET a "/store/order/{orderId}"
    Then el código de estado de la respuesta debería ser 200
    And el cuerpo de la respuesta debería contener los detalles del pedido

      | petId  | quantity | shipDate          | status    | complete |
