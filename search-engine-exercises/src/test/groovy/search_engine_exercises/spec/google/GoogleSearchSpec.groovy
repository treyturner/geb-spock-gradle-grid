package search_engine_exercises.spec.google

import util.WebApplicationSpecification
import search_engine_exercises.page.google.GoogleResultsPage
import search_engine_exercises.page.google.GoogleSearchPage

class GoogleSearchSpec extends WebApplicationSpecification {

    def "should load the Google search page"() {

        given: "a user navigates to the Google search page"
        GoogleSearchPage search = to GoogleSearchPage

        expect: "the Google search page to be shown"
        at search
    }

    def "should return search results when performing a search"() {

        given: "a user navigates to the Google search page"
        GoogleSearchPage search = to GoogleSearchPage

        when: "searching for the White House"
        // Facebook and Youtube often outrank whitehouse.gov
        // without being a bit specific
        search.search 'the white house official website'

        then: "results are returned"
        GoogleResultsPage results = at GoogleResultsPage
        results.results.size() > 1

        and: "the first result should be whitehouse.gov"
        results.results[0].name.text().contains("White House")
        results.results[0].link.@href == "https://www.whitehouse.gov/"

        and: "a basic match can be made against the description"
        results.results[0].description.text().toLowerCase().contains("president")

        and: "the second result should be usa.gov"
        results.results[1].name.text().contains("USAGov")
        results.results[1].link.@href == "https://www.usa.gov/agencies/white-house"

        and: "a basic match can be made against the description"
        results.results[1].description.text().toLowerCase().contains("white house")
    }
}
