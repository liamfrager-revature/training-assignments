import { useState } from "react";
import SearchBarComponent from "../components/search/SearchBarComponent";
import SearchResultsComponent from "../components/search/SearchResultsComponent";
import { SearchResults } from "../utils/Types";

const SearchRoute = () => {
    const [results, setResults] = useState<SearchResults>();

    return (
        <>
            <div className="sticky">
                <h1 className="page-heading">Search</h1>
                <SearchBarComponent setSearchResults={setResults}/>
            </div>
            <SearchResultsComponent results={results}/>
        </>
    );
}

export default SearchRoute;