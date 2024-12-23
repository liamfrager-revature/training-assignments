import { useState } from "react";
import ImageUploader from "./ImageUploader";
import axiosUtil from "../../utils/AxiosUtil";
import { NewPost, Post } from "../../utils/Types";

const AddPostComponent = (props: {onPostAdd: (addedPost: Post) => void}) => {

    const [newContent, setNewContent] = useState("");
    const [newAttachment, setNewAttachment] = useState<string>();

    const formSubmitHandler = (e: React.FormEvent) => {
        e.preventDefault();

        const newPost: NewPost = {
            content: newContent,
            attachment: newAttachment
        }
        axiosUtil.post('/posts', JSON.stringify(newPost)).then(res => {
            setNewContent("");
            props.onPostAdd(res.data);
        })
    }

    return (
        <form onSubmit={formSubmitHandler} className="justify-between add-post">
            <input type="text" name="post" placeholder="Add Post" onChange={(e) => setNewContent(e.target.value)} value={newContent}/>
            <span className="justify-between">
                <ImageUploader setImage={setNewAttachment}/>
                <button type="submit">Create Post</button>
            </span>
        </form>
    )

}

export default AddPostComponent;