package search_engine_exercises.page.google

import geb.Page
import geb.navigator.Navigator

class GoogleSearchPage extends Page {

    static url = "https://google.com"

    static atCheckWaiting = 'short'

    static at = {
        title == "Google"
        searchBox
    }

    static content = {
        searchBox
                { $(name: 'q') }
        searchButton (to: GoogleResultsPage)
                { $('input', name: 'btnK').first() }
    }

    Navigator search(String text) {
        searchBox.value(text)
        sleep 1500
        interact {
            moveToElement(searchButton)
        }
        searchButton.click()
    }
}
