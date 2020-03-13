import React from "react";
import {Link} from "react-router-dom";

const NotFound = () => {
    return (
        <div>
            <h3>Error 404</h3>
            <small>Not Found</small>
            <br></br>
            <Link to="/" className="btn btn-primary">Back to Home</Link>
        </div>
    );
};

export default NotFound;
