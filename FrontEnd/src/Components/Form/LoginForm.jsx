import React, { Component } from 'react';
import classes from "./LoginForm.module.css";

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
        console.log("login button clicked, not implemented");
        // AuthenticationService
        //     .executeBasicAuthenticationService(this.state.username, this.state.password)
        //     .then(
        //         () => {
        //             AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password);
        //             this.props.history.push(`/courses`);
        //         }
        //     )
        //     .catch(
        //         () => {
        //             this.setState({
        //                 showSuccessMessage: false,
        //                 hasLoginFailed: true
        //             })
        //         }
        //     )
    }

    render() {
        return (
            <div className={classes.loginFormContainer}>
                <h2>Selamat datang kembali!</h2>
                <div>
                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                    <fieldset className="form-group">
                        <label>Username</label>
                        <input className="form-control" type="text" name="username" placeholder="Username" value={this.state.username} onChange={this.handleChange} />
                    </fieldset>
                    <fieldset className="form-group">
                        <label>Password</label>
                        <input className="form-control" type="password" name="password" placeholder="Masukan 8 karakter atau lebih" value={this.state.password} onChange={this.handleChange} />
                    </fieldset>
                    <fieldset className="w-100 text-right">
                        <button className="btn sirio-btn-blue-recommended" onClick={this.loginClicked}>
                            Login
                    </button>
                    </fieldset>

                </div>
            </div>
        );
    }
}

export default LoginForm;
