import { useEffect, useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { SearchResults } from "../../utils/Types";

const SearchBarComponent = (props: {setSearchResults: (searchResults: SearchResults | undefined) => void}) => {
    const [searchQuery, setSearchQuery] = useState("");
    const setSearchResults = props.setSearchResults;

    useEffect(() => {
        if (searchQuery) {
            axiosUtil.get('/search', {params: {searchQuery: searchQuery}}).then(res => {
                setSearchResults(res.data);
            })
        } else {
            setSearchResults(undefined);
        }
    }, [searchQuery, setSearchResults])

    return (
        <input className="search" type="text" name="searchQuery" value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} placeholder="Search for posts and users..."/>
    )

}

export default SearchBarComponent;