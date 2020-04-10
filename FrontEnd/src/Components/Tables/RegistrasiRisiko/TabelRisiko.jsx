import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../RegistrasiRisiko/TabelRisiko.module.css';
import SirioTable from '../SirioTable';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import SirioMessageButton from "../../Button/ActionButton/SirioMessageButton";

class TabelRisiko extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true,
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            openNotification: false
        })
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
                pathname: "/registrasi-risiko/detail",
                state: {
                    id: row.idRisiko,
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
                        Tambah Risiko
                    </SirioButton>
                </NavLink>
                <NavLink to={{
                    pathname: "/registrasi-risiko/ubah-hierarki"
                }}>
                    <SirioButton purple classes="mx-2">
                        Ubah Semua Hierarki
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    render() {
        return (
            <>
            <SirioTable
                title="Registrasi Risiko"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
            {this.props.location.state && this.props.location.state.addSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Risiko berhasil Disimpan"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.deleteSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Risiko berhasil Dihapus"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.editSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Risiko berhasil Diubah"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
} 

export default withRouter(TabelRisiko);