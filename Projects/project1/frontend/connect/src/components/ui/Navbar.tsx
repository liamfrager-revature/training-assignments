import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { faHouse, faUser, faUsers } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";

const Navbar = () => {
    type NavItem = {
        text: string,
        icon: IconProp,
        link: string
    }
    const navItems: NavItem[] = [
        {text: "Home", icon: faHouse, link: '/home'},
        {text: "Profile", icon: faUser, link: '/profile'},
        {text: "Following", icon: faUsers, link: '/following'},
    ]

    return (
        <div id="navbar">
            <div className="logo align-center">
                {/* <img src={`${process.env.PUBLIC_URL}/logo.png`} alt="logo"/> */}
                <h1 className="logo pointer">CONNECT</h1>
            </div>
            <ul>
                {
                navItems.map((navItem, index) => (
                    <Link to={navItem.link} key={index} className="nav-link">
                        <li className="hover nav-link">
                            <FontAwesomeIcon icon={navItem.icon} className="nav-icon space-right" />
                            <span>{navItem.text}</span>
                        </li>
                    </Link>
                ))
                }
            </ul>
        </div>
    )
}

export default Navbar;