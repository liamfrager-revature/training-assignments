import { useState } from "react";

const PfpComponent = (props: {pfp?: string}) => {

    const [pfpImage] = useState(props.pfp !== undefined ? `data:image/png;base64,${props.pfp}` : "https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg");

    return (
        <img className="pfp" src={pfpImage} alt="profile" />
    )
}

export default PfpComponent;