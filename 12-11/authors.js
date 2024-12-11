function showAuthors() {
    fetch("https://jsonplaceholder.typicode.com/users")
        .then(res => {
            res.json().then(data => {
                makeList(data);
            });
        })
        .catch(err => {
            console.error(err);
        });
}

async function showAuthorsAsync() {
    res = await fetch("https://jsonplaceholder.typicode.com/users")
    data = await res.json()
    makeList(data);
}

function makeList(data) {
    document.getElementById("button").remove();
    const list = document.createElement("ul");
    list.id = "list";
    data.map(author => {
        const newAuthor = document.createElement("li");
        const authorName = document.createElement("strong");
        authorName.innerHTML = author.name + ", ";
        newAuthor.append(authorName);
        const emailAnchor = document.createElement("a")
        emailAnchor.href = `mailto:${author.email}`;
        const authorEmail = document.createElement("i");
        authorEmail.innerHTML = author.email;
        emailAnchor.append(authorEmail);
        newAuthor.append(emailAnchor);
        list.append(newAuthor);
    })
    document.getElementsByTagName("body")[0].append(list);
}