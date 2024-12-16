import { Link } from "react-router-dom"

const Navbar = () => {
    return (
        <div className="navbar">
            <h2>React Demo</h2>
            <div className="links">
                <Link to="/hello">Hello</Link>
                <Link to="/todo">Todo</Link>
                <Link to="/posts">Posts</Link>
            </div>
        </div>
    )
}

export default Navbar;