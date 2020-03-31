import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelHasilPemeriksaan.module.css';
import SirioTable from '../SirioTable';

export default class TabelHasilPemeriksaan extends React.Component {

    statusFormatter(cell) {
        switch (cell) {
            case "Draft":
                return (
                    <span style={{ color: '#7F3F98' }}>{cell}</span>
                );
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            case "Menunggu Pelaksanaan":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    getButtonsFirst(cell, row) {
        const menjalankanRekomendasi = row.menjalankanRekomendasi;
        const status = row.statusHasilPemeriksaan;
        const jalankan = (menjalankanRekomendasi === 1 && status === "Menunggu Pelaksanaan");
        return (
            <SirioButton
                purple
                disabled={!jalankan}
            >
                Jalankan
            </SirioButton>
        )
    }

    getButtonsSecond(cell, row) {
        return (
            <SirioButton purple>
                Detail Hasil
            </SirioButton>
        )
    }

    getNamaHasilPemeriksaan(cell, row) {
        return 'Hasil Pemeriksaan Kantor Cabang ' + row.kantorCabang + ' untuk Tugas Pemeriksaan #' + row.idTugasPemeriksaan;
    }


    columns = [{
        dataField: '',
        isDummyField: true,
        text: 'NO',
        sort: true,
        classes: classes.rowNumber,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "50px", textAlign: 'center' };
        },
        formatter: this.rowNumber

    }, {
        dataField: "",
        text: 'HASIL PEMERIKSAAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        },
        formatter: this.getNamaHasilPemeriksaan

    }, {
        dataField: 'statusHasilPemeriksaan',
        text: 'STATUS',
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
        formatter: this.getButtonsFirst
    }, {
        dataField: 'id',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsSecond
    }];

    data = [
        { "id": 1, "kantorCabang": "Pekalongan", "statusHasilPemeriksaan": "Draft", "idTugasPemeriksaan": 1, "menjalankanRekomendasi": 0},
        { "id": 2, "kantorCabang": "Pekalongan", "statusHasilPemeriksaan": "Ditolak", "idTugasPemeriksaan": 2, "menjalankanRekomendasi": 0},
        { "id": 3, "kantorCabang": "Pekalongan", "statusHasilPemeriksaan": "Menunggu Persetujuan", "idTugasPemeriksaan": 3, "menjalankanRekomendasi": 0},
        { "id": 4, "kantorCabang": "Pekalongan", "statusHasilPemeriksaan": "Menunggu Persetujuan", "idTugasPemeriksaan": 4, "menjalankanRekomendasi": 0},
        { "id": 5, "kantorCabang": "Jakarta", "statusHasilPemeriksaan": "Menunggu Pelaksanaan", "idTugasPemeriksaan": 5, "menjalankanRekomendasi": 0},
        { "id": 6, "kantorCabang": "Bandung", "statusHasilPemeriksaan": "Menunggu Pelaksanaan", "idTugasPemeriksaan": 6, "menjalankanRekomendasi": 0},
        { "id": 7, "kantorCabang": "Bandung", "statusHasilPemeriksaan": "Selesai", "idTugasPemeriksaan": 7, "menjalankanRekomendasi": 0},
        { "id": 8, "kantorCabang": "Bandung", "statusHasilPemeriksaan": "Selesai", "idTugasPemeriksaan": 8, "menjalankanRekomendasi": 0},
        { "id": 9, "kantorCabang": "Bandung", "statusHasilPemeriksaan": "Draft", "idTugasPemeriksaan": 9, "menjalankanRekomendasi": 1},
    ];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Hasil Pemeriksaan"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
            />
        );
    }
}