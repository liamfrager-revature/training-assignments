import { useState } from "react";

const AddCommentComponent = (props: {onCommentAdd: (content: string) => void}) => {

    const [newComment, setNewComment] = useState("");

    const formSubmitHandler = (e: React.FormEvent) => {
        e.preventDefault();
        props.onCommentAdd(newComment);
    }

    return (
        <form onSubmit={formSubmitHandler}>
            <input type="text" name="comment" placeholder="Add Comment" onChange={(e) => setNewComment(e.target.value)} value={newComment}/>
            <button type="submit">Post Comment</button>
        </form>
    )

}

export default AddCommentComponent;