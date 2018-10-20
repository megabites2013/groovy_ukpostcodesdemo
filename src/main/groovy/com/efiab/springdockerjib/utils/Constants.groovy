package com.efiab.springdockerjib.utils

class Constants {

    public static final double EARTH_RADIUS = 6371 // radius in kilometers

    public static final String THE1PAGE = "Java test challenge:\n" +
            "\n" +
            "Write a REST (-y) service that will return the geographic (straight line) distance between two postal codes in the UK.\n" +
            "\n" +
            "Arguments to a request are two UK postal codes (you may decide how these arguments are provided).\n" +
            "\n" +
            "Result to a valid request must be a json document that contains the following information:\n" +
            "\n" +
            "    For both locations, the postal code, latiude and longitude (both in degrees);\n" +
            "\n" +
            "    The distance between the two locations (in kilometers);\n" +
            "\n" +
            "    A fixed string 'unit' that has the value \"km\";\n" +
            "\n" +
            "For postal codes lookup: use the following data.\n" +
            "\n" +
            "http://www.freemaptools.com/download-uk-postcode-lat-lng.htm;\n" +
            "\n" +
            "http://www.freemaptools.com/download/full-postcodes/postcodes.zip;"


    public static final String FILE_POSTCODE_OUTCODES_CSV = "postcode-outcodes.csv"
    public static final String COLLECTION = "postcodes"

    public static final String UKPCODE = "UKPOSTCODE"

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8"
    public static final String CONTENT_TYPE = "content-type"
    public static final int MAGIC_TWO = 2

    public static final String CONNECTION_STRING = "connection_string"
    public static final String DB_NAME = "db_name"
    public static final String COL_POSTCODES_TEST = "postcodes-test"
    public static final String MONGODB_LOCALHOST_27017 = "mongodb://localhost:27017"
}
