import React from 'react';
import classes from './DetailRencana.module.css';
import SirioDetailPage from '../SirioDetailPage';

export default class TabelRencanaPemeriksaan extends React.Component {

    columns = [{
        dataField: 'informasi',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "5%", textAlign: 'left' };
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
        { "informasi": "Kantor Cabang", "isiInformasi": "Kantor Cabang Ciawi" },
        { "informasi": "Lampiran:", "isiInformasi": "https://drive.google.com/drive/folders/1SvB_2W4BjD8rxVQR1-dDbA63-4Zx-hoN" },
        ]

    render() {
        return (
            <SirioDetailPage
                title="Detail Rencana Pemeriksaan"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
            />
        );
    }
} 