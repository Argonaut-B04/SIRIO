import React from 'react';
import SirioForm from '../Components/Form/SirioForm';

/**
 * Kelas untuk membuat form demo
 */
export default class TheForm extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "bambang",
            umur: 18,
            jenisKelamin: "Pria"
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
    }

    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
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
                    type: "text",
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
                    handleChange: this.handleChange,
                    type: "select",
                    name: "jenisKelamin",
                    value: this.state.jenisKelamin,
                    optionList: [
                        {
                            name: "Pria",
                            value: "Pria"
                        }, {
                            name: "Wanita",
                            value: "Wanita"
                        }
                    ]
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