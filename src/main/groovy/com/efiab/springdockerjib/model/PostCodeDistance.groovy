package com.efiab.springdockerjib.model

import io.vertx.core.json.JsonObject

/**
 * PostCodeDistance Entity
 *
 * @author SebastianX
 * @version 1.0
 *     <p>For both locations, the postal code, latiude and longitude (both in degrees); The distance
 *     between the two locations (in kilometers); A fixed string 'unit' that has the value "km";
 */
class PostCodeDistance {

    private PostCode postCode1
    private PostCode postCode2
    private double distance
    private static final String unit = "km"

    /**
     * Instantiates a new Post code distence.
     *
     * @param postCode1 the post code 1
     * @param postCode2 the post code 2
     * @param distance the distance
     */
    PostCodeDistance(PostCode postCode1, PostCode postCode2, Double distance) {
        this.postCode1 = postCode1
        this.postCode2 = postCode2
        this.distance = distance
    }

    /** Instantiates a new Post code distence. */
    PostCodeDistance() {}

    /**
     * Gets post code 1.
     *
     * @return the post code 1
     */
    PostCode getPostCode1() {
        return postCode1
    }

    /**
     * Sets post code 1.
     *
     * @param postCode1 the post code 1
     */
    void setPostCode1(PostCode postCode1) {
        this.postCode1 = postCode1
    }

    /**
     * Gets post code 2.
     *
     * @return the post code 2
     */
    PostCode getPostCode2() {
        return postCode2
    }

    /**
     * Sets post code 2.
     *
     * @param postCode2 the post code 2
     */
    void setPostCode2(PostCode postCode2) {
        this.postCode2 = postCode2
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    Double getDistance() {
        return distance
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    void setDistance(Double distance) {
        this.distance = distance
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    String getUnit() {
        return unit
    }

    /**
     * To json obj json object.
     *
     * @return the json object
     */
    JsonObject toJsonObj() {
        return new JsonObject()
                .put("postCode1", postCode1.toJsonObj())
                .put("postCode2", postCode2.toJsonObj())
                .put("distance", distance)
                .put("unit", unit)
    }
}
