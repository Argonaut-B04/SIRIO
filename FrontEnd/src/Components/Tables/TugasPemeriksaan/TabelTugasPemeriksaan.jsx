import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelTugasPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import TugasPemeriksaanService from '../../../Services/TugasPemeriksaanService';
import AuthenticationService from '../../../Services/AuthenticationService';
import { NavLink } from 'react-router-dom';
import SirioAxiosBase from '../../../Services/SirioAxiosBase';

export default class TabelTugasPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            role: AuthenticationService.getRole(),
            rowList: []
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        const response = await TugasPemeriksaanService.getTugasPemeriksaanByLoggedInUser();

        this.setState({
            rowList: response.data.result
        })
    }

    getButtonsFirst(cell, row) {
        const idHasilPemeriksaan = row.idHasilPemeriksaan;
        const terdapatHasil = idHasilPemeriksaan !== null;
        if (terdapatHasil) {
            return (
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/detail",
                    state: {
                        id: idHasilPemeriksaan,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Detail Hasil
                    </SirioButton>
                </NavLink>
            )
        } else {
            return (
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/tambah",
                    state: {
                        id: row.id,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Tambah Hasil
                    </SirioButton>
                </NavLink>
            )
        }
    }

    namaTugasPemeriksaanFormatter(cell, row) {
        switch (this.state.role) {
            case "QA Officer Operational Risk":
                return 'Kantor Cabang ' + row.namaKantorCabang;
            default:
                return 'Kantor Cabang ' + row.namaKantorCabang +
                    ', QA ' + row.namaQA;
        }
    }

    columns = [
        {
            dataField: 'namaKantorCabang',
            isDummyField: true,
            text: 'TUGAS PEMERIKSAAN',
            sort: true,
            classes: classes.rowItem,
            formatter: (cell, row) => this.namaTugasPemeriksaanFormatter(cell, row),
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "25%", textAlign: 'left' };
            }
        }, {
            dataField: 'tanggalMulai',
            text: 'TANGGAL MULAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "20%" };
            },
            formatter: SirioAxiosBase.formatDateFromSirioDatePicker
        }, {
            dataField: 'tanggalSelesai',
            text: 'TANGGAL SELESAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "20%" };
            },
            formatter: SirioAxiosBase.formatDateFromSirioDatePicker
        }, {
            dataField: 'noData 1',
            text: 'Aksi Hasil',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsFirst,
            style: () => {
                return { textAlign: 'center' }
            }
        }];

    defaultSorted = [{
        dataField: 'tanggalMulai',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Tugas Pemeriksaan"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
            />
        );
    }
}