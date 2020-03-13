import React from "react";
import {Link} from "react-router-dom";

const MainPage = () => {
    return (
        <div>
            <h3>Welcome to SIRIO</h3>
            <small>Main Page</small>
            <br></br>
            <Link to="/rekomendasi" className="btn btn-primary">Tabel Rekomendasi</Link>
        </div>
    );
};

export default MainPage;
