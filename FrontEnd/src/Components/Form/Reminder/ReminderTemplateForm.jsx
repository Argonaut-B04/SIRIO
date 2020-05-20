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
            changeComplete: false,
            changed: false,
            errorSubjek: "",
            errorBody: ""
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.submitable = this.submitable.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        if (this.props.location.state) {
            this.setState({
                idReminder: this.props.location.state.idReminder
            })
            this.renderKonten();
        } else {
            this.renderKontenGlobal();
        }
    }

    async renderKontenGlobal() {
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await ReminderService.getTemplateGlobal();

        // Mengubah isi dari loader
        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
            subject: response.data.result.subjects,
            content: response.data.result.body
        }, this.props.contentFinishLoading())

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
        // ini untuk nampung isi error nya apa
        var hasilError;
        // misalnya ini adalah validasi untuk input dengan name: "field"
        if (event.target.name === "subject") {

            var errorSubjek = ""
            if (event.target.value === "") {
                errorSubjek = "Subjek tidak boleh kosong"
            } else if (event.target.value.length < 10) {
                errorSubjek = "Subjek harus setidaknya terdiri dari 10 karakter";
            } else if (event.target.value.length > 74) {
                errorSubjek = "Subjek tidak dapat lebih dari 75 karakter";
            }

            this.setState({
                [event.target.name]: event.target.value,
                errorSubjek: errorSubjek,
                changed: true
            })

        } else if (event.target.name === "content") {

            var errorBody = "";
            if (event.target.value === "") {
                errorBody = "Body tidak boleh kosong"
            } else if (event.target.value.length < 10) {
                errorBody = "Body harus setidaknya terdiri dari 10 karakter";
            } else if (event.target.value.length > 498) {
                errorBody = "Subjek tidak dapat lebih dari 500 karakter";
            }

            // lalu kita simpan ke state, misal disini aku kasi nama error nya "errorField2"
            this.setState({
                [event.target.name]: event.target.value,
                errorBody: errorBody,
                changed: true
            })
        }
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
        return this.state.errorBody === "" && this.state.errorSubjek === "";
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
                    type: "text",

                    name: "subject",
                    value: this.state.subject,
                    errormessage: this.state.errorSubjek,

                    required: true,
                    placeholder: "masukan subject",
                    handleChange: this.handleChange,
                }, {
                    label: "Message Body",
                    type: "textarea",

                    name: "content",
                    value: this.state.content,
                    errormessage: this.state.errorBody,

                    required: true,
                    placeholder: "masukan isi pesan reminder",
                    handleChange: this.handleChange,
                }
            ]
        )
    }

    submitButton() {
        const submitable = this.submitable();
        const role = AuthenticationService.getRole();
        const canChangeGlobal = role === "Administrator" || role === "Supervisor" || role === "QA Lead Operational Risk" || role === "Super QA Officer Operational Risk" || role === "Manajer Operational Risk";

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
