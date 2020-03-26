import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelKantorCabang.module.css';
import SirioTable from '../SirioTable';

export default class TabelKantorCabang extends React.Component {

    getButtonsFirst(cell, row) {
        return (
            <SirioButton
                purple
                onClick={() => window.location.href = "/administrator/kantorCabang/detail-kantorCabang"}
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
        dataField: 'namaPoint',
        text: 'NAMA POINT',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'bm',
        text: 'BRANCH MANAGER',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'area',
        text: 'AREA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'regional',
        text: 'REGIONAL',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'jumlahKunjungan',
        text: 'JUMLAH KUNJUNGAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
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

    data = [
        { "id": "1", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"},
        { "id": "2", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"},
        { "id": "3", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"},
        { "id": "4", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"},
        {  "id": "5", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"},
        { "id": "6", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"},
        {  "id": "7", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"},
        {  "id": "8", "namaPoint": "Kantor A", "bm": "nabilla",  "area": "Area 1", "regional": "regional 1", "jumlahKunjungan": "1"}
       
        
    ]

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    headerButton() {
        return (
            <SirioButton purple>
                Tambah Kantor Cabang
            </SirioButton>
        )
    }

    render() {
        return (
        
            <SirioTable
                title="Daftar Kantor Cabang"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
} 