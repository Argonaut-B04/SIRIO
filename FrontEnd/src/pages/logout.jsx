import React, { Component } from 'react';
import AuthenticationService from "../Services/AuthenticationService";
import { Redirect } from "react-router-dom";

/** 
 * Controller untuk Logout
 * 
 * Trigger: akses /logout
 * 
 * Mekanisme:
 * - Memanggil fungsi Logout dari Authentication Service
 * - Redirect ke halaman utama
 */
export default class Logout extends Component {

    constructor(props) {
        super(props);
        AuthenticationService.logout();
    }

    render() {
        return (
            <Redirect to="/" />
        );
    }
}