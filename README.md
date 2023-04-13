# ClevertecServletTask
___
<ul>
<li>WEB-приложение реализовано на платформе JDK 17.</li>
<li>Используемая СУБД  - PostgreSQL 14.</li>
<li>Контейнер сервлетов — Apache Tomcat 10.0.73.</li>
</ul>

## Usage

### Product CRUD

The Product entity has the following fields:

-   `id`: unique identifier for the product
-   `name`: name of the product
-   `price`: price of the product
-   `quantity`: quantity of the product

The application supports the following operations on the Product entity:

-   `GET /products`: get a list of all products
-   `POST /products`: create a new product
-   `PUT /products/{id}`: update an existing product
-   `DELETE /products/{id}`: delete an existing product

### Pagination

The `GET /products` endpoint supports pagination. By default, it returns 20 products per page. You can specify the page size and page number using the `page` and `limit` query parameters:


`GET /products?page=10&limit=2`

This will return the second page of 10 products.

### Receipt Generation

The application supports generating receipts in PDF format for a given set of products and discount card. To generate a receipt, make a GET request to the `GET /receipts` endpoint with the following query parameters:

-   `productId`: a comma-separated list of product IDs
-   `discountCardId` (optional): the ID of the discount card to apply to the products

For example:

`GET /receipts?quantity=1&quantity=1`

This will generate a receipt in PDF format for the products with IDs


