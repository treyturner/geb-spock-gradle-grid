package util

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j

@Slf4j
class Util {

    /**
     * Attempts to resolve the local operating system into a string.
     * Useful when configuring a local WebDriver instance.
     *
     * @return  one of 'windows64', 'windows32', 'osx', 'linux64', and 'linux32'
     */
    static String getPlatform() {
        def ret = null,
            platform = System.getProperty('os.name'),
            architecture = System.getProperty('os.arch')
        log.trace "Detected platform: ${platform} (${architecture})"
        if (platform.toLowerCase().contains('windows')) {
            if (System.getenv("ProgramFiles(x86)") != null) {
                ret = 'windows64'
            } else {
                ret = 'windows32'
            }
        } else if (platform.toLowerCase().contains('os x')) {
            ret = 'osx'
        } else if (platform == 'Linux') {
            switch (architecture) {
                case 'amd64':
                    ret = 'linux64'
                    break
                default:
                    ret = 'linux32'
                    break
            }
        }
        ret
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
