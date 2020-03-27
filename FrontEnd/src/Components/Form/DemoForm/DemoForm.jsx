import React from 'react';
import SirioForm from '../SirioForm';

/**
 * Kelas untuk membuat form demo
 */
export default class DemoForm extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "bambang",
            umur: 18,
            jenisKelamin: "Pria",
            manusia: true
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
                    label: "Nama Bambang",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "masukan nama bambang"
                }, {
                    label: "Umur Bambang",
                    handleChange: this.handleChange,
                    type: "number",
                    name: "umur",
                    value: this.state.umur,
                    placeholder: "masukan umur bambang"
                }, {
                    label: "Jenis Kelamin",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "jenisKelamin",
                    value: this.state.jenisKelamin,
                    optionList: [
                        {
                            label: "Pria",
                            value: "Pria"
                        }, {
                            label: "Wanita",
                            value: "Wanita"
                        }, {
                            label: "Gak tau apa",
                            value: "gak tau apa"
                        }
                    ]
                }, {
                    label: "Manusia?",
                    handleChange: this.handleChange,
                    type: "checkbox",
                    name: "manusia",
                    value: this.state.manusia,
                }
            ]
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <SirioForm
                title="Demo Form"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
            />
        );
    }
}