import React from "react";
import classes from './SideNav.module.css';

class SideNav extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={classes.sidebar}>
                <div className={classes.logo}>
                    <img src={process.env.PUBLIC_URL + '/logo.png'} className={classes.logoImage} />
                    <h1 className={classes.logoName}>SIRIO</h1>
                </div>
                <h4 className={classes.logoDesc}>
                    Sistem Informasi Risiko Operasional
                </h4>
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
export default SideNav;