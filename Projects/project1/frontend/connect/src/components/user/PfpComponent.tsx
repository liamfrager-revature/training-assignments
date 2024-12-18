import { useState } from "react";

const PfpComponent = (props: {pfp?: Blob}) => {

    const [pfpImage, setPfpImage] = useState(props.pfp !== undefined ? URL.createObjectURL(props.pfp) : "https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg");

    return (
        <img src={pfpImage} alt="profile picture" />
    )
}

export default PfpComponent;