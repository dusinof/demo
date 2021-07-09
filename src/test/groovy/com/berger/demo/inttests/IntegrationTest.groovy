package com.berger.demo.inttests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.transaction.Transactional

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
@Rollback
class IntegrationTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    EntityManager entityManager

    ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        objectMapper.registerModule(new Jdk8Module()) // to support optional when parsing json.
    }

    def post(String url) {
        mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().response.contentAsString
    }

    def get(String url) {
        mvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().response.contentAsString
    }

    def delete(String url) {
        mvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().response.contentAsString
    }


}
