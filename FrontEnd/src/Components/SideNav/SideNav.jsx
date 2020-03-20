import React from "react";
import classes from './SideNav.module.css';
import LogoTag from "../LogoTag/LogoTag";

class SideNav extends React.Component {

    render() {
        if (this.props.loginMode) {
            return (
                <div className={this.props.classes ? [this.props.classes, classes.loginModeSideBar].join(' ') : classes.loginModeSideBar}>

                </div>
            );
        } else {
            return (
                <div className={this.props.classes ? [this.props.classes, classes.sidebar].join(' ') : classes.sidebar}>
                    <LogoTag light />
                    <div className={classes.identity}>
                        <h3 className={classes.username}>
                            {this.props.username}
                        </h3>
                        <p className={classes.role}>
                            {this.props.role}
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
                            <a href={this.props.logHyperlink} className={classes.footerButton}>
                                {this.props.login ? "Logout" : "Login"}
                            </a>
                        </div>
                    </div>
                </div>
            );
        }

    }
}
export default SideNav;