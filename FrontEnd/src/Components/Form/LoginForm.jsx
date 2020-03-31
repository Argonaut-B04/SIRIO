import React, { Component } from 'react';
import classes from "./LoginForm.module.css";
import SirioButton from "../Button/SirioButton";
import AuthenticationService from "../../Services/AuthenticationService";
import { withRouter } from 'react-router-dom';

/**
 * Komponen independen Form Login
 * TODO: refactor menggunakan form tag
 */
class LoginForm extends Component {

    constructor(props) {
        super(props);

        // informasi username dan password di input field 
        this.state = {
            username: '',
            password: '',
            hasLoginFailed: false,
            source: null,
            target: null
        }

        this.handleChange = this.handleChange.bind(this);
        this.loginClicked = this.loginClicked.bind(this);
        this.loadSource = this.loadSource.bind(this);
    }

    componentDidMount() {
        this.loadSource();
    }

    loadSource() {
        if (this.props.location.state) {
            this.setState({
                source: this.props.location.state.source,
                target: this.props.location.state.goto
            })
        }
    }

    // Fungsi yang mengikat input field dengan state
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    // Fungsi ketika user klik tombol submit
    // Komunikasi dengan SirioBackend melalui AuthenticationService
    loginClicked() {
        AuthenticationService
            .executeBasicAuthenticationService(this.state.username, this.state.password)
            .then(
                (response) => {
                    AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password, response.data.result.role.namaRole);
                    if (this.state.target) {
                        window.location.href = this.state.target;
                    } else {
                        window.location.href = "/";
                    }
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
                    {this.state.source === 401 && <div className="alert alert-warning">You need to login first</div>}
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

export default withRouter(LoginForm);