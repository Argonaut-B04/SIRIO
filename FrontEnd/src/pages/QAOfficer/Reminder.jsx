import React from "react";
import TabelReminder from "../../Components/Tables/Reminder/TabelReminder";
import SirioMainLayout from "../../Layout/SirioMainLayout";

/**
 * Controller yang menampilkan halaman daftar remidner
 */
export default class Reminder extends React.Component {

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
        return (
            // Menggunakan contentLoading dan loadingBody untuk mengubah loader konten
            <SirioMainLayout contentLoading={this.state.contentLoading} loadingBody={this.state.loadingBody}>
                <TabelReminder contentFinishLoading={this.contentFinishLoading} contentStartLoading={this.contentStartLoading} changeLoadingBody={this.changeLoadingBody} />
            </SirioMainLayout>
        );
    }
};