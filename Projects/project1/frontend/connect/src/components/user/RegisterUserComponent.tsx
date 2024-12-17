import axios from "axios";
import { useState } from "react";
import { User } from "../../types/Types";

const RegisterUserComponent = () => {
    const URL = process.env.REACT_APP_DB_API_URL;
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const newUser: User = {
            username: username,
            email: email,
            password: password,
        }
        const config = {
            headers: {
                'Content-Type': 'application/json'
            }
        }
        axios.post(`${URL}/users`, JSON.stringify(newUser), config).then(res => console.log("user created", res.data));
    }

    return (
        <>
        <form onSubmit={onSubmit}>
            <input type="text" name="username" placeholder="username" onChange={(e) => setUsername(e.target.value)}/><br/>
            <input type="email" name="email" placeholder="email" onChange={(e) => setEmail(e.target.value)}/><br/>
            <input type="password" name="password" placeholder="password" onChange={(e) => setPassword(e.target.value)}/><br/>
            <button type="submit">Register</button>
        </form>
        </>
    )
}

export default RegisterUserComponent;