package info.treyturner.qa.demo.google_search.spec

import info.treyturner.qa.demo.google_search.WebApplicationSpecification
import info.treyturner.qa.demo.google_search.page.GoogleResultsPage
import info.treyturner.qa.demo.google_search.page.GoogleSearchPage

class GoogleSearchSpec extends WebApplicationSpecification {

    def "should load the Google search page"() {

        given: "the user navigates to the Google search page"
        to GoogleSearchPage

        expect: "the Google search page to be shown"
        at GoogleSearchPage
    }

    def "should return search results when performing a search"() {

        given: "the user is at the Google search page"
        at GoogleSearchPage

        when: "the user types 'The White House' into the search field"
        searchBox = 'The White House'

        and: "the user clicks the search button"
        searchButton.click()

        then: "the user is brought to the search results page"
        at GoogleResultsPage

        and: "some number of search results are returned"
        results.size() > 1
    }

    def "should return whitehouse.gov as the first result for a search for 'The White House'"() {

        given: "the user is at the former search result page"
        at GoogleResultsPage

        expect: "the first result to be for civitaslearning.com"
        results[0].name.text() == "The White House | whitehouse.gov"
        results[0].link.@href == "https://www.whitehouse.gov/"
        results[0].description.text() == "Official White House site presents issue positions, news, Cabinet, " +
                "appointments, offices and major speeches. Includes biography, video tour and photo essays."
    }
}
