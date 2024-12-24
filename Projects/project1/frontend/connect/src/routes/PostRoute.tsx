import { useParams } from 'react-router-dom';
import PostComponent from '../components/post/PostComponent';

const PostRoute = () => {
    const { postID } = useParams();

    return (
        <>  
            {postID && <PostComponent postID={postID}/>}
        </>
    );
}

export default PostRoute;