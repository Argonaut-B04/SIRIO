import React, { Component } from 'react';
import classes from "./LoginForm.module.css";
import SirioButton from "../Button/SirioButton";
import AuthenticationService from "../../Services/AuthenticationService";

class LoginForm extends Component {

    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',
            hasLoginFailed: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.loginClicked = this.loginClicked.bind(this);
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    loginClicked() {
        AuthenticationService
            .executeBasicAuthenticationService(this.state.username, this.state.password)
            .then(
                (response) => {
                    AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password, response.data.result.role);
                    this.props.history.push("/");
                }
            )
            .catch(
                () => {
                    this.setState({
                        hasLoginFailed: true
                    })
                }
            )
    }

    render() {
        return (
            <div className={classes.loginFormContainer}>
                <h2>Selamat datang kembali!</h2>
                <div>
                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                    <fieldset className="form-group">
                        <label>Username</label>
                        <input
                            className="form-control"
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={this.state.username}
                            onChange={this.handleChange}
                            onKeyPress={(event) => {
                                if (event.key === "Enter") {
                                    this.loginClicked();
                                }
                            }}
                        />
                    </fieldset>
                    <fieldset className="form-group">
                        <label>Password</label>
                        <input
                            className="form-control"
                            type="password"
                            name="password"
                            placeholder="Masukan 8 karakter atau lebih"
                            value={this.state.password}
                            onChange={this.handleChange}
                            onKeyPress={(event) => {
                                if (event.key === "Enter") {
                                    this.loginClicked();
                                }
                            }}
                        />
                    </fieldset>
                    <fieldset className="w-100 text-right">
                        <SirioButton
                            blue
                            recommended
                            onClick={this.loginClicked}
                        >
                            Login
                        </SirioButton>
                    </fieldset>
                </div>
            </div>
        );
    }
}

export default LoginForm;
