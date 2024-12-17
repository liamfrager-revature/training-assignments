import { useState } from "react";
import { AuthenticateUser } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import { useNavigate } from "react-router-dom";

const AuthenticateUserComponent = () => {
    const navigate = useNavigate();
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
        axiosUtil.post('/auth/login', JSON.stringify(authUser)).then(res => {
            const authHeader = res.headers['authorization'];
            if (authHeader) {
                sessionStorage.setItem('token', authHeader.startsWith('Bearer ') ? authHeader.slice(7) : authHeader);
                navigate('/home');
                return;
            }
            throw new Error();
        }).catch(err => {
            if (err.status === 401) {
                // TODO: invalid login
                console.error('invalid login')
            } else {
                // TODO: generic error
                console.error('something went wrong')
            }
        });
    }

    return (
        <>
        <form onSubmit={onSubmit}>
            <input type="text" name="usernameOrEmail" placeholder="username/email" onChange={(e) => setUsernameOrEmail(e.target.value)}/><br/>
            <input type="password" name="password" placeholder="password" onChange={(e) => setPassword(e.target.value)}/><br/>
            <button type="submit">Login</button>
        </form>
        </>
    )
}

export default AuthenticateUserComponent;