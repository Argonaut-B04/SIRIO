import React, { Component } from 'react';
import classes from "./SideNavFramework.module.css"

class LoginSideNav extends Component {
  render() {
    return (
        <div className={this.props.classes ? [this.props.classes, classes.loginModeSideBar].join(' ') : classes.loginModeSideBar}>

        </div>
    );
  }
}

export default LoginSideNav;
