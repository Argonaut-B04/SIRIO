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
        { "informasi": "QA Officer:", "isiInformasi": "Rudi" },
        { "informasi": "Tanggal Mulai:", "isiInformasi": "12/12/2020" },
        { "informasi": "Tanggal Selesai:", "isiInformasi": "19/12/2020" },

    ]


    data1 = [
        { "informasi": "Kantor Cabang", "isiInformasi": "Kantor Cabang Ciawi" },
        { "informasi": "QA Officer:", "isiInformasi": "Lala" },
        { "informasi": "Tanggal Mulai:", "isiInformasi": "20/12/2020" },
        { "informasi": "Tanggal Selesai:", "isiInformasi": "27/12/2020" },

    ]

    render() {
        return (
            <div>
                <SirioDetailPage
                    title="Detail Rencana Pemeriksaan"
                    data={this.data}
                    id='id'
                    columnsDefinition={this.columns}
                />

                <SirioDetailPage
                    title="Detail Rencana Pemeriksaan"
                    data={this.data1}
                    id='id'
                    columnsDefinition={this.columns}
                />
            </div>
            
        );
    }
} 