package util

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j

@Slf4j
class Util {

    /**
     * Pretty print a Groovy object as a JSON String.
     * @param obj   The object to be represented as a pretty printed JSON string
     * @return      A String representation of an Object in pretty printed JSON format
     */
    static String pp(Object obj) {
        new JsonBuilder(obj).toPrettyString()
    }
}
