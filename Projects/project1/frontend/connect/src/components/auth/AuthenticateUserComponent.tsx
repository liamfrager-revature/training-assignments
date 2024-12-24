import { useEffect, useRef, useState } from "react";
import { AuthenticateUser } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import { useNavigate } from "react-router-dom";
import { useUser } from "../../utils/Context";
import ErrorComponent from "../error/ErrorComponent";

const AuthenticateUserComponent = () => {
    const navigate = useNavigate();
    const { currentUser, setCurrentUser } = useUser();
    const inputRef = useRef<HTMLInputElement>(null);
    const [usernameOrEmail, setUsernameOrEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errorLoggingIn, setErrorLoggingIn] = useState(false);
    const [errorMessage, setErrorMessage] = useState("Something went wrong, please try again.");

    useEffect(() => {
        inputRef.current?.focus();
        if (currentUser)
            navigate('/home');
    }, [currentUser, navigate])

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
                sessionStorage.setItem('currentUser', JSON.stringify(res.data));
                setCurrentUser(res.data);
                return;
            }
            throw new Error();
        }).catch(err => {
            if (err.status === 401) {
                setErrorMessage("There is no account with that username/email and password.");
                setErrorLoggingIn(true);
            } else {
                setErrorLoggingIn(true);
            }
        });
    }

    return (
        <>
        {errorLoggingIn && <ErrorComponent message={errorMessage}/>}
        <form onSubmit={onSubmit} className="auth-form">
            <input ref={inputRef} type="text" name="usernameOrEmail" placeholder="username/email" onChange={(e) => setUsernameOrEmail(e.target.value)}/><br/>
            <input type="password" name="password" placeholder="password" onChange={(e) => setPassword(e.target.value)}/><br/>
            <button type="submit">Login</button>
        </form>
        </>
    )
}

export default AuthenticateUserComponent;