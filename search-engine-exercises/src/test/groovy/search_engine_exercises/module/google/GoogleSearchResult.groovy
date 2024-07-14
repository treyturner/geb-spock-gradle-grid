package search_engine_exercises.module.google

import geb.Module

class GoogleSearchResult extends Module {

    static content = {
        name
                { find('h3').first() }
        link
                { find('a').first() }
        description
                { find('[lang][data-hveid][data-ved] > div > div:nth-child(2)') }
    }
}
