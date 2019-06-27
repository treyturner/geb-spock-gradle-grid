package search_engine_exercises.spec.google

import util.WebApplicationSpecification
import search_engine_exercises.page.google.GoogleResultsPage
import search_engine_exercises.page.google.GoogleSearchPage

class GoogleSearchSpec extends WebApplicationSpecification {

    def "should load the Google search page"() {

        given: "a user navigates to the Google search page"
        to GoogleSearchPage

        expect: "the Google search page to be shown"
        at GoogleSearchPage
    }

    def "should return search results when performing a search"() {

        given: "a user is at the Google search page"
        at GoogleSearchPage

        when: "they type 'The White House' into the search field"
        searchBox = 'The White House'

        and: "move the mouse to the search button and click"
        // Google protects their search button with javascript,
        // so we must actually move the mouse to the button
        interact {
            moveToElement(searchButton)
            click(searchButton)
        }

        then: "the user is brought to the search results page"
        at GoogleResultsPage

        and: "some number of search results are returned"
        results.size() > 1
    }

    def "should return whitehouse.gov as the first result for a search for 'The White House'"() {

        given: "the user is at the former search result page"
        at GoogleResultsPage

        expect: "the first result to be whitehouse.gov"
        results[0].name.text().contains("The White House")
        results[0].link.@href == "https://www.whitehouse.gov/"

        and: "to hit some basic matchers on the result description"
        results[0].description.text().toLowerCase().contains("america")
    }
}
