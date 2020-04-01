import React from 'react';
import SirioForm from '../../Form/SirioForm';
import SirioButton from '../../Button/SirioButton';

/**
 * Kelas untuk membuat form demo
 */
export default class FormTambahKantorCabang extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            kc: "kantor cabang 1",
            bm: "Billa",
            area: "area 1",
            regional: "regional 1"
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
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
                    label: "Nama Point*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "kc",
                    value: this.state.kc,
                    placeholder: "Masukan nama point"
                }, {
                    label: "Branch Manger*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "bm",
                    value: this.state.bm,
                    optionList: [
                        {
                            label: "Billa",
                            value: "Billa"
                        }, {
                            label: "kana",
                            value: "kana"
                        }, {
                            label: "tora",
                            value: "tora"
                        }
                    ]
                },{
                    label: "Area*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "area",
                    value: this.state.area,
                    optionList: [
                        {
                            label: "area 1",
                            value: "area 1"
                        }, {
                            label: "area 2",
                            value: "area 2"
                        }, {
                            label: "area 3",
                            value: "area 3"
                        }
                    ]
                },{
                    label: "Regional*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "regional",
                    value: this.state.regional,
                    optionList: [
                        {
                            label: "regional 1",
                            value: "regional 1"
                        }, {
                            label: "regional 2",
                            value: "regional 2"
                        }, {
                            label: "regional 3",
                            value: "regional 3"
                        }
                    ]
                }

            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={() => window.location.href = "http://www.google.com"}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "http://www.google.com"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <SirioForm
                title="Form Tambah Kantor Cabang"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
}