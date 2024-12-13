import { useState } from "react";

const StateDemo = () => {
    // let name = "Jasdhir"
    const [name, setName] = useState("Liam");
    const changeName = (newName) => {
        setName(newName);
        console.log("Name changed to '" + newName + "'");
    }
    return (
        <>
            <h2>{name}</h2>
            <button onClick={() => changeName("Frager")}>Change Name</button>
        </>
    )
}

export default StateDemo;