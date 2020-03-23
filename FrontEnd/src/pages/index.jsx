import React from "react";
import {
    Link
} from 'react-router-dom';
import AuthenticationService from "../Services/AuthenticationService";
import SirioMessageButton from "../Components/Button/ActionButton/SirioMessageButton";
import SirioWarningButton from "../Components/Button/ActionButton/SirioWarningButton";
import SirioConfirmButton from "../Components/Button/ActionButton/SirioConfirmButton";

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
                <Link to="/bm/rekomendasi" className="btn btn-primary">Rekomendasi BM</Link>
                <Link to="/login" className="btn btn-primary">Login</Link>
                <Link to="/logout" className="btn btn-primary">Logout</Link>
                <br />
                <SirioMessageButton
                    purple
                    circular
                    modalTitle="Anda telah berhasil membuka popup berhasil"
                    customConfirmText="Tombol Iya"
                    customCancelText="Tombol Tidak"
                >
                    test popup pesan berhasil
                </SirioMessageButton>
                <br />
                <SirioWarningButton
                    purple
                    circular
                    modalTitle="Ini modal warning"
                    modalDesc="ini deskripsi yang harusnya menjelaskan modal Title"
                    onConfirm={() => window.location.href = "http://www.google.com"}
                    customConfirmText="Tombol Iya"
                    customCancelText="Tombol Tidak"
                >
                    Test Popup Warning
                </SirioWarningButton>
                <br />
                <SirioConfirmButton
                    purple
                    circular
                    modalTitle="ini pesan konfirmasi, apa anda mau ke google sekarang?"
                    onConfirm={() => window.location.href = "http://www.google.com"}
                    customConfirmText="Tombol Iya"
                    customCancelText="Tombol Tidak"
                >
                    Test Popup Konfirmasi
                </SirioConfirmButton>
            </div>
        )
    }
}