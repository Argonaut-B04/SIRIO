import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelKantorCabang.module.css';
import SirioTable from '../SirioTable';
import KantorCabangService from '../../../Services/KantorCabangService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import SirioMessageButton from "../../Button/ActionButton/SirioMessageButton";

/**
 * Kelas untuk membuat komponen tabel kantor cabang
 */
class TabelKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true
        };

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
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
        const response = await KantorCabangService.getKantorCabangList();

        this.setState({
            rowList: response.data.result
        })
    }

    auditFormatter(kunjunganAudit){
        if (kunjunganAudit == false){
            return "Belum Pernah"
        }else if (kunjunganAudit == true){
            return "Sudah Pernah"
        }
    }

    columns = [{
        dataField: 'namaKantor',
        text: 'NAMA POINT',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    },{
        dataField: 'pemilik.nama',
        text: 'BRANCH MANAGER',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' }
        },
        
    }, {
        dataField: 'area',
        text: 'AREA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'regional',
        text: 'REGIONAL',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'kunjunganAudit',
        text: 'KUNJUNGAN AUDIT',
        sort: true,
        formatter: this.auditFormatter,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'noData1',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsFirst
    }];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    headerButton() {
        return (
            <NavLink to={{
                pathname: "/administrator/kantorCabang/tambah"
            }}>
                <SirioButton
                    purple recommended
                >
                    Tambah Kantor Cabang
                </SirioButton>
            </NavLink>
        )
    }

    getButtonsFirst(cell, row) {
        return (
            <NavLink to={{
                pathname: "/administrator/kantorCabang/detail",
                state: {
                    id: row.idKantor
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

    render() {
        return (
            <>
                <SirioTable
                    title="Daftar Kantor Cabang"
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
                    modalTitle="Kantor Cabang berhasil Disimpan"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.deleteSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Kantor Cabang berhasil Dihapus"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.editSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Kantor Cabang berhasil Diubah"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
} 

export default withRouter(TabelKantorCabang);