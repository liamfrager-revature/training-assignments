function handleClick() {
    elements = document.getElementsByTagName("p")
    for (elem of elements) {
        elem.style.backgroundColor = "red"
    }
}

function addElement() {
    const paras = document.getElementsByTagName("p")
    let newElem = document.createElement("p")
    newElem.innerHTML = `Para ${paras.length + 1}`
    document.getElementById("paras").append(newElem)
}