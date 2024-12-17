import { useContext } from "react";
import withFancyText from "./withFancyText";
import Context from "../Context";

const TextComponent = () => {
    const data = useContext(Context);
    return (
        <p>{data}</p>
    )
}

export default withFancyText(TextComponent);