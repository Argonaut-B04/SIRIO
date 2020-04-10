import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';

/**
 * Kelas untuk membuat form demo
 */
export default class FormTambahKantorCabang extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            kantorCabang: "kantor cabang 1",
            QA: "Billa",
            tanggalMulai: "12/12/2020",
            tanggalSelesai: "19/12/2020",
            link: "ini link",
            tugasPemeriksaan: []
           
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

    // handleAdd = () => {
    //     const _fields = [...fields];
    //     _fields.push({ kantorCabang: '', QA: '', tanggalMulai:'', tanggalSelesai:'' });
    //     setFields(_fields);
    // }

    // handleRemove = (i) => {
    //     const _fields = [...fields];
    //     _fields.splice(i, 1);
    //     setFields(_fields);
    // }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Kantor Cabang*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "kantorCabang",
                    value: this.state.kantorCabang,
                    optionList: [
                        {
                            label: "kantor cabang 1",
                            value: "kantor cabang 1"
                        }, {
                            label: "kantor cabang 2",
                            value: "kantor cabang 2"
                        }, {
                            label: "kantor cabang 3",
                            value: "kantor cabang 3"
                        }
                    ]
                }, {
                    label: "QA Officer*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "QA",
                    value: this.state.QA,
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
                    label: "Tanggal Mulai*",
                    handleChange: this.handleChange,
                    type: "date",
                    name: "tanggalMulai",
                    value: this.state.tanggalMulai,
                    placeholder: "Masukan tanggal mulai"
                },{
                    label: "Tanggal Selesai*",
                    handleChange: this.handleChange,
                    type: "date",
                    name: "tanggalSelesai",
                    value: this.state.tanggalSelesai,
                    placeholder: "Masukan tanggal selesai"
                },{
                    label: "Link Majelis*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "link",
                    value: this.state.link,
                    placeholder: "Masukan link majelis"
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

    // cancelButton(){
    //     return(
    //         <div className="w-100 text-center">
    //             <SirioButton
    //                 purple
    //             >
    //             Batal
    //             </SirioButton>
    //         </div>
    //     )
    // }

    // removeButton(){
    //     {fields.map((field, i) => {
    //         return (
    //             <SirioButton
    //                 purple
    //                 onClick={() => handleRemove(i)}
    //             >
    //             Hapus
    //             </SirioButton>
    //         )
    //     })}
    // }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                <SirioForm
                    title="Form Tambah Rencana Pemeriksaan"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                    subtitle="Tugas Pemeriksaan 1"
                    // cancelButton={this.cancelButton()}
                    // removeButton={this.removeButton()}
                />
               {this.state.changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Tenggat Waktu berhasil Disimpan"
                        customConfirmText="Kembali"
                        onClick={this.endNotification}
                    />
                }
            </>
           
        );
    }
}