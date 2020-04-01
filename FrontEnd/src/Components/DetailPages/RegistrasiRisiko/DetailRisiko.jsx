import React from 'react';
import classes from './DetailRisiko.module.css';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';

export default class RegistrasiRisiko extends React.Component {

    data = {
        Nama: "Risiko 1",
        Kategori: "Kategori 1",
        Parent: "-",
        SOP: "SOP 1",
        Komponen: "Komponen Risiko 1",
    }

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