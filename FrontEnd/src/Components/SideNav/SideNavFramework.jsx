import React from "react";
import classes from './SideNavFramework.module.css';
import LogoTag from "../LogoTag/LogoTag";
import SirioButton from "../Button/SirioButton";
import SirioDropdown from "../Dropdown/SirioDropdown";
import SirioDropdownItem from "../Dropdown/SirioDropdownItem";
import AuthenticationService from "../../Services/AuthenticationService";
import { NavLink } from "react-router-dom";
import { logout, login, changePassword } from "../../Configuration/UrlConfig";

/**
 * Komponen Side Nav secara General
 * 
 * Props yang tersedia:
 * - classes                : cssClass, kelas custom untuk SideNav
 * - links                  : list of JSON, kumpulan informasi objek navigasi
 * - links.title            : String, nama item navigasi
 * - links.link             : String, url tujuan navigasi
 * - links.dropdown         : List of Json, kumpulan informasi objek dropdown navigasi
 * - links.dropdown.title   : String, nama item navigasi dalam dropdown
 * - links.dropdown.link    : String, url tujuan navigasi dalam dropdown
 */
export default class SideNavFramework extends React.Component {

    constructor(props) {
        super(props);

        // Mendapatkan informasi username dan role dari Authentication Service
        this.state = {
            username: AuthenticationService.getUsername(),
            role: AuthenticationService.getRole(),
            shown: false
        }
        this.toggleNav = this.toggleNav.bind(this);
    }

    // Fungsi untuk melakukan navigasi
    onSelect(event) {
        window.location.href = event.value;
    }

    toggleNav() {
        const prev = this.state.shown;
        this.setState({ shown: !prev });
        this.props.toggleNav()
    }

    // Fungsi untuk render SideNav
    render() {
        var logButton;
        var changePasswordButton;
        if (AuthenticationService.isLoggedIn()) {
            logButton =
                <SirioButton
                    blue
                    recommended
                    circular
                    classes={classes.footerButton}
                    onClick={() => window.location.href = logout.link}
                >
                    {logout.title}
                </SirioButton>;
            changePasswordButton =
                <SirioButton
                    purple
                    circular
                    classes={classes.footerButton2}
                    onClick={() => window.location.href = changePassword.link}
                >
                    {changePassword.title}
                </SirioButton>
        } else {
            logButton =
                <SirioButton
                    blue
                    recommended
                    circular
                    classes={classes.footerButton}
                    onClick={() => window.location.href = login.link}
                >
                    {login.title}
                </SirioButton>
        }

        var className = [this.props.classes, classes.sidebar].join(" ");
        if (this.props.shouldShow) {
            className = [className, classes.show].join(" ");
        }
        return (
            <>
                <nav className={className}>
                    <LogoTag light />
                    <div className={classes.identity}>
                        <h3 className={classes.username}>
                            {this.state.username}
                        </h3>
                        <p className={classes.role}>
                            {this.state.role}
                        </p>
                    </div>
                    <div className={classes.navigationContainer}>
                        {this.props.links.map((menu, i) =>
                            menu.dropdown ?
                                <SirioDropdown
                                    key={i}
                                    headerClass={classes.clickableNavigation}
                                    headerTitle={menu.title}
                                    activeClass={classes.active}
                                    menuClass={classes.dropdownMenu}
                                >
                                    {menu.dropdown.map((item, i) =>
                                        <SirioDropdownItem
                                            key={i}
                                            classes={classes.dropdownItem}
                                            onClick={(object) => window.location.href = object}
                                            clickArgument={item.link}
                                        >
                                            {item.title}
                                        </SirioDropdownItem>
                                    )}
                                </SirioDropdown>
                                :
                                <NavLink to={menu.link} key={i} exact={menu.exact} activeClassName={classes.active} className={classes.clickableNavigation}>
                                    {menu.title}
                                </NavLink>
                        )}
                    </div>
                    <div className={classes.sideFooter}>
                        <div className={classes.footerButtonContainer}>
                            {changePasswordButton}
                            {logButton}
                        </div>
                    </div>
                    {!this.props.noToggle &&
                        <SirioButton
                            blue
                            recommended
                            classes="m-2"
                            onClick={() => { this.toggleNav() }}>
                            Tutup Menu
                        </SirioButton>
                    }
                </nav>
            </>
        );
    }
}