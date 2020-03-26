import React, { Component } from 'react';
import classes from "./SideNavFramework.module.css"

/**
 * Komponen Side Nav untuk bagian kiri halaman login
 * 
 * Props yang tersedia:
 * - classes          : cssClass, custom styling untuk komponen
 */
export default class LoginSideNav extends Component {
    render() {
        return (
            <div className={this.props.classes ? [this.props.classes, classes.loginModeSideBar].join(' ') : classes.loginModeSideBar}>

            </div>
        );
    }
}