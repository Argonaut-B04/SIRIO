import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelRencanaPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';
import SirioAxiosBase from '../../../Services/SirioAxiosBase';

export default class TabelRencanaPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true
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
            case 1:
                return (
                    <span style={{ color: '#black' }}>Draft</span>
                );
            case 3:
                return (
                    <span style={{ color: '#5DBCD2' }}>Selesai</span>
                );
            case 2:
                return (
                    <span style={{ color: '#6FCF97' }}>Sedang Dijalankan</span>
                );
            default:
                return (cell);
        }
    }

    getButtonsFirst(cell, row) {
        return (
            <SirioButton
                purple
                onClick={() => window.location.href = "/manager/rencanaPemeriksaan/detail"}
            >
                Detail
            </SirioButton>
        )
    }

    columns = [{
        dataField: 'namaRencana',
        text: 'NAMA RENCANA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }
    }, {
        dataField: 'daftarTugasPemeriksaan[0].tanggalSelesai',
        text: 'TAHUN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' }
        },
        formatter: SirioAxiosBase.formatDateYear
    }, {
        dataField: 'daftarTugasPemeriksaan[0].tanggalSelesai',
        text: 'BULAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' }
        },
        formatter: SirioAxiosBase.formatDateMonth
    }, {
        dataField: 'status',
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
                onClick={() => window.location.href = "/manager/rencanaPemeriksaan/tambah"}
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