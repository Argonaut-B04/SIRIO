import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';
import SirioTable from '../SirioTable';

export default class TabelBuktiPelaksanaan extends React.Component {

    statusFormatter(cell) {
        switch (cell) {
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Disetujui":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    getButtons(cell, row) {
        return (
            <SirioButton purple
                onClick={() => window.location.href = "/bukti-pelaksanaan/detail-persetujuan"}
            >
                Detail Bukti
            </SirioButton>
        )
    }

    columns = [{
        dataField: '',
        isDummyField: true,
        text: 'NO',
        sort: true,
        classes: classes.rowNumber,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "60px", textAlign: 'center' };
        },
        formatter: this.rowNumber
    }, {
        dataField: 'keteranganRekomendasi',
        text: 'KETERANGAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "30%", textAlign: 'left' };
        }
    }, {
        dataField: 'namaKantorCabang',
        text: 'KANTOR CABANG',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }
    }, {
        dataField: 'statusBukti',
        text: 'STATUS BUKTI',
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
        { "id": 10, "keteranganRekomendasi": "Keterangan", "namaKantorCabang": "Kantor Cabang Depok", "statusBukti": "Disetujui" },
        { "id": 1, "keteranganRekomendasi": "Keterangan", "namaKantorCabang": "Kantor Cabang Depok", "statusBukti": "Ditolak" },
        { "id": 2, "keteranganRekomendasi": "Keterangan", "namaKantorCabang": "Kantor Cabang Depok", "statusBukti": "Disetujui" },
        { "id": 3, "keteranganRekomendasi": "Keterangan", "namaKantorCabang": "Kantor Cabang Depok", "statusBukti": "Menunggu Persetujuan" },
        { "id": 4, "keteranganRekomendasi": "Keterangan", "namaKantorCabang": "Kantor Cabang Depok", "statusBukti": "Menunggu Persetujuan" },
        { "id": 5, "keteranganRekomendasi": "Keterangan", "namaKantorCabang": "Kantor Cabang Depok", "statusBukti": "Menunggu Persetujuan" },
        { "id": 6, "keteranganRekomendasi": "Keterangan", "namaKantorCabang": "Kantor Cabang Depok", "statusBukti": "Menunggu Persetujuan" },
    ]

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Bukti Pelaksanaan Rekomendasi"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
            />
        );
    }
} 