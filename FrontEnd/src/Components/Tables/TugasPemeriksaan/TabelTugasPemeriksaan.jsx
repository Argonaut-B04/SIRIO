import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelTugasPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import TugasPemeriksaanService from '../../../Services/TugasPemeriksaanService';
import { NavLink } from 'react-router-dom';
import SirioAxiosBase from '../../../Services/SirioAxiosBase';

export default class TabelTugasPemeriksaan extends React.Component {

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
        const response = await TugasPemeriksaanService.getTugasPemeriksaanByLoggedInUser();

        this.setState({
            rowList: response.data.result
        })
    }

    getButtonsFirst(cell, row) {
        const idHasilPemeriksaan = row.idHasilPemeriksaan;
        const status = row.namaStatus;
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
        return 'Kantor Cabang ' + row.namaKantorCabang +
            ', untuk QA ' + row.namaQA;
    }

    columns = [
        {
            dataField: '',
            isDummyField: true,
            text: 'NO',
            sort: true,
            classes: classes.rowNumber,
            formatter: this.rowNumber,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "50px", textAlign: 'center' };
            }
        }, {
            dataField: '',
            isDummyField: true,
            text: 'TUGAS PEMERIKSAAN',
            sort: true,
            classes: classes.rowItem,
            formatter: this.namaTugasPemeriksaanFormatter,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "25%", textAlign: 'left' };
            }
        }, {
            dataField: 'tanggalMulai',
            text: 'TANGGAL MULAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'tanggalSelesai',
            text: 'TANGGAL SELESAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'noData 1',
            text: '',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsFirst,
            style: () => {
                return { textAlign: 'center' }
            }
        }];

    defaultSorted = [{
        dataField: 'id',
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