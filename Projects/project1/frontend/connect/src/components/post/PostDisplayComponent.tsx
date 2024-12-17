import { Post } from "../../utils/Types";

const PostDisplayComponent = (props: {post: Post}) => {
    const post = props.post;
    return (
        <>
        <span>{post.user.username}</span>
        <div>
            <img src="" alt="" />
        </div>
        </>
    )
}

export default PostDisplayComponent;