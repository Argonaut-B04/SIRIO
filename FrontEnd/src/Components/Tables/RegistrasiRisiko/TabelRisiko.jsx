import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../RegistrasiRisiko/TabelRisiko.module.css';
import SirioTable from '../SirioTable';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import { NavLink, Redirect } from 'react-router-dom';
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
        this.endNotification = this.endNotification(this);
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
        RegistrasiRisikoService.getAllRisiko().then(response => {
            this.setState({
                rowList: response.data.result
            })
        })
        .catch(error => {
            if (error.response.data.status == 401) {
                this.setState({
                    redirector: <Redirect to={{
                        pathname: "/401",
                        state: {
                            detail: error.response.data.message,
                        }
                    }} />
                })
            }
        })
        ;
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

    parentFormatter(cell, row) {
        if (row.parent === null) {
            return "-"
        } else {
            return row.namaParent
        }
    }

    columns = [{
        dataField: 'nama',
        text: 'NAMA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "30%", textAlign: 'left' };
        }
    }, {
        dataField: 'kategori',
        text: 'KATEGORI',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'center' };
        },
        style: () => {
            return { textAlign: 'center' }
        },
    }, {
        dataField: 'parent',
        text: 'PARENT',
        sort: true,
        formatter: this.parentFormatter,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'center' };
        }
    }, {
        dataField: 'noData 1',
        text: 'AKSI',
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
        return this.state.redirector || (
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
                {this.props.location.state && this.props.location.state.editHierarkiSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Hierarki Risiko berhasil Diubah"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
} 

export default withRouter(TabelRisiko);