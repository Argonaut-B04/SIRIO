import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../RegistrasiRisiko/TabelRisiko.module.css';
import SirioTable from '../SirioTable';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import { NavLink } from 'react-router-dom';

export default class TabelRisiko extends React.Component {

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
        const response = await RegistrasiRisikoService.getRisiko();

        this.setState({
            rowList: response.data.result
        })
    }

    getButtons(cell, row) {
        return (
            <NavLink to={{
                pathname: "/registrasi-risiko/detail",
                state: {
                    id: row.id,
                }
            }}>
                <SirioButton
                    purple
                >
                    Detail
                </SirioButton>
            </NavLink>
        )
    }

    columns = [{
        dataField: 'namaRisiko',
        text: 'NAMA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "30%", textAlign: 'left' };
        }
    }, {
        dataField: 'risikoKategori',
        text: 'KATEGORI',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'center' };
        }
    }, {
        dataField: 'parent',
        text: 'PARENT',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'center' };
        },
        formatter: this.formatterParent
    }, {
        dataField: 'id',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtons
    }];

    formatterParent(cell, row) {
        console.log(cell)
    }

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/registrasi-risiko/tambah"
                }}>
                    <SirioButton purple recommended classes="mx-2">
                        Tambah Risiko
                    </SirioButton>
                </NavLink>
                <SirioButton purple
                onClick={() => window.location.href = "/registrasi-risiko/ubah-hierarki"}>
                    Ubah Semua Hierarki
                </SirioButton> 
            </div>
        )
    }

    render() {
        return (
            <SirioTable
                title="Registrasi Risiko"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
} 