import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelRekomendasiBM.module.css';
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

    getButtons(cell, row) {
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
            return { width: "25%", textAlign: 'left' };
        }

    }, {
        dataField: 'tenggatWaktuRekomendasi',
        text: 'TENGGAT WAKTU',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
        
    }, {
        dataField: 'durasiRekomendasi',
        text: 'DURASI',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "12%", textAlign: 'left' };
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
        { "id": 10, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 1, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Ditolak" },
        { "id": 2, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Disetujui" },
        { "id": 3, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 4, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 5, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 6, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 7, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 8, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 9, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 11, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 12, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 13, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 14, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 25, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
        { "id": 16, "keteranganRekomendasi": "Keterangan", "tenggatWaktuRekomendasi": "01/02/2020", "durasiRekomendasi": "10 Hari", "statusBukti": "Menunggu Persetujuan" },
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