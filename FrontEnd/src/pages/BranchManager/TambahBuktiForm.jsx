import React from "react";
import FormTambahBukti from "../../Components/Form/BuktiPelaksanaan/FormTambahBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import AuthenticationService from "../../Services/AuthenticationService";
import { Redirect } from "react-router-dom";

export default class TambahBuktiForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            contentLoading: true
        }
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
        if (AuthenticationService.getRole() === "Manajer Operational Risk"
            || AuthenticationService.getRole() === "QA Lead Operational Risk"
            || AuthenticationService.getRole() === "Supervisor"
            || AuthenticationService.getRole() === "Administrator") {
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
            <SirioMainLayout contentLoading={this.state.contentLoading} loadingBody={this.state.loadingBody}>
                <FormTambahBukti contentFinishLoading={this.contentFinishLoading} contentStartLoading={this.contentStartLoading} changeLoadingBody={this.changeLoadingBody}/>
            </SirioMainLayout>
        );
    }
};