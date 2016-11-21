package util

import groovy.json.JsonBuilder

class Util {

    /**
     * Attempts to resolve the local operating system into a string.
     * Useful when configuring a local WebDriver instance.
     *
     * @return  one of 'windows64', 'windows32', 'osx', 'linux64', and 'linux32'
     */
    static String getPlatform() {
        def platform = System.getProperty('os.name')
        if (platform.toLowerCase().contains('windows')) {
            if (System.getenv("ProgramFiles(x86)") != null) {
                return 'windows64'
            } else {
                return 'windows32'
            }
        } else if (platform.toLowerCase().contains('os x'))
            return 'osx'
        else if (platform.contains('64'))
            return 'linux64'
        else {
            return 'linux32'
        }
    }

    /**
     * Return a Groovy object as pretty printed JSON String.
     *
     * @param obj   The object to be represented as a pretty printed JSON string
     * @return      A String representation of an Object in pretty printed JSON format
     */
    static String prettyPrint(Object obj) {
        new JsonBuilder(obj).toPrettyString()
    }
}
