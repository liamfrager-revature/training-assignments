import { useState } from "react";
import SearchBarComponent from "../components/search/SearchBarComponent";
import SearchResultsComponent from "../components/search/SearchResultsComponent";
import { SearchResults } from "../utils/Types";

const SearchRoute = () => {
    const [results, setResults] = useState<SearchResults>();

    return (
        <>
        {/* TODO: implement search feature */}
        <SearchBarComponent setSearchResults={setResults}/>
        <SearchResultsComponent results={results}/>
        </>
    );
}

export default SearchRoute;