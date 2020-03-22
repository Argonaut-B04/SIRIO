import React from "react";
import classes from './SideNavFramework.module.css';
import LogoTag from "../LogoTag/LogoTag";
import SirioButton from "../Button/SirioButton";
import AuthenticationService from "../../Services/AuthenticationService";

export default class SideNavFramework extends React.Component {

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
                    {this.props.links.map((menu) =>
                        <a href={menu.link}>
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