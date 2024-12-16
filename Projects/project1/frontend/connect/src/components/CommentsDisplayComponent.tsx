import CommentComponent from "./CommentComponent";
import { Comment } from "../types/Types"

const CommentsDisplayComponent = (props: {comments: Array<Comment>}) => {

    return (
        <>
        <h3>Comments</h3>
        {
            props.comments.map((comment: Comment) => (
                <CommentComponent key={comment.id} comment={comment} />
            ))
        }
        </>
    )
}

export default CommentsDisplayComponent;