import { useState } from "react";

const AddCommentComponent = (props: {onCommentAdd: (content: string) => void}) => {

    const [newComment, setNewComment] = useState("");

    const formSubmitHandler = (e: React.FormEvent) => {
        e.preventDefault();
        setNewComment("");
        props.onCommentAdd(newComment);
    }

    return (
        <form onSubmit={formSubmitHandler} className="justify-between">
            <input id="add-comment" type="text" name="comment" placeholder="Add Comment" className="space-right" onChange={(e) => setNewComment(e.target.value)} value={newComment}/>
            <button type="submit">Post Comment</button>
        </form>
    )

}

export default AddCommentComponent;