import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../../Tables/RegistrasiRisiko/TabelRisiko.module.css';
import SirioTable from '../../Tables/SirioTable';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import { NavLink } from 'react-router-dom';

export default class FormHierarkiRisiko extends React.Component {

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
        const response = await RegistrasiRisikoService.getAllRisiko();

        this.setState({
            rowList: response.data.result
        })
    }

    getButtons(cell, row) {
        console.log(row)
        return (
            <NavLink to={{
                pathname: "/registrasi-risiko/ubah-hierarki/ubah",
                state: {
                    id: row.idRisiko,
                }
            }}>
                <SirioButton
                    purple
                >
                    Ubah Hierarki
                </SirioButton>
            </NavLink>
        )
    }

    parentFormatter() {
        if (this.parent) {
            return this.parent
        } else {
            return "-"
        }
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
        formatter: this.parentFormatter,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'center' };
        }
    }, {
        dataField: 'noData 1',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtons
    }];

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
                        Simpan
                    </SirioButton>
                </NavLink>
                <NavLink to={{
                    pathname: "/registrasi-risiko"
                }}>
                    <SirioButton purple classes="mx-2">
                        Batal
                    </SirioButton>
                </NavLink>
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