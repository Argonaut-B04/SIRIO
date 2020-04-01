import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelRencanaPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';

export default class TabelRencanaPemeriksaan extends React.Component {

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
        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanByLoggedInUser();
        this.setState({
            rowList: response.data.result
        })
    }

    statusFormatter(cell) {
        switch (cell) {
            case "Draft":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Selesai":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            case "Sedang Dijalankan":
                return (
                    <span style={{ color: '#6FCF97' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    getButtonsFirst(cell, row) {
        return (
            <SirioButton
                purple
                onClick={() => window.location.href = "/manager/rencanaPemeriksaan/detail-rencana"}
            >
                Detail
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
        dataField: 'namaRencana',
        text: 'NAMA RENCANA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }
    }, {
        dataField: 'tahun',
        text: 'TAHUN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'bulan',
        text: 'BULAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'statusRencanaPemeriksaan',
        text: 'STATUS',
        sort: true,
        classes: classes.rowItem,
        formatter: this.statusFormatter,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'noData1',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsFirst
    }];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    headerButton() {
        return (
            <SirioButton 
                purple
                onClick={() => window.location.href = "/manager/rencanaPemeriksaan/form-tambahRencanaPemeriksaan"}
            >

                Tambah Rencana
            </SirioButton>
        )
    }

    render() {
        return (
            <SirioTable
                title="Daftar Rencana Pemeriksaan"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
} 