package com.efiab.springdockerjib

import com.efiab.springdockerjib.model.PostCode
import com.efiab.springdockerjib.utils.DistanceCalc
import groovyx.net.http.RESTClient
import org.apache.logging.log4j.LogManager
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.*

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

@Stepwise
@SpringBootTest
class SpringdockerjibApplicationTests extends Specification {

    @Shared
    def LOGGER = LogManager.getLogger("SPRINGDOCKERJIBAPPLICATIONTESTS")
    @Shared
    def testURL = "http://localhost:8080"
    @Shared
    def client = new RESTClient(testURL)

    static PostCode p1
    static PostCode p2
    static plist = []

    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

    void setupSpec() {
        Future future = Executors
                .newSingleThreadExecutor().submit(
                new Callable() {
                    @Override
                    ConfigurableApplicationContext call() throws Exception {
                        return (ConfigurableApplicationContext) SpringApplication
                                .run(SpringdockerjibApplication.class)
                    }
                })
        context = future.get(60, TimeUnit.SECONDS)
        println("ConfigurableApplicationContext setup as " + context)

        plist << new PostCode(1, 'AB10', 57.13514, -2.11731)
        plist << new PostCode(2, 'AB11', 57.13875, -2.09089)
        plist << new PostCode(3, 'AB12', 57.10100, -2.11060)
        plist << new PostCode(4, 'AB13', 57.10801, -2.23776)
        plist << new PostCode(5, 'AB14', 57.10076, -2.27073)
        plist << new PostCode(6, 'AB15', 57.13868, -2.16525)
        plist << new PostCode(7, 'AB16', 57.16115, -2.15543)
        plist << new PostCode(8, 'AB21', 57.20960, -2.20033)
        plist << new PostCode(9, 'AB22', 57.18724, -2.11913)
        plist << new PostCode(10, 'AB23', 57.21242, -2.08776)
    }


    def "testing if Spring-Spock-Groovy-Geb testing loads"() {
        expect:
        true
    }

    def "index.html Page works"() {
        when: "get index Page"
        def response = client.get(
                path: '/'
        )

        then: "Status is 200"
        response.status == 200
    }


    def "Swagger2-ui Page shows"() {
        when: "get Swagger2 Page"
        def response = client.get(
                path: '/'
        )

        then: "Status is 200"
        response.status == 200
    }


    def "Purge then reload DB"() {
        when: "reload db"
        def response = client.get(
                path: '/api/postcodes/reload'
        )

        then: "Status is 200"
        response.status == 200

    }

    //test with Spring RestTemplate
    def "Spring RestTemplate - load index page"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:8080", String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.contains('Just a simple CRUD page based on the RestAPI(SpringBoot)')
    }

    //test with Groovy RestClient
    def "Groovy RestClient - get all postcodes"() {

        when: "get all postcodes"
        def response = client.get(
                path: '/api/postcodes/all'
        )

        then: "Status is 200"
        response.status == 200

        and: "data contains many proper values"
        assert response.data[0].id >= 1
        assert response.data[0].postcode != ""
        assert response.data[0].latitude != 0
        assert response.data[0].longitude != 0
        assert response.data.size >= 200
    }

    //complete test case, create, update, getone and delete
    def "Create a new UK postcode from client"() {
        when: "prepare a new PostCode and post and get response"
        def resp = client.post(path: "/api/postcodes/save",
                contentType: "application/json",
                requestContentType: "application/json",
                body: [postcode : "XXXXX",
                       latitude : 12345,
                       longitude: 78901
                ]
        )

        assert resp.status == 200
        assert resp.contentType == "application/json"
        p1 = resp.data
        LOGGER.debug(p1.toString())

        then: "it is saved"
        assert p1 != null
        assert p1.id > 0
        assert p1.postcode == "XXXXX"
        assert p1.latitude == 12345
        assert p1.longitude == 78901
    }

    def "verify newly created postcode by query it"() {
        when: "query it by rest"
        println p1.toString()
        assert p1 != null
        def resp = client.get(path: "/api/postcodes/" + p1.getId())
        assert resp.status == 200
        assert resp.contentType == "application/json"
        def postcode = resp.data

        then: "confirm it is the same"
        assert postcode.id == p1.getId()
        assert postcode.postcode == p1.postcode
        assert postcode.latitude == p1.latitude
        assert postcode.longitude == p1.longitude
    }

    def "update postcode p1 with rest"() {
        when: "update a postcode"
        assert p1 != null
        p2 = p1 //old value
        def resp = client.put(path: "/api/postcodes/" + p1.getId(),

                contentType: "application/json",
                requestContentType: "application/json",
                body: [postcode : "YYYYY",
                       latitude : p1.latitude++,
                       longitude: p1.longitude++
                ]

        )
        assert resp.status == 200
        assert resp.contentType == "application/json"
        def postcode = resp.data

        and: "query it back to confirm"

        assert p1 != null
        def resp2 = client.get(path: "/api/postcodes/" + p1.getId())
        assert resp2.status == 200
        assert resp2.contentType == "application/json"
        p1 = resp2.data

        then: "assure it is updated"
        assert postcode.id == p1.id
        assert postcode.postcode == p1.postcode
        assert postcode.latitude == p1.latitude
        assert postcode.longitude == p1.longitude
    }

    def "delete user with rest"() {
        when:
        def resp = client.delete(path: "/api/postcodes/" + p1.getId())
        then:
        assert resp.status == 200  // done
    }

    /**
     * some test data
     * pList:
     * 1,AB10,57.13514,-2.11731
     2,AB11,57.13875,-2.09089
     3,AB12,57.10100,-2.11060
     4,AB13,57.10801,-2.23776
     5,AB14,57.10076,-2.27073
     6,AB15,57.13868,-2.16525
     8,AB16,57.16115,-2.15543
     9,AB21,57.20960,-2.20033
     10,AB22,57.18724,-2.11913
     11,AB23,57.21242,-2.08776
     * @return
     */
    @Unroll
    def "calculate #distance between 2 postcode #pid1 #pid2 with this rest"() {
        when: "take 2 postcodes, sent request to calculate distance between them"
        def resp = client.get(path: "/api/postcodes/calc/" + pid1 + "/" + pid2,
                requestContentType: "application/json"
        )

        then: "response is json contains the result"
        assert resp.status == 200
        assert resp.contentType == "application/json"
        def pcDistance = resp.data
        LOGGER.debug(resp.data)


        expect: "the calculation is correct"

        pcDistance != null
        pcDistance.postCode1 != null
        pcDistance.postCode2 != null
        pcDistance.distance == distance

        where:

        pid1 || pid2 || distance
        1    || 2    || DistanceCalc.calculateDistance(plist[0], plist[1]).distance
        3    || 4    || DistanceCalc.calculateDistance(plist[2], plist[3]).distance
        5    || 6    || DistanceCalc.calculateDistance(plist[4], plist[5]).distance
        7    || 8    || DistanceCalc.calculateDistance(plist[6], plist[7]).distance
        9    || 10   || DistanceCalc.calculateDistance(plist[8], plist[9]).distance

    }

}
