import { useState } from "react";

const UpdatingText = () => {
    const [value, setValue] = useState("Hello, updating text!");
    return (
        <>
            <input type="text" value={value} onChange={(e) => setValue(e.target.value)} />
            <p>{value}</p>
        </>
    )
}

export default UpdatingText;