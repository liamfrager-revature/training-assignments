import { useEffect, useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { SearchResults } from "../../utils/Types";

const SearchBarComponent = (props: {setSearchResults: (searchResults: SearchResults) => void}) => {
    const [searchQuery, setSearchQuery] = useState("");

    useEffect(() => {
        if (searchQuery) {
            axiosUtil.get('/search', {params: {searchQuery: searchQuery}}).then(res => {
                props.setSearchResults(res.data);
            })
        }
    }, [searchQuery, props])

    return (
        <>
        <input style={{width: "100%"}} type="text" name="searchQuery" value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} placeholder="Search for posts and users..."/>
        </>
    )

}

export default SearchBarComponent;