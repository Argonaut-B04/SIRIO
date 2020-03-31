import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';
import SirioTable from '../SirioTable';

export default class TabelRisiko extends React.Component {

    getButtons(cell, row) {
        return (
            <SirioButton
                purple
                onClick={() => window.location.href = "/registrasi-risiko/detail/1"}
            >
                Detail
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

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <div>
                <SirioButton purple recommended classes="mx-2"
                onClick={() => window.location.href = "http://www.google.com"}>
                    Tambah Risiko
                </SirioButton>
                <SirioButton purple
                onClick={() => window.location.href = "http://www.google.com"}>
                    Ubah Semua Hierarki
                </SirioButton> 
            </div>
        )
    }

    render() {
        return (
            <SirioTable
                title="Registrasi Risiko"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
} 