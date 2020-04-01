import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../../Tables/RegistrasiRisiko/TabelRisiko.module.css';
import SirioTable from '../../Tables/SirioTable';

export default class FormHierarkiRisiko extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            nama: "",
            kategori: "",
            editMode: false,
        }

        this.handleSelectChange = this.handleSelectChange.bind(this);
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

    getButtons(cell, row) {
        return (
            <SirioButton
                purple
                onClick={() => this.setState.editMode(true),
                    window.location.href = "/registrasi-risiko/ubah-hierarki/ubah"}
            >
                Ubah Hierarki
            </SirioButton>
        )
    }

    columns = [{
        dataField: 'namaRisiko',
        text: 'NAMA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "30%", textAlign: 'left' };
        }
    }, {
        dataField: 'kategoriRisiko',
        text: 'KATEGORI',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }
    }, {
        dataField: 'parentRisiko',
        text: 'PARENT',
        sort: true,
        classes: classes.rowItem,
        formatter: this.statusFormatter,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'id',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtons
    }];

    data = [
        { "id": 1, "namaRisiko": "Risiko 1", "kategoriRisiko": "Kategori 1", "parentRisiko": "-" },
        { "id": 2, "namaRisiko": "Risiko 2", "kategoriRisiko": "Kategori 2", "parentRisiko": "Risiko 1" },
        { "id": 3, "namaRisiko": "Risiko 3", "kategoriRisiko": "Kategori 3", "parentRisiko": "Risiko 2" },
        { "id": 4, "namaRisiko": "Risiko 4", "kategoriRisiko": "Kategori 1", "parentRisiko": "-" },
        { "id": 5, "namaRisiko": "Risiko 5", "kategoriRisiko": "Kategori 2", "parentRisiko": "Risiko 4" },
        { "id": 6, "namaRisiko": "Risiko 6", "kategoriRisiko": "Kategori 3", "parentRisiko": "Risiko 5" },
        { "id": 7, "namaRisiko": "Risiko 7", "kategoriRisiko": "Kategori 1", "parentRisiko": "-" },
        { "id": 8, "namaRisiko": "Risiko 8", "kategoriRisiko": "Kategori 2", "parentRisiko": "Risiko 7" },
        { "id": 9, "namaRisiko": "Risiko 9", "kategoriRisiko": "Kategori 3", "parentRisiko": "Risiko 8" },
    ]

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    // getButtonSecond(){
    //     if (this.state.editMode) {
    //         return (
            
    //         )
    //     } else {
    //         return (

    //         )
    //     }
    // }

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <div>
                <SirioButton purple recommended classes="mx-2"
                onClick={() => window.location.href = "/registrasi-risiko/tambah"}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                onClick={() => window.location.href = "/registrasi-risiko"}>
                    Batal
                </SirioButton> 
            </div>
        )
    }

    render() {
        return (
            <SirioTable
                title="Ubah Hierarki Risiko"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
} 