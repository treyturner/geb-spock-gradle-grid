package info.treyturner.qa.demo.util

import groovy.json.JsonBuilder

class Util {

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

    static String prettyPrint(Object obj) {
        new JsonBuilder(obj).toPrettyString()
    }
}
