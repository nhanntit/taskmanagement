package com.tm.taskManagement

//import jdk.nashorn.internal.runtime.Specialization
import com.tm.taskManagement.controller.TaskController
import net.sf.json.JSON
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ActiveProfiles
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException

import spock.lang.*

//@SpringBootTest(
//        classes = TaskController.class,
//        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
//)
//@ActiveProfiles("test")
//@ContextConfiguration
class TaskControllerTest extends Specification{
    @Shared
    RESTClient client = new RESTClient('http://localhost:8080')
//    def "Should return 200"() {
//        when:
//            def response = client.get([ path: '/api/tasks'])
//        then:
//            with(response) {
//                status == 200
//                contentType == "application/json"
//            }
//    }
    def "maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [3, 5, 9]
        b << [7, 4, 9]
        c << [7, 5, 9]
    }
//    def "Should return 200 and success"() {
//        when:
//        def httpBuilder = new HTTPBuilder( "http://localhost:8080/api" )
//        httpBuilder.ignoreSSLIssues()
//
//        httpBuilder.request(GET,TEXT) { req ->
//            uri.path = '/users'
//            headers.'Accept' = 'application/json'
//            headers.'User-Agent' = 'Mozilla/5.0'
//
//            response.success = { resp, reader ->
//                println JsonPath.read(reader.text, "\$[*].login")
//            }
//        }
//        then:
//        Math.max(a, b) == c
//
//        where:
//        a << [3, 5, 9]
//        b << [7, 4, 9]
//        c << [7, 5, 9]
//
//    }

}