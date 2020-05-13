import React, { Component } from 'react';
import { withRouter, Prompt } from 'react-router-dom';
import SirioForm from '../SirioForm';
import SirioWarningButton from '../../Button/ActionButton/SirioWarningButton';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';
import ReminderService from '../../../Services/ReminderService';
import AuthenticationService from '../../../Services/AuthenticationService';

class ReminderTemplateForm extends Component {

    constructor(props) {
        super(props);

        this.state = {
            idReminder: "",
            subject: "",
            content: "",
            submitableArray: [false, false],
            changeComplete: false,
            changed: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.submitable = this.submitable.bind(this);
        this.validateSubject = this.validateSubject.bind(this);
        this.validateBody = this.validateBody.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        if (this.props.location.state) {
            this.setState({
                idReminder: this.props.location.state.idReminder
            })
            this.renderKonten();
        }
    }

    async renderKonten() {
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await ReminderService.getTemplateByIdReminder(this.props.location.state.idReminder);

        // Mengubah isi dari loader
        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
            subject: response.data.result.subjects,
            content: response.data.result.body
        }, this.props.contentFinishLoading())
        
    }

    handleChange(event) {
        const name = event.target.name;
        const value = event.target.value;
        const changed = "changed" + name;
        this.setState(
            {
                [name]: value,
                [changed]: true,
                changed: true
            }
        )
    }

    endNotification() {
        this.setState({
            changeComplete: false
        })
    }

    handleSubmit(event, area) {
        event && event.preventDefault();
        if (this && !this.submitable()) return;

        const data = {
            id: this.state.id,
            idReminder: this.state.idReminder,
            subject: this.state.subject,
            content: this.state.content,
            effectArea: area
        }

        ReminderService.changeTemplate(data)
            .then((response) => {
                this.setState({
                    changeComplete: true,
                    changed: false
                })
                window.history.back();
            });
    }

    submitable() {
        var submitable = true;
        const fokus = this.state.submitableArray;
        for (let i = 0; i < fokus.length; i++) {
            submitable = (submitable && fokus[i])
        }
        return submitable;
    }

    validateSubject(fokus) {
        var submitable = true;
        var errorNama;
        if (fokus === "") {
            submitable = false;
            if (this.state.changedsubject) {
                submitable = false;
                errorNama = "jangan kosong"
            }
        } else if (fokus.length < 10) {
            submitable = false;
            errorNama = "paling sedikit 10";
        } else if (fokus.length > 74) {
            submitable = false;
            errorNama = "Batas nya 75 huruf, sekarang sudah " + fokus.length;
        }

        if (submitable !== this.state.submitableArray[0]) {
            var stateToChange = this.state.submitableArray;
            stateToChange[0] = submitable;
            this.setState({
                submitableArray: stateToChange
            })
        }
        return errorNama;
    }

    validateBody(fokus) {
        var submitable = true;
        var errorNama;
        if (fokus === "") {
            submitable = false;
            if (this.state.changedcontent) {
                submitable = false;
                errorNama = "jangan kosong"
            }
        } else if (fokus.length < 10) {
            submitable = false;
            errorNama = "paling sedikit 10";
        } else if (fokus.length > 600) {
            submitable = false;
            errorNama = "Batas nya 600 huruf, sekarang sudah " + fokus.length;
        }

        if (submitable !== this.state.submitableArray[1]) {
            var stateToChange = this.state.submitableArray;
            stateToChange[1] = submitable;
            this.setState({
                submitableArray: stateToChange
            })
        }
        return errorNama;
    }

    inputDefinition() {
        return (
            [
                {
                    fullComponent: <p>
                        Masukan subject dan isi dari email yang akan dikirimkan sebagai reminder<br />
                        <small>
                            Cantumkan nama penerima dengan <code>!!nama!! </code>
                            dan tanggal tenggat waktu dengan <code>!!tanggal!!</code>
                        </small>
                    </p>
                }, {
                    label: "Subject",
                    required: true,
                    handleChange: this.handleChange,
                    validationFunction: this.validateSubject,
                    type: "text",
                    name: "subject",
                    value: this.state.subject,
                    placeholder: "masukan subject",
                }, {
                    label: "Message Body",
                    handleChange: this.handleChange,
                    validationFunction: this.validateBody,
                    type: "textarea",
                    name: "content",
                    value: this.state.content,
                    placeholder: "masukan isi pesan reminder",
                    required: true
                }
            ]
        )
    }

    submitButton() {
        const submitable = this.submitable();
        const role = AuthenticationService.getRole();
        const canChangeGlobal = role === "dev" || role === "Administrator" || role === "Supervisor" || role === "QA Lead Operational Risk" || role === "Super QA Officer Operational Risk";

        return (
            <div className="d-flex align-items-center justify-content-end">
                <p className="m-0">
                    Simpan pengaturan konten reminder untuk
                </p>
                {this.state.idReminder &&
                    <>
                        <SirioConfirmButton
                            purple
                            disabled={!submitable}
                            hover
                            classes="ml-2"
                            title="Pengaturan isi email akan disimpan untuk satu reminder ini"
                            type="button"
                            modalTitle="Anda akan menyimpan pengaturan email untuk reminder ini"
                            onConfirm={() => this.handleSubmit(null, "reminder")}
                            customConfirmText="Konfirmasi"
                            customCancelText="Batal"
                        >
                            Reminder
                        </SirioConfirmButton>
                        <SirioConfirmButton
                            purple
                            disabled={!submitable}
                            hover
                            classes="ml-2"
                            title="Pengaturan isi email akan disimpan untuk seluruh reminder dalam rekomendasi ini"
                            type="button"
                            modalTitle="Anda akan menyimpan pengaturan email untuk seluruh reminder dalam rekomendasi ini"
                            onConfirm={() => this.handleSubmit(null, "rekomendasi")}
                            customConfirmText="Konfirmasi"
                            customCancelText="Batal"
                        >
                            Rekomendasi
                        </SirioConfirmButton>
                    </>
                }
                {canChangeGlobal ?
                    <SirioConfirmButton
                        purple
                        disabled={!submitable}
                        hover
                        classes="ml-2"
                        title="Template akan digunakan untuk seluruh reminder baru dalam Sirio"
                        type="button"
                        modalTitle="Anda akan mengubah template reminder untuk seluruh reminder baru dalam Sirio"
                        onConfirm={() => this.handleSubmit(null, "global")}
                        customConfirmText="Konfirmasi"
                        customCancelText="Batal"
                    >
                        Global
                    </SirioConfirmButton>
                    :
                    <SirioConfirmButton
                        purple
                        disabled={!submitable}
                        hover
                        classes="ml-2"
                        title="Pengaturan ini akan menyimpan template reminder untuk seluruh reminder yang anda buat selanjutnya"
                        type="button"
                        modalTitle="Anda akan menyimpan template reminder untuk reminder yang akan anda buat seterusnya"
                        onConfirm={() => this.handleSubmit(null, "akun")}
                        customConfirmText="Konfirmasi"
                        customCancelText="Batal"
                    >
                        Akun
                    </SirioConfirmButton>
                }
                <SirioWarningButton
                    red
                    hover
                    classes="ml-2"
                    modalTitle="Konfirmasi Pembatalan"
                    modalDesc="Seluruh perubahan isi email yang belum tersimpan akan dihapus. Konfirmasi?"
                    onConfirm={() => window.history.back()}
                    customConfirmText="Konfirmasi"
                    type="button"
                    customCancelText="Kembali"
                >
                    Batal
                </SirioWarningButton>
            </div>
        )
    };

    render() {
        return (
            <>
                <SirioForm
                    title="Pengaturan Surel Reminder"
                    inputDefinition={this.inputDefinition()}
                    submitButton={this.submitButton()}
                    onSubmit={this.handleSubmit}
                />
                <Prompt
                    when={this.state.changed}
                    message={`Anda akan membatalkan perubahan pengaturan, konfirmasi ?`}
                />
                {this.state.changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Perubahan Reminder Telah Disimpan"
                        customConfirmText="Kembali"
                        onClick={this.endNotification}
                    />
                }
            </>
        )
    }
}

export default withRouter(ReminderTemplateForm);
