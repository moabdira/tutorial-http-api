package com.example

import com.example.models.Customer
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerRouteTests {
    private val BASE_URL = "http://localhost:8080"
    private val CUSTOMER = "/customer"

    @Test
    fun testGetCustomers() = testApplication {
        val actual: Customer = client.get("/customer/100").body()
        val expected = Customer("100", "Jane", "Smith", "jane.smith@company.com")
        assertEquals(expected, actual)
    }

    @Test
    fun testPostCustomer() = testApplication {
        val response = client.post("/customer"){
            contentType(ContentType.Application.Json)
            //header(HttpHeaders.ContentType, ContentType.Application.Json)
            //setBody(Customer("100", "Jane", "Smith", "jane.smith@company.com"))
            setBody("""{"id": "400", "firstName": "Mo", "lastName": "Smith", "email": "mo.smith@company.com" }""")
        }
        assertEquals("Customer stored correctly", response.bodyAsText())
        assertEquals(HttpStatusCode.Created, response.status)

//        val customers = mutableListOf<Customer>(
//            Customer("100", "Jane", "Smith", "jane.smith@company.com"),
//            Customer("200", "John", "Smith", "john.smith@company.com"),
//            Customer("300", "Mary", "Smith", "mary.smith@company.com")
//        )
//        val response = client.post(CUSTOMER){
//            contentType(ContentType.Application.Json)
//            setBody(Customer("400", "Mo", "Smith", "mo.smith@company.com"))
//        }
//        for (customer in customers){
//            val response = client.post("/customer"){
//                contentType(ContentType.Application.Json)
//                setBody(customer)
//            }
//            assertEquals(HttpStatusCode.Created, response.status)
//        }
    }
}