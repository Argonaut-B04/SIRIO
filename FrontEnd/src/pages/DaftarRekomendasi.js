import React from "react";
import { Link } from "react-router-dom";
import TabelRekomendasi from "../Components/Tables/Rekomendasi/TabelRekomendasi";

const MainPage = () => {
    return (
        <div>
            <Link to="/" className="btn btn-primary">Back to Home</Link>
            <TabelRekomendasi />
        </div>
    );
};

export default MainPage;
