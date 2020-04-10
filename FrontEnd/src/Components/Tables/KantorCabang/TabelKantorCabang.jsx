import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelKantorCabang.module.css';
import SirioTable from '../SirioTable';
import KantorCabangService from '../../../Services/KantorCabangService';
import { NavLink } from 'react-router-dom';

/**
 * Kelas untuk membuat komponen tabel kantor cabang
 */
export default class TabelKantorCabang extends React.Component {

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
        const response = await KantorCabangService.getKantorCabangByLoggedInUser();

        this.setState({
            rowList: response.data.result
        })
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
        dataField: 'pemilik',
        text: 'BRANCH MANAGER',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
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
            <SirioButton 
                purple
                onClick={() => window.location.href = "/administrator/kantorCabang/form-tambahKantorCabang"}
            >
                Tambah Kantor Cabang
            </SirioButton>
        )
    }

    getButtonsFirst(cell, row) {
        return (
            <NavLink to={{
                pathname: "/administrator/kantorCabang/detail-kantorCabang",
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
            <SirioTable
                title="Daftar Kantor Cabang"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
} 