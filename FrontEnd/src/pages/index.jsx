import React from "react";
import {
    Link
} from 'react-router-dom';
import AuthenticationService from "../Services/AuthenticationService";
import SirioMessageButton from "../Components/Button/ActionButton/SirioMessageButton";
import SirioWarningButton from "../Components/Button/ActionButton/SirioWarningButton";
import SirioConfirmButton from "../Components/Button/ActionButton/SirioConfirmButton";

/**
 * Controller untuk menampilkan halaman utama
 */
export default class MainPage extends React.Component {

    constructor(props) {
        super(props);

        // Mengambil username dan role dari AuthenticationService
        this.state = {
            username: AuthenticationService.getUsername(),
            role: AuthenticationService.getRole()
        }
    }
    render() {
        return (
            <div>
                <h3>Welcome to SIRIO</h3>

                {/* Contoh cara menampilkan username dan role */}
                {this.state.username && <h4>Username: {this.state.username}</h4>}
                {this.state.role && <h4>Role: {this.state.role}</h4>}

                <small>Main Page</small>
                <br />

                {/* Contoh cara menampilkan tombol redirect */}
                {/* Alternatif lain: href="/tujuan" */}
                <Link to="/rekomendasi" className="btn btn-primary">Tabel Rekomendasi</Link>
                <br />
                <Link to="/bm/rekomendasi" className="btn btn-primary">Rekomendasi BM</Link>
                <br />
                <Link to="/bukti-pelaksanaan" className="btn btn-primary">Tabel Bukti</Link>
                <br />
                <Link to="/mor/registrasi-risiko" className="btn btn-primary">Registrasi Risiko</Link>
                <br />
                <Link to="/Form" className="btn btn-primary">Demo Form</Link>
                <br />
                <Link to="/login" className="btn btn-primary">Login</Link>
                <br />
                <Link to="/logout" className="btn btn-primary">Logout</Link>
                <br />

                {/* Contoh button yang mengeluarkan popup berupa message */}
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

                {/* Contoh button yang mengeluarkan popup berupa warning */}
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

                {/* Contoh button yang mengeluarkan popup konfirmasi */}
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