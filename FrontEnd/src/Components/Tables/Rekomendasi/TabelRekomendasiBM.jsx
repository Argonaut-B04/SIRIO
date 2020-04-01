import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioTable from '../SirioTable';
import RekomendasiService from '../../../Services/RekomendasiService';
import { NavLink } from 'react-router-dom';
import classes from './TabelRekomendasiBM.module.css';

export default class TabelRekomendasi extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: []
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        const response = await RekomendasiService.getRekomendasiByLoggedInUser();

        this.setState({
            rowList: response.data.result
        })
    }

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
        const status = row.statusBukti;
        const tambah = status === "";
        const detail = status === "Menunggu Persetujuan" || status === "Disetujui" || status === "Ditolak";

        if (tambah) {
            return (
                <SirioButton
                    purple
                    onClick={() => window.location.href = "/bm/bukti-pelaksanaan/tambah"}
                >
                    Tambah Bukti
                </SirioButton>
            )
        } else if (detail) {
            return (
                <SirioButton
                    purple
                    onClick={() => window.location.href = "/bm/rekomendasi/detail-bukti"}
                >
                    Detail Bukti
                </SirioButton>
            )
        }
    }

    columns = [{
        dataField: 'keterangan',
        text: 'KETERANGAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }

    }, {
        dataField: 'tenggatWaktu',
        text: 'TENGGAT WAKTU',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
        
    }, {
        dataField: 'durasi',
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
        dataField: 'action',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtons
    }];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Rekomendasi"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
            />
        );
    }
} 