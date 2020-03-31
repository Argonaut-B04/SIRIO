import React from 'react';
import classes from './DetailRisiko.module.css';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';

export default class RegistrasiRisiko extends React.Component {

    subButton() {
        return (
            <div>
                <SirioButton 
                    classes="mx-2"
                    purple recommended
                    onClick={() => window.location.href = "/"}
                    className={classes.buttons}
                >
                    Ubah
                </SirioButton>
                <SirioButton
                    purple
                    onClick={() => window.location.href = "/"}
                    className={classes.buttons}
                >
                    Hapus
                </SirioButton>
            </div>
        )
    }

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

    render() {
        return (
            <SirioDetailPage
                title="Detail Risiko"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                subButton={this.subButton()}
                link="registrasi-risiko"
            />
        );
    }
} 