import { useState } from "react";

const AddPostComponent = (props: {onPostAdd: (content: string) => void}) => {

    const [newPost, setNewPost] = useState("");

    const formSubmitHandler = (e: React.FormEvent) => {
        e.preventDefault();
        props.onPostAdd(newPost);
    }

    return (
        <form onSubmit={formSubmitHandler}>
            <input type="text" name="post" placeholder="Add Post" onChange={(e) => setNewPost(e.target.value)} value={newPost}/>
            <button type="submit">Create Post</button>
        </form>
    )

}

export default AddPostComponent;