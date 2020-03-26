import React from 'react';
import classes from './DetailKantorCabang.module.css';
import SirioDetailPage from '../SirioDetailPage';

export default class TabelKantorCabang extends React.Component {

    columns = [{
        dataField: 'informasi',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }

    }, {
        dataField: 'isiInformasi',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }];

    data = [
        { "informasi": "Nama Point  :", "isiInformasi": "Kantor Cabang 1" },
        { "informasi": "Branch Manager  :", "isiInformasi": "billa" },
        { "informasi": "Area    :", "isiInformasi": "Area 1" },
        { "informasi": "Regional    :", "isiInformasi": "Regional 1" },
        { "informasi": "Jumlah Kunjungan:", "isiInformasi": "2" },
        { "informasi": "Kunjungan Audit:", "isiInformasi": "Sudah Pernah" },
    ]

    render() {
        return (
            <SirioDetailPage
                title="Detail Kantor Cabang"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
            />
        );
    }
} 