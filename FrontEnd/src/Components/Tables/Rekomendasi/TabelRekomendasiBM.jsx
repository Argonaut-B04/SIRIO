import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import classes from './TabelRekomendasi.module.css';
import SirioTable from '../SirioTable';

export default class TabelRekomendasi extends React.Component {

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

    getButtonsFirst(cell, row) {
        return (
            <SirioButton
                purple
                onClick={() => window.location.href = "http://www.google.com"}
            >
                Tambah Bukti
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
            return { width: "50px", textAlign: 'center' };
        },
        formatter: this.rowNumber

    }, {
        dataField: 'keteranganRekomendasi',
        text: 'KETERANGAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }

    }, {
        dataField: 'tenggatWaktuRekomendasi',
        text: 'KETERANGAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }
        
    }, 
        {
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
        formatter: this.getButtonsFirst
    }];

    data = [
        { "tenggatWaktu": "", "id": 10, "keteranganRekomendasi": "Nathan", "statusBukti": "Menunggu Pengaturan Tenggat Waktu" },
        { "tenggatWaktu": "", "id": 1, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Ditolak" },
        { "tenggatWaktu": "", "id": 2, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Sedang Dijalankan" },
        { "tenggatWaktu": "", "id": 3, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Menunggu Persetujuan" },
        { "tenggatWaktu": "10/10/2000", "id": 4, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Menunggu Pelaksanaan" },
        { "tenggatWaktu": "10/10/2000", "id": 5, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Sedang Dijalankan" },
        { "tenggatWaktu": "", "id": 6, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 7, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 8, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 9, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 11, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 12, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 13, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 14, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 25, "keteranganRekomendasi": "Nathan2", "statusRekomendasi": "Draft" },
        { "tenggatWaktu": "", "id": 16, "keteranganRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
    ]

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Rekomendasi"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
            />
        );
    }
} 