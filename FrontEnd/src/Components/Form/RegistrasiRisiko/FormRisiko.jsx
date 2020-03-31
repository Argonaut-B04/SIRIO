import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';

export default class FormRisiko extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "",
            kategori: "",
            sop: "",
            komponen: "",
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
    }

    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        if (typeof event.target.checked === "boolean") {
            this.setState(
                {
                    [event.target.name]
                        : event.target.checked
                }
            )
        } else {
            this.setState(
                {
                    [event.target.name]
                        : event.target.value
                }
            )
        }
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi unu wajib ada jika membuat field tipe select
    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    handleSubmit(event) {
        alert("submited");
        // event.preventDefault wajib ada
        event.preventDefault();
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Nama Risiko*",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "Masukan nama risiko"
                }, {
                    label: "Kategori Risiko*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "kategori",
                    value: this.state.kategori,
                    optionList: [
                        {
                            label: "Kategori 1",
                            value: "Kategori 1"
                        }, {
                            label: "Kategori 2",
                            value: "Kategori 2"
                        }, {
                            label: "Kategori 3",
                            value: "Kategori 3"
                        }
                    ]
                }, {
                    label: "Referensi SOP*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "sop",
                    value: this.state.sop,
                    optionList: [
                        {
                            label: "1",
                            value: "1"
                        }, {
                            label: "2",
                            value: "2"
                        }, {
                            label: "3",
                            value: "3"
                        }
                    ]
                }, {
                    label: "Komponen Risiko*",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "komponen",
                    value: this.state.komponen,
                    placeholder: "Masukan komponen risiko"
                }
            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={() => window.location.href = "/registrasi-risiko"}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "/registrasi-risiko"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <SirioForm
                title="Form Risiko"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
}