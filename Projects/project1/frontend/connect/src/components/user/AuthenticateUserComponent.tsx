import axios from "axios";
import { useState } from "react";
import { AuthenticateUser, User } from "../../types/Types";

const AuthenticateUserComponent = () => {
    const URL = process.env.REACT_APP_DB_API_URL;
    const [usernameOrEmail, setUsernameOrEmail] = useState("");
    const [password, setPassword] = useState("");

    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const isEmail = /\S+@\S+\.\S+/.test(usernameOrEmail);

        const authUser: AuthenticateUser = isEmail ? {
            email: usernameOrEmail,
            password: password,
        } : {
            username: usernameOrEmail,
            password: password,
        }
        const config = {
            headers: {
                'Content-Type': 'application/json'
            }
        }
        axios.post(`${URL}/auth/login`, JSON.stringify(authUser), config).then(res => console.log("user logged in", res));
    }

    return (
        <>
        <form onSubmit={onSubmit}>
            <input type="text" name="usernameOrEmail" placeholder="username/email" onChange={(e) => setUsernameOrEmail(e.target.value)}/><br/>
            <input type="password" name="password" placeholder="password" onChange={(e) => setPassword(e.target.value)}/><br/>
            <button type="submit">Register</button>
        </form>
        </>
    )
}

export default AuthenticateUserComponent;