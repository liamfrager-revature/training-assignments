const ToDoDisplay = (props) => {
    return (
        <ul>
            {
                props.list.map((listItem, index) => (<li key={index}>{listItem}</li>))
            }
        </ul>
    );
}

export default ToDoDisplay;