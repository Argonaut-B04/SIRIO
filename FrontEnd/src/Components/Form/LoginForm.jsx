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
        const { state } = this.props.location;
        if (state) {
            this.setState({
                source: state.source,
                target: state.goto
            })
        }
    }

    // Fungsi yang mengikat input field dengan state
    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        )
    }

    // Fungsi ketika user klik tombol submit
    // Komunikasi dengan SirioBackend melalui AuthenticationService
    loginClicked() {
        const { username, password, target } = this.state
        AuthenticationService
            .executeBasicAuthenticationService(username, password)
            .then(
                (response) => {
                    AuthenticationService.registerSuccessfulLogin(username, password, response.data.result.role.namaRole);
                    if (target) {
                        window.location.href = target;
                    } else {
                        window.location.href = "/";
                    }
                }
            )
            .catch(
                (error) => {
                    var errInfo = error.response ? error.response.data.message : "Sambungan ke server terputus";
                    if (error.response) {
                        errInfo = error.response.status === 401 ? "Username dan Password tidak sesuai" : errInfo;
                    }
                    this.setState({
                        hasLoginFailed: true,
                        errInfo: errInfo
                    })
                }
            )
    }

    render() {
        const { handleChange, loginClicked, state } = this;
        const { loginFormContainer } = classes;
        const { source, hasLoginFailed, errInfo, username, password } = state;
        return (
            <div className={loginFormContainer}>
                <h2>Selamat datang kembali!</h2>
                <div>
                    {source === 401 && <div className="alert alert-warning">You need to login first</div>}
                    {hasLoginFailed && <div className="alert alert-warning">{errInfo}</div>}
                    <fieldset className="form-group">
                        <label>Username</label>
                        <input
                            className="form-control"
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={username}
                            onChange={handleChange}
                            onKeyPress={(event) => {
                                if (event.key === "Enter") {
                                    loginClicked();
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
                            value={password}
                            onChange={handleChange}
                            onKeyPress={(event) => {
                                if (event.key === "Enter") {
                                    loginClicked();
                                }
                            }}
                        />
                    </fieldset>
                    <fieldset className="w-100 text-right">
                        <SirioButton
                            blue
                            recommended
                            onClick={loginClicked}
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