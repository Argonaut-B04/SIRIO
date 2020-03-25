import React from "react";
import { Link } from "react-router-dom";

/**
 * Controller yang akan menampilkan halaman Error jika user mengalami 404
 * TODO: Perlu kustomisasi halaman 404
 */
export default class NotFound extends React.Component {
    render() {
        return (
            <div>
                <h3>Error 404</h3>
                <small>Not Found</small>
                <br></br>
                <Link to="/" className="btn btn-primary">Back to Home</Link>
            </div>
        )
    }
}
