import { useState } from "react";
import { RegisterUser } from "../../utils/Types";
import { useNavigate } from "react-router-dom";
import axiosUtil from "../../utils/AxiosUtil";
import { useUser } from "../../utils/Context";

const RegisterUserComponent = () => {
    const navigate = useNavigate();
    const { currentUser, setCurrentUser } = useUser();
    // Form control
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    // 

    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const newUser: RegisterUser = {
            username: username,
            email: email,
            password: password,
        }
        
        axiosUtil.post(`/auth/register`, JSON.stringify(newUser)).then(res => {
            const authHeader = res.headers['authorization'];
            if (authHeader) {
                sessionStorage.setItem('token', authHeader.startsWith('Bearer ') ? authHeader.slice(7) : authHeader);
                sessionStorage.setItem('currentUser', JSON.stringify(res.data));
                setCurrentUser(res.data);
                navigate('/home');
                return;
            }
            throw new Error();
        }).catch(err => {
            if (err.status === 409) {
                // TODO: user already exists with that email or username; try logging in.
                console.error('user already exists')
            } else {
                // TODO: generic error
                console.error('something went wrong')
            }
        });
    }

    return (
        <>
        <form onSubmit={onSubmit}>
            <input type="text" name="username" placeholder="username" onChange={(e) => setUsername(e.target.value)}/><br/>
            <input type="email" name="email" placeholder="email" onChange={(e) => setEmail(e.target.value)}/><br/>
            <input type="password" name="password" placeholder="password" minLength={8} onChange={(e) => setPassword(e.target.value)}/><br/>
            <button type="submit">Register</button>
        </form>
        </>
    )
}

export default RegisterUserComponent;