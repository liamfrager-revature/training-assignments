import { Comment } from "../types/Types";

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

    return (
        <div>
            <img src={URL.createObjectURL(user.pfp)} alt={user.name + " profile picture"} />
            <div className="comment-text">
                <span>{user.name}</span>
                <span>{comment.content}</span>
                <br />
                <span>{comment.timestamp}</span>
                <span>❤️🤍</span><span>{formatLikes(comment.likesCount)}</span>
            </div>
        </div>
    )
}

export default CommentComponent;