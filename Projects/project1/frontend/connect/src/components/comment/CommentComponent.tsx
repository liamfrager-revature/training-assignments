import { Comment } from "../../utils/Types";
import Metadata from "../Metadata";

const CommentComponent = (props: {comment: Comment}) => {
    const comment = props.comment
    const user = comment.user

    const formatLikes = (likes: number) => {
        const suffixes = ['K', 'M', 'B', 'T'];
        let i = -1;
        while (likes >= 1000 && i < suffixes.length - 1) {
            likes /= 1000;
            i++;
        }
        return `${likes.toFixed(1)}${suffixes[i] || ''}`;
    }

    const onCommentLike = () => {

    }

    return (
        <div>
            {user.pfp ? <img src={URL.createObjectURL(user.pfp)} alt={user.username + " profile picture"} /> : null}
            <div className="comment-text">
                <span className="bold space-right">{user.username}</span>
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