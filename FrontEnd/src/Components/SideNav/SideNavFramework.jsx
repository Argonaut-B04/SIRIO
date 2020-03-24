import React from "react";
import classes from './SideNavFramework.module.css';
import LogoTag from "../LogoTag/LogoTag";
import SirioButton from "../Button/SirioButton";
import AuthenticationService from "../../Services/AuthenticationService";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';


export default class SideNavFramework extends React.Component {

    onSelect(event) {
        window.location.href = event.value;
    }

    render() {
        let username = AuthenticationService.getUsername();
        let role = AuthenticationService.getRole();

        return (
            <div className={this.props.classes ? [this.props.classes, classes.sidebar].join(' ') : classes.sidebar}>
                <LogoTag light />
                <div className={classes.identity}>
                    <h3 className={classes.username}>
                        {username}
                    </h3>
                    <p className={classes.role}>
                        {role}
                    </p>
                </div>
                <div className={classes.navigationContainer}>
                    {this.props.links.map((menu, i) =>

                        menu.dropdown ?
                            <div key={i}>
                                <Dropdown
                                    controlClassName={classes.clickableNavigation}
                                    menuClassName={classes.dropdownMenu}
                                    options={menu.dropdown}
                                    onChange={this.onSelect}
                                    placeholder={menu.title} />
                            </div>
                            :
                            <a href={menu.link} key={i}>
                                <div className={menu.active ? [classes.clickableNavigation, classes.active].join(' ') : classes.clickableNavigation}>
                                    {menu.title}
                                </div>
                            </a>

                    )}

                </div>
                <div className={classes.sideFooter}>
                    <div className={classes.footerButtonContainer}>
                        <SirioButton
                            blue
                            recommended
                            circular
                            classes={classes.footerButton}
                            onClick={() => window.location.href = '/logout'}
                        >
                            Logout
                            </SirioButton>
                    </div>
                </div>
            </div>
        );
    }
}