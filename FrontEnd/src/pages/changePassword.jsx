import React from "react";
import FormUbahPassword from "../Components/Form/Employee/EmployeeFormUbahPassword";
import SirioMainLayout from "../Layout/SirioMainLayout";
import AuthenticationService from "Services/AuthenticationService";
import { Redirect } from "react-router-dom";

export default class ChangePassword extends React.Component {
    constructor(props) {
        super(props);

        // State untuk menyimpan kondisi loader konten
        this.state = {
            contentLoading: true,
        };

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
            <SirioMainLayout contentLoading={this.state.contentLoading} loadingBody={this.state.loadingBody}>
                <FormUbahPassword contentFinishLoading={this.contentFinishLoading} contentStartLoading={this.contentStartLoading} changeLoadingBody={this.changeLoadingBody} />
            </SirioMainLayout>
        );
    }
};