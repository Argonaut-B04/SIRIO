import React, { Component } from 'react';
import SirioMainLayout from "../../Layout/SirioMainLayout";
import ReminderTemplateForm from '../../Components/Form/Reminder/ReminderTemplateForm';
import AuthenticationService from "Services/AuthenticationService";
import { Redirect } from "react-router-dom";

export class TemplateReminder extends Component {

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
        if (AuthenticationService.getRole() === "Super QA Officer Operational Risk"
            || AuthenticationService.getRole() === "QA Lead Operational Risk"
            || AuthenticationService.getRole() === "Supervisor"
            || AuthenticationService.getRole() === "Administrator"
            || AuthenticationService.getRole() === "Branch Manager") {
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
                <ReminderTemplateForm contentFinishLoading={this.contentFinishLoading} contentStartLoading={this.contentStartLoading} changeLoadingBody={this.changeLoadingBody} />
            </SirioMainLayout>
        )
    }
}

export default TemplateReminder;