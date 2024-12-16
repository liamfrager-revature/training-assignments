import { useState } from "react";
import ToDoDisplay from "./ToDoDisplay";
import ToDoCount from "./ToDoCount";
import ToDoForm from "./ToDoForm";

const ToDoList = () => {
    const [list, setList] = useState([]);

    const addToList = (value) => {
        setList([...list, value]);
    }

    return (
        <>
            <h1>Todo!</h1>
            <ToDoForm list={list} updateList={addToList} />
            <hr />
            <ToDoDisplay list={list} />
            <ToDoCount list={list} />
        </>
    );
}

export default ToDoList;