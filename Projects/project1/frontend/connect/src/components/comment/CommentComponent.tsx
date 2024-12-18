import { Comment } from "../../utils/Types";
import Metadata from "../Metadata";
import UsernameComponent from "../user/UsernameComponent";

const CommentComponent = (props: {comment: Comment}) => {
    const comment = props.comment
    const user = comment.user

    const onCommentLike = () => {

    }

    return (
        <div>
            {user.pfp ? <img src={URL.createObjectURL(user.pfp)} alt={user.username + " profile picture"} /> : null}
            <div className="comment-text">
                <UsernameComponent user={user}/>
                <span>{comment.content}</span>
                <br />
                <Metadata
                    timestamp={comment.timestamp}
                    likeCount={comment.likeCount}
                    onLike={onCommentLike}
                />
            </div>
        </div>
    )
}

export default CommentComponent;