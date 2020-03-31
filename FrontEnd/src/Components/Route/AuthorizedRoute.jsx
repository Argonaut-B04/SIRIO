import React, { Component } from 'react';
import { Route, Redirect } from "react-router-dom";
import AuthenticationService from "../../Services/AuthenticationService";

export default class AuthorizedRoute extends Component {
    render() {
        if (AuthenticationService.isLoggedIn()) {
            return (
                <Route {...this.props} />
            )
        } else {
            return (
                <Redirect to={{
                    pathname: "/login",
                    state: {
                        source: 401,
                        goto: this.props.path
                    }
                }} />
            );
        }
    }
}