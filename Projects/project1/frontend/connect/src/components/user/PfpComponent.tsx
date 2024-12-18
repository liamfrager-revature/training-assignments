import { useState } from "react";

const PfpComponent = (props: {pfp?: Blob}) => {

    const [pfpImage] = useState(props.pfp !== undefined ? URL.createObjectURL(props.pfp) : "https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg");

    return (
        <img className="pfp" src={pfpImage} alt="profile" />
    )
}

export default PfpComponent;