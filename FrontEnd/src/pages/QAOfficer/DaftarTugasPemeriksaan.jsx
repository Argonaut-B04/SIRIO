import React from "react";
import TabelTugasPemeriksaan from "../../Components/Tables/TugasPemeriksaan/TabelTugasPemeriksaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import AuthenticationService from "Services/AuthenticationService";
import { Redirect } from "react-router-dom";

export default class DaftarTugasPemeriksaan extends React.Component {
    constructor(props) {
        super(props);

        // State untuk menyimpan kondisi loader konten
        this.state = {
            contentLoading: true,
        }

        // Jangan lupa bind seluruh fungsi yang menggunakan state
        this.contentFinishLoading = this.contentFinishLoading.bind(this);
        this.contentStartLoading = this.contentStartLoading.bind(this);
        this.changeLoadingBody = this.changeLoadingBody.bind(this);
    }

    // Fungsi untuk menghentikan tampilan loader konten
    contentFinishLoading() {
        setTimeout(function () { // Memberikan jeda waktu 0.5 detik
            this.setState({
                contentLoading: false
            })
        }.bind(this), 500)
    }

    // Fungsi untuk menampilkan loader konten
    contentStartLoading() {
        this.setState({
            contentLoading: true
        })
    }

    // Fungsi untuk mengubah teks loader konten
    changeLoadingBody(body) {
        this.setState({
            loadingBody: body
        })
    }

    render() {
        if (AuthenticationService.getRole() !== "QA Officer Operational Risk" || AuthenticationService.getRole() !== "Super QA Officer Operational Risk") {
            return (
                <Redirect to={{
                    pathname: "/error",
                    state: {
                        detail: "Not Authorized",
                        code: "401"
                    }
                }} />
            )
        }

        return (
            // Menggunakan contentLoading dan loadingBody untuk mengubah loader konten
            <SirioMainLayout contentLoading={this.state.contentLoading} loadingBody={this.state.loadingBody}>
                <TabelTugasPemeriksaan contentFinishLoading={this.contentFinishLoading} contentStartLoading={this.contentStartLoading} changeLoadingBody={this.changeLoadingBody} />
            </SirioMainLayout>
        );
    }
};