import { useParams } from 'react-router-dom';
import PostComponent from '../components/post/PostComponent';
import ErrorComponent from '../components/error/ErrorComponent';

const PostRoute = () => {
    const { postID } = useParams();

    return (
        <>  {
            postID ? (
                <PostComponent postID={postID}/>
            ) : (
                <ErrorComponent statusCode={400} message={`Post ID not valid.`}/>
            )

            }
        </>
    );
}

export default PostRoute;