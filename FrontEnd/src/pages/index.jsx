import React from "react";
import {
    Link
} from 'react-router-dom'

export default class MainPage extends React.Component {
    render() {
        return (
            <div>
                <h3>Welcome to SIRIO</h3>
                <small>Main Page</small>
                <br></br>
                <Link to="/rekomendasi" className="btn btn-primary">Tabel Rekomendasi</Link>
                <Link to="/login" className="btn btn-primary">Login</Link>
            </div>
        )
    }
}