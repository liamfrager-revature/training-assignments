import { useState } from "react";
import { User } from "../../../utils/Types";
import ImageUploader from "../../post/ImageUploader";


const EditProfileComponent = (props: {user: User}) => {
    const [pfp, setPfp] = useState(props.user.pfp);
    return (
        <form>
            <ImageUploader setImage={setPfp}/>
        </form>
    )
}

export default EditProfileComponent;