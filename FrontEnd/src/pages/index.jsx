import React from "react";
import {
    Link
} from 'react-router-dom';
import AuthenticationService from "../Services/AuthenticationService";

export default class MainPage extends React.Component {
    render() {
        let username = AuthenticationService.getUsername();
        let role = AuthenticationService.getRole();
        return (
            <div>
                <h3>Welcome to SIRIO</h3>
                {username && <h4>Username: {username}</h4>}
                {role && <h4>Role: {role}</h4>}
                <small>Main Page</small>
                <br></br>
                <Link to="/rekomendasi" className="btn btn-primary">Tabel Rekomendasi</Link>
                <Link to="/login" className="btn btn-primary">Login</Link>
                <Link to="/logout" className="btn btn-primary">Logout</Link>
            </div>
        )
    }
}