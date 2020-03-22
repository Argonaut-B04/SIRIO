import React, { Component } from 'react';
import AuthenticationService from "../Services/AuthenticationService";
import { Redirect } from "react-router-dom"; 

class Logout extends Component {
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

export default Logout;
