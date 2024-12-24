import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { faHouse, faRightFromBracket, faSearch, faUser, faUsers } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import LogoComponent from "./LogoComponent";

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
        {text: "Search", icon: faSearch, link: '/search'},
        {text: "Logout", icon: faRightFromBracket, link: '/logout'},
    ]

    return (
        <div id="navbar">
            <div className="logo align-center justify-center">
                <LogoComponent/>
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