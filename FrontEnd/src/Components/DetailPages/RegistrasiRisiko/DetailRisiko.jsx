import React from 'react';
import classes from './DetailRisiko.module.css';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';

export default class RegistrasiRisiko extends React.Component {

    columns = [{
        dataField: 'key',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "5%", textAlign: 'left' };
        }

    }, {
        dataField: 'value',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }];

    data = [
        { "key": "Nama Risiko :", "value": "Risiko 1" },
        { "key": "Kategori Risiko :", "value": "Kategori 1" },
        { "key": "Parent :", "value": "-" },
        { "key": "Referensi SOP :", "value": "SOP 1" },
        { "key": "Komponen Risiko :", "value": "Komponen Risiko 1" },
    ]

    subButton() {
        return (
            <div>
                <SirioButton purple
                    classes="mx-2 my-2"
                    onClick={() => window.location.href = "/bm/bukti-pelaksanaan/tambah"}
                >
                    Ubah Bukti
                </SirioButton>
                
            </div>
        )
    }

    render() {
        return (
            <SirioDetailPage
                title="Detail Risiko"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
            />
        );
    }
} 