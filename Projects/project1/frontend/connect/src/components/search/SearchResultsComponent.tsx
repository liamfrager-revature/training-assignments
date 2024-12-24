import { SearchResults } from "../../utils/Types";
import PostDisplayComponent from "../post/PostDisplayComponent";
import UserDisplayComponent from "../user/UserDisplayComponent";

const SearchResultsComponent = (props: {results: SearchResults | undefined}) => {
    return (
        <>
        { props.results &&
            <>
            { props.results.users.length > 0 &&
            <>
                <h2>Users</h2>
                {props.results.users.map(user => <UserDisplayComponent user={user}/>)}
            </>
            }
            { props.results.posts.length > 0 &&
            <>
                <h2>Posts</h2>
                {props.results.posts.map(post => <PostDisplayComponent post={post}/>)}
            </>
            }
            </>
        }
        </>
    )
}

export default SearchResultsComponent;