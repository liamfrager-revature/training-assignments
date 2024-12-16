import { useState, useRef, useEffect } from "react";

const ToDoForm = (props) => {
    const [value, setValue] = useState("");
    const inputRef = useRef(null);

    const submitHandler = (e) => {
        e.preventDefault();
        props.updateList(value);
        setValue("");
        inputRef.current.focus();
    }

    useEffect(() => inputRef.current.focus(), [])

    return (
        <form onSubmit={submitHandler}>
            <input ref={inputRef} type="text" value={value} onChange={(e) => setValue(e.target.value)} />
            <button type="submit">Add</button>
        </form>
    )
}

export default ToDoForm;