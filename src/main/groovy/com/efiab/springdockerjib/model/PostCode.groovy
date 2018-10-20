package com.efiab.springdockerjib.model

import com.opencsv.bean.CsvBindByName
import io.vertx.core.json.JsonObject

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class PostCode {
    PostCode() {
    }

    PostCode(Integer id, String postcode, double latitude, double longitude) {
        this.id = id
        this.postcode = postcode
        this.latitude = latitude
        this.longitude = longitude
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getPostcode() {
        return postcode
    }

    void setPostcode(String postcode) {
        this.postcode = postcode
    }

    double getLatitude() {
        return latitude
    }

    void setLatitude(double latitude) {
        this.latitude = latitude
    }

    double getLongitude() {
        return longitude
    }

    void setLongitude(double longitude) {
        this.longitude = longitude
    }

    /**
     * To json obj json object.
     *
     * @return the json object
     */
    JsonObject toJsonObj() {
        return new JsonObject().put("postcode", postcode).put("latitude", latitude).put("longitude", longitude).put("id", id)
    }

    @Override
    String toString() {
        return "" + id + ", " + postcode + ", " + latitude + ", " + longitude
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id
    @CsvBindByName
    @Column(name = "postcode")
    private String postcode
    @CsvBindByName
    @Column(name = "latitude")
    private double latitude
    @CsvBindByName
    @Column(name = "longitude")
    private double longitude
}
